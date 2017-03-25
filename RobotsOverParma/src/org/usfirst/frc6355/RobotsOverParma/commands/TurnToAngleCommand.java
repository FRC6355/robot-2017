package org.usfirst.frc6355.RobotsOverParma.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc6355.RobotsOverParma.Robot;
import org.usfirst.frc6355.RobotsOverParma.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class TurnToAngleCommand extends Command {

	private double startTime = 0.0;
	private double degreesToTurn = 0.0;
	private double driveMagnitude = 0.0;
	private double degreesWhenFinished = 0.0;
	private int negativeEnergyCounter = 0;
	private int iterationsAtTarget = 0;

	public TurnToAngleCommand(double degreesToTurn, double driveMagnitude) {
    	requires(Robot.driveTrain);
		System.out.println("Turn command created.");
		this.degreesToTurn = degreesToTurn;
		this.driveMagnitude = driveMagnitude;
		
    	// SmartDashboard.putNumber("Autonomous - Turn Degrees", degreesToTurn);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
    	// Reset the navx and turn assist.
		Robot.driveTrain.Stop();

    	// Try to read the desired turn angle from smart dashboard.
    	// degreesToTurn = SmartDashboard.getNumber("Autonomous - Turn Degrees", degreesToTurn);
		
		startTime = Timer.getFPGATimestamp(); // seconds.

		
		Robot.driveTrain.setAngle(this.degreesToTurn);

		this.degreesWhenFinished = (Robot.driveTrain.getAngle() + this.degreesToTurn) % 360;
		System.out.println("Turn command init. Angle:" + Robot.driveTrain.getAngle() 
								+ " \r\nDegrees to Turn: " + degreesToTurn 
								+ ". \r\nDegrees when Done: " + degreesWhenFinished);
		
		negativeEnergyCounter = 0;
		iterationsAtTarget = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double target = Robot.driveTrain.getTargetAngle();
		double current = Robot.driveTrain.getAngle();
		double diff = Math.abs((target % 360.0) - (current % 360.0));
		
		System.out.println("Turn Diff: " + diff);
		
		
		if (diff < 10.0){
			iterationsAtTarget++;
			Robot.driveTrain.Stop();
			return;
		}

		// Not in the tolerance. Reset.
		iterationsAtTarget = 0;
		
		//if (degreesToTurn > 50.0 && diff < 7.0 && negativeEnergyCounter < 4) {
		//	// Apply negative drive, but only for a couple executions.
		//	Robot.driveTrain.TurnToSetpoint(-1.0 * this.driveMagnitude / 4.0);
		//	negativeEnergyCounter++;
		//} else if (degreesToTurn < 50.0 && diff < 5.0 && negativeEnergyCounter < 3) {
		//	// Apply negative drive, but only for a couple executions.
		//	Robot.driveTrain.TurnToSetpoint(-1.0 * this.driveMagnitude / 4.0);
		//	negativeEnergyCounter++;
		//} else 
		if (diff < 15.0 && degreesToTurn > 20.0){
			Robot.driveTrain.Stop();
			negativeEnergyCounter = 0;	// Reset.
		} else if (diff < 20.0) {
			// slow down the turn.
			Robot.driveTrain.TurnToSetpoint(this.driveMagnitude / 3.0);
			negativeEnergyCounter = 0;	// Reset.
		} else if (diff < 30.0) {
			// slow down the turn.
			Robot.driveTrain.TurnToSetpoint(this.driveMagnitude / 2.0);
			negativeEnergyCounter = 0;	// Reset.
		} else {
			Robot.driveTrain.TurnToSetpoint(this.driveMagnitude);
			negativeEnergyCounter = 0;	// Reset.
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// Let the robot settle at the target.
		if (iterationsAtTarget < 40)
			return false;
		
		double currentTime = Timer.getFPGATimestamp();

		double degreesFromTargetAngle = degreesWhenFinished - Robot.driveTrain.getAngle();

		if (Math.abs(degreesFromTargetAngle) < 10.0) {
			System.out.println("Turn command finished. Current Degrees: " + Robot.driveTrain.getAngle() + " Off from Target: "
					+ degreesFromTargetAngle);
			return true;
		}

		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Turn command end.");
		Robot.driveTrain.Stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
