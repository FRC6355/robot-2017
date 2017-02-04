package org.usfirst.frc.team6355.btt.robot;

import org.usfirst.frc.team6355.btt.robot.commands.Collect;
import org.usfirst.frc.team6355.btt.robot.commands.DriveForward;
import org.usfirst.frc.team6355.btt.robot.commands.LowGoal;
import org.usfirst.frc.team6355.btt.robot.commands.SetCollectionSpeed;
import org.usfirst.frc.team6355.btt.robot.commands.SetPivotSetpoint;
import org.usfirst.frc.team6355.btt.robot.commands.Shoot;
import org.usfirst.frc.team6355.btt.robot.subsystems.Collector;
import org.usfirst.frc.team6355.btt.robot.subsystems.Pivot;
import org.usfirst.frc.team6355.btt.robot.triggers.DoubleButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The operator interface of the robot, it has been simplified from the real
 * robot to allow control with a single PS3 joystick. As a result, not all
 * functionality from the real robot is available.
 */
public class OI {
	public Joystick joystick = new Joystick(0);

	public OI() {
		new JoystickButton(joystick, 12).whenPressed(new LowGoal());
		new JoystickButton(joystick, 10).whenPressed(new Collect());

		new JoystickButton(joystick, 11).whenPressed(new SetPivotSetpoint(Pivot.kShoot));
		new JoystickButton(joystick, 9).whenPressed(new SetPivotSetpoint(Pivot.kShootNear));

		new DoubleButton(joystick, 2, 3).whenActive(new Shoot());

		// SmartDashboard Buttons
		SmartDashboard.putData("Drive Forward", new DriveForward(2.25));
		SmartDashboard.putData("Drive Backward", new DriveForward(-2.25));
		SmartDashboard.putData("Start Rollers", new SetCollectionSpeed(Collector.kForward));
		SmartDashboard.putData("Stop Rollers", new SetCollectionSpeed(Collector.kStop));
		SmartDashboard.putData("Reverse Rollers", new SetCollectionSpeed(Collector.kReverse));
	}

	public Joystick getJoystick() {
		return joystick;
	}
}
