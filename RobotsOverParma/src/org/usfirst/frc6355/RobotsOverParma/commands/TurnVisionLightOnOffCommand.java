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
import edu.wpi.first.wpilibj.command.InstantCommand;

import org.usfirst.frc6355.RobotsOverParma.Robot;
import org.usfirst.frc6355.RobotsOverParma.RobotMap;

/**
 *
 */
public class TurnVisionLightOnOffCommand extends InstantCommand {

	private boolean turnLightOn;

	public TurnVisionLightOnOffCommand(boolean lightState) {
		turnLightOn = lightState;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (turnLightOn) {
			Robot.vision.turnRingLightOn();
		} else {
			Robot.vision.turnRingLightOff();
		}
	}

}