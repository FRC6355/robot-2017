// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc6355.RobotsOverParma.subsystems;

import org.usfirst.frc6355.RobotsOverParma.RobotMap;
import org.usfirst.frc6355.RobotsOverParma.commands.*;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Shooter extends Subsystem {
	public static final double kDefaultShooterSpeed = 1;
	public static final double kStop = 0;
	

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController leftTalon = RobotMap.shooterLeftTalon;
    private final SpeedController rightTalon = RobotMap.shooterRightTalon;
    private final Servo ballStopperServo = RobotMap.shooterBallStopperServo;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    public void startShooter(){
    	setSpeed(kDefaultShooterSpeed);
    }
    public void setSpeed (double speed){
    	leftTalon.set(speed);
    	rightTalon.set(speed);
    	
    }
    public void increaseSpeed(){
    	//TODO create an if statement to make sure the shooter does not go above the max value
    	leftTalon.set((leftTalon.get() * 1.02));
    	rightTalon.set((rightTalon.get() * 1.02));
    	
    
    }
    
    public void decreaseSpeed(){
    	//TODO create an if statement to make sure the shooter does not go above the max value
    	leftTalon.set((leftTalon.get() * 0.98));
    	rightTalon.set((rightTalon.get() * 0.98));
    	
    
    }
    
    public void stopShooter(){
    	setSpeed(0);
    }
    
   
}

