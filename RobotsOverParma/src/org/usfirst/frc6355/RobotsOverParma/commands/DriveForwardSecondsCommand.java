package org.usfirst.frc6355.RobotsOverParma.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc6355.RobotsOverParma.Robot;
import org.usfirst.frc6355.RobotsOverParma.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class DriveForwardSecondsCommand extends Command {
	
	private double startTime = 0.0;
	private double driveForwardSeconds = 0.0;
	private double driveForwardMagnitude = 0.0;
	
	
    public DriveForwardSecondsCommand(double secondsToDriveForward, double forwardMagnitude) {
    	requires(Robot.driveTrain);
    	System.out.println("Drive Fwd created.");
    	driveForwardSeconds = secondsToDriveForward;
    	driveForwardMagnitude = forwardMagnitude;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Reset the navx and turn assist.
		Robot.driveTrain.Stop();

		startTime = Timer.getFPGATimestamp();	// seconds.
    	System.out.println("Drive Fwd init " + startTime + " Magnitude: " + driveForwardMagnitude);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.Drive(driveForwardMagnitude, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	double currentTime = Timer.getFPGATimestamp();
    	
    	if (startTime + driveForwardSeconds < currentTime){
        	System.out.println("Drive Fwd finished. StartTime: " 
        									+ startTime + " DriveSeconds: "
        									+ driveForwardSeconds + " CurrentTime: "
        									+ currentTime);
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
