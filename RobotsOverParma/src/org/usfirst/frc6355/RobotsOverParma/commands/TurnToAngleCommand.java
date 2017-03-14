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

	public TurnToAngleCommand(double degreesToTurn, double driveMagnitude) {
    	requires(Robot.driveTrain);
		System.out.println("Turn command created.");
		this.degreesToTurn = degreesToTurn;
		this.driveMagnitude = driveMagnitude;
		
    	SmartDashboard.putNumber("Autonomous - Turn Degrees", degreesToTurn);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Try to read the desired turn angle from smart dashboard.
    	degreesToTurn = SmartDashboard.getNumber("Autonomous - Turn Degrees", degreesToTurn);
		
		startTime = Timer.getFPGATimestamp(); // seconds.

		Robot.driveTrain.setAngle(this.degreesToTurn);

		this.degreesWhenFinished = (Robot.driveTrain.getAngle() + this.degreesToTurn) % 360;
		System.out.println("Turn command init. Angle:" + Robot.driveTrain.getAngle() + " \r\nDegrees to Turn: " 
				+ degreesToTurn + ". \r\nDegrees when Done: " + degreesWhenFinished);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveTrain.TurnToSetpoint(this.driveMagnitude);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double currentTime = Timer.getFPGATimestamp();

		double degreesFromTargetAngle = degreesWhenFinished - Robot.driveTrain.getAngle();

		if (Math.abs(degreesFromTargetAngle) < 3.0) {
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
