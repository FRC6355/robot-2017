// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc6355.RobotsOverParma.subsystems;

import org.usfirst.frc6355.RobotsOverParma.RobotMap;
import org.usfirst.frc6355.RobotsOverParma.commands.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem implements PIDOutput, PIDSource {
	// These constants adjust the behavior of the PIDController
	static final double kP = 0.03; // Proportional
	static final double kI = 0.00; // Integral
	static final double kD = 0.00; // Derivative
	static final double kF = 0.00; // Feed forward
	static final double kToleranceDegrees = 2.0f;

	// Anything lower than this won't turn the wheels.
	static final double kDeadbandForward = 0.40;
	static final double kDeadbandTurn = 0.45;

	// The rate of adjustment from the turn controller.
	double rotateToAngleRate = 0.00;

	// A capped value of the turn rate adjustment. Prevents too aggressive
	// correction.
	double rotateToAngleRateCapped = 0.00;

	boolean isDriveStraightMode = false;

	// This PIDController provides feedback on how far off we are from
	// the desired angle. Used for driving straight or turning to a desired
	// angle.
	private PIDController turnController;

	private final SpeedController leftFrontSpeedControler = RobotMap.driveTrainLeftFrontSpeedControler;
	private final SpeedController leftRearSpeedControler = RobotMap.driveTrainLeftRearSpeedControler;
	private final SpeedController rightFrontSpeedControler = RobotMap.driveTrainRightFrontSpeedControler;
	private final SpeedController rightRearSpeedControler = RobotMap.driveTrainRightRearSpeedControler;
	private final RobotDrive robotDrive4 = RobotMap.driveTrainRobotDrive4;

	public DriveTrain() {
		try {
			// Initialize the PIDController.
			// The AHRS (NavX board) is the PIDSource.
			// This DriveTrain class is the PIDOutput target.
			// turnController = new PIDController(kP, kI, kD, kF, RobotMap.ahrs, this);
			turnController = new PIDController(kP, kI, kD, kF, this, this);
			turnController.setInputRange(0.0f, 360.0f);
			turnController.setOutputRange(-1.0, 1.0);
			turnController.setAbsoluteTolerance(kToleranceDegrees);
			turnController.setContinuous(true);
			turnController.disable();
		} catch (Exception ex) {
			DriverStation.reportError(ex.getMessage(), true);
		}

	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// This command is the one that will be active when no other commands
		// require the subsystem.
		setDefaultCommand(new DriveWithJoystickComand());
	}

	/*
	 * This is the primary command for single joystick driving.
	 */
	public void arcadeDrive(Joystick joy) {
		Drive(joy.getAxis(AxisType.kY), joy.getAxis(AxisType.kX));
	}

	/*
	 * Drives the robot forward with the specified magnitude and turn. The
	 * forwardMagnitude is a value between -1 and 1. The turnMagnitude is a
	 * value between -1 and 1. The method will attempt to drive the robot
	 * straight if the turnMagnitude is below 0.1
	 */
	public void Drive(double forwardMagnitude, double turnMagnitude) {
		SmartDashboard.putNumber("AHRS Angle", getAngle());
		SmartDashboard.putNumber("Robot Drive Correction", rotateToAngleRate);
		SmartDashboard.putNumber("Robot Drive CorrectionCapped", rotateToAngleRateCapped);
		SmartDashboard.putNumber("Robot Drive PID SP", this.turnController.getSetpoint());

		if (forwardMagnitude < -0.1 || forwardMagnitude > 0.1) {
			double xAxis = turnMagnitude;
			if (-0.1 < xAxis && xAxis < 0.1) {
				if (!isDriveStraightMode) {
					setAngleToCurrentAngle();
				}
				SmartDashboard.putBoolean("Robot Drive Correction Active", true);
				// robotDrive4.arcadeDrive(handleDeadbandForward(forwardMagnitude),
				// handleDeadbandTurn(turnMagnitude + rotateToAngleRateCapped));

				// When driving with straight turn correction, don't adjust for
				// deadband.
				// Otherwise the robot will waddle as the turn correction
				// bounces between the min/max deadband.
				// As long as the forward magnitude is sufficient, then the
				// deadband on the turn is unnecessary.
				robotDrive4.arcadeDrive(handleDeadbandForward(forwardMagnitude),
						(turnMagnitude + rotateToAngleRateCapped));
			} else {
				// Mixed forward and turn inputs. Not just a turn, but not just
				// straight.
				SmartDashboard.putBoolean("Robot Drive Correction Active", false);
				robotDrive4.arcadeDrive(handleDeadbandForward(forwardMagnitude), handleDeadbandTurn(turnMagnitude));
				isDriveStraightMode = false;
			}
		} else {
			// Mostly turn based input. Minimal forward magnitude.
			SmartDashboard.putBoolean("Robot Drive Correction Active", false);
			robotDrive4.arcadeDrive(handleDeadbandForward(forwardMagnitude), handleDeadbandTurn(turnMagnitude));
			isDriveStraightMode = false;
		}
	}

	/*
	 * Stops the drive motion and disables the turn correction.
	 */
	public void Stop() {
		robotDrive4.stopMotor();
		isDriveStraightMode = false;

		// These values will be updated in the pidWrite() method.
		rotateToAngleRate = 0;
		rotateToAngleRateCapped = 0;

		turnController.disable();
		// RobotMap.ahrs.reset();
	}

	/*
	 * Similar to Drive(), this method will cause the robot to turn toward the
	 * desired set angle. The turnMagnitude is a value between -1 and 1.
	 */
	public void TurnToSetpoint(double turnMagnitude) {
		SmartDashboard.putNumber("AHRS Angle", getAngle());
		SmartDashboard.putNumber("Robot Drive Correction", rotateToAngleRate);
		SmartDashboard.putNumber("Robot Drive CorrectionCapped", rotateToAngleRateCapped);
		SmartDashboard.putNumber("Robot Drive PID SP", this.turnController.getSetpoint());

		// Calculate the amount to turn.
		// Use the output of the PID controller to help us "ease" into the
		// desired angle.
		double turnRate = turnMagnitude * rotateToAngleRateCapped;

		SmartDashboard.putNumber("Robot Drive TurnToSetpoint()", turnRate);

		System.out.println("TurnToSetpoint: turnRate: " + turnRate + ", roateToAngleRateCapped: " 
											+ rotateToAngleRateCapped + " Setpoint: " 
											+ this.turnController.getSetpoint() 
											+ " Angle: " + RobotMap.ahrs.getAngle());
		
		robotDrive4.arcadeDrive(0, handleDeadbandTurn(turnRate));
	}

	/*
	 * Set the target angle for the NavX drive and turn assistance. Sets the
	 * angle to the current angle. Used for driving straight.
	 */
	public void setAngleToCurrentAngle() {
		isDriveStraightMode = true;

		// Acquire current yaw angle, using this as the target angle.
		turnController.reset();
		turnController.setSetpoint(getAngle());

		System.out.println("Setting target angle to " + getAngle());

		SmartDashboard.putNumber("Robot Drive Target Angle", getAngle());
		SmartDashboard.putNumber("Robot Drive Angle", getAngle());

		// These values will be updated in the pidWrite() method.
		rotateToAngleRate = 0;
		rotateToAngleRateCapped = 0;

		// Enable the PID controller loop so that we get the pidWrite feedback.
		if (!turnController.isEnabled()) {
			turnController.enable();
		}
	}

	/*
	 * Set the target angle for the NavX drive and turn assistance. Sets the
	 * angle to offset degrees from the current angle.
	 */
	public void setAngle(double offset) {
		// Acquire current yaw angle, using this as the target angle.
		double newAngle = (getAngle() + offset) % 360.0;

		System.out.println("Setting target angle to " + newAngle);
		SmartDashboard.putNumber("Robot Drive Target Angle", newAngle);

		turnController.setSetpoint(newAngle);

		// These values will be updated in the pidWrite() method.
		rotateToAngleRate = 0;
		rotateToAngleRateCapped = 0;

		// Enable the PID controller loop so that we get the pidWrite feedback.
		if (!turnController.isEnabled()) {
			turnController.enable();
		}
	}

	/*
	 * Gets the current angle, as reported by the NavX.
	 */
	public double getAngle() {
		try {
			double angle = RobotMap.ahrs.getAngle() % 360.0;
			
			if (angle < 0.0)
				angle = 360.0 + angle;
			
			// System.out.println("NavX angle from getAngle(): " +angle);
			return angle;
		} catch (Exception ex) {
			DriverStation.reportError(ex.getMessage(), true);
			return 0.0;
		}
	}

	/*
	 * Ensure that we have a minimum amount of power to the drive. Otherwise the
	 * motors won't turn.
	 */
	private double handleDeadbandForward(double value) {
		double returnValue = 0.0;

		// Suppress small feeds, like the joystick drift.
		if (Math.abs(value) < 0.1) {
			returnValue = 0.0;
		} else if (value > 0) {
			returnValue = Math.max(value, kDeadbandForward);
		} else {
			returnValue = Math.min(value, -1 * kDeadbandForward);
		}

		SmartDashboard.putNumber("Robot Forward Magnitude", returnValue);
		return returnValue;
	}

	/*
	 * Ensure that we have a minimum amount of power to the drive. Otherwise the
	 * motors won't turn.
	 */
	private double handleDeadbandTurn(double value) {
		double returnValue = 0.0;

		// Suppress small feeds, like the joystick drift.
		if (Math.abs(value) < 0.1) {
			returnValue = 0.0;
		} else if (value > 0) {
			returnValue = Math.max(value, kDeadbandTurn);
		} else {
			returnValue = Math.min(value, -1 * kDeadbandTurn);
		}

		SmartDashboard.putNumber("Robot Turn Magnitude", returnValue);
		return returnValue;
	}

	/*
	 * This function is invoked periodically by the PID Controller, based upon
	 * navX MXP yaw angle input and PID Coefficients.
	 */
	@Override
	public void pidWrite(double output) {
		rotateToAngleRate = output;
		rotateToAngleRateCapped = output;

		if (rotateToAngleRateCapped > 0.2) {
			rotateToAngleRateCapped = 0.2;
		} else if (rotateToAngleRateCapped < -0.2) {
			rotateToAngleRateCapped = -0.2;
		}
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		RobotMap.ahrs.setPIDSourceType(pidSource);
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return RobotMap.ahrs.getPIDSourceType();
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return this.getAngle();
	}

}
