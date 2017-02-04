package org.usfirst.frc.team6355.btt.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;

import org.usfirst.frc.team6355.btt.robot.Robot;

/**
 * Close the claw.
 *
 * NOTE: It doesn't wait for the claw to close since there is no sensor to
 * detect that.
 */
public class CloseClaw extends InstantCommand {

	public CloseClaw() {
		requires(Robot.collector);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.collector.close();
	}
}
