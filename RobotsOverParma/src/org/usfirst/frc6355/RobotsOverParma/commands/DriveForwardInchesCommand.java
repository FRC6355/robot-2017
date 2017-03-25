package org.usfirst.frc6355.RobotsOverParma.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc6355.RobotsOverParma.Robot;
import org.usfirst.frc6355.RobotsOverParma.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class DriveForwardInchesCommand extends Command {
	
	private double startDistance = 0.0;
	private double driveForwardInches = 0.0;
	private double driveForwardMagnitude = 0.0;
	
	
    public DriveForwardInchesCommand(double inchesToDriveForward, double forwardMagnitude) {
    	requires(Robot.driveTrain);
    	System.out.println("Drive Fwd Inches created.");
    	driveForwardInches = inchesToDriveForward;
    	driveForwardMagnitude = forwardMagnitude;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Reset the navx and turn assist.
		Robot.driveTrain.Stop();

		startDistance = Robot.driveTrain.getWheelDistanceAverage();
		
    	System.out.println("Drive Fwd init: From " + startDistance + " Magnitude: " + driveForwardMagnitude);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.Drive(driveForwardMagnitude, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double currentDistance = Robot.driveTrain.getWheelDistanceAverage();
    	double distanceDelta = Math.abs(currentDistance - startDistance);
    	
    	if (distanceDelta > Math.abs(driveForwardInches)){
        	System.out.println("Drive Fwd Inches finished. StartDistance: " 
        									+ startDistance + " DriveInches: "
        									+ driveForwardInches + " CurrentDistance: "
        									+ currentDistance);
    		return true;
    	}    	
    	
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Drive Fwd end.");
    	Robot.driveTrain.Stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
