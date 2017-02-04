package org.usfirst.frc.team6355.btt.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team6355.btt.robot.subsystems.Collector;
import org.usfirst.frc.team6355.btt.robot.subsystems.Pivot;

/**
 * Get the robot set to collect balls.
 */
public class Collect extends CommandGroup {
	public Collect() {
		addSequential(new SetCollectionSpeed(Collector.kForward));
		addParallel(new CloseClaw());
		addSequential(new SetPivotSetpoint(Pivot.kCollect));
		addSequential(new WaitForBall());
	}
}
