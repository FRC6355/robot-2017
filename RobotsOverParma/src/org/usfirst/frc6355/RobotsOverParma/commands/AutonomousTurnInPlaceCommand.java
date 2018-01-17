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
public class AutonomousTurnInPlaceCommand extends CommandGroup {

	public static final double kDriveFast = 0.8;
	public static final double kDriveMed = 0.6;
	public static final double kDriveSlow = 0.4;
	public static final double kDriveStopped = 0.0;

	public static final double kInchesPerFoot = 12.0;

    public AutonomousTurnInPlaceCommand(double degreesToTurn) {
    	System.out.println("Autonomous Turn in Place command created.");
    	
    	// Turn to Angle (degrees to turn, speed)
    	this.addSequential(new TurnToAngleCommand(degreesToTurn, kDriveSlow), 15.0);
    }

}