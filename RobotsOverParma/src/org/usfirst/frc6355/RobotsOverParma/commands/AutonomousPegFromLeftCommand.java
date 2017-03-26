// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc6355.RobotsOverParma.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc6355.RobotsOverParma.Robot;
import org.usfirst.frc6355.RobotsOverParma.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 * 
 */
public class AutonomousPegFromLeftCommand extends CommandGroup {

	public static final double kDriveFast = 0.8;
	public static final double kDriveMed = 0.6;
	public static final double kDriveSlow = 0.4;
	public static final double kDriveStopped = 0.0;

	public static final double kInchesPerFoot = 12.0;
	public static final double kMaxInchesToPeg = 5.0 * kInchesPerFoot; 		// 5 feet;
		
	
    public AutonomousPegFromLeftCommand(boolean useVision) {
    	System.out.println("Autonomous Peg from Left command created.");
    	
    	// Drive forward (inches, speed)
    	this.addSequential(new DriveForwardInchesCommand(15.0 * kInchesPerFoot, kDriveMed));
    	
    	// Turn to Angle (degrees to turn, speed)
    	this.addSequential(new TurnToAngleCommand(120.0, kDriveSlow), 7.0);
    	
    	if (useVision){
        	// Turn on the ring light
        	this.addSequential(new TurnVisionLightOnOffCommand(true));

        	// Drive forward (inches, speed)
        	this.addSequential(new DriveToPegWithVisionCommand(kDriveSlow, kMaxInchesToPeg));

        	// Turn off the ring light
        	this.addSequential(new TurnVisionLightOnOffCommand(false));
    	} else {
        	// Drive forward(inches, speed)
        	this.addSequential(new DriveForwardInchesCommand(2.0 * kInchesPerFoot, kDriveMed));
    	}

    	// Pause (so we don't jerk the robot too much.
    	// (seconds, magnitude)
    	this.addSequential(new DriveForwardSecondsCommand(3.0, kDriveStopped));

    	// Drive backwards(inches, speed)
    	this.addSequential(new DriveForwardInchesCommand(1.0 * kInchesPerFoot, -kDriveMed));
    }

}
