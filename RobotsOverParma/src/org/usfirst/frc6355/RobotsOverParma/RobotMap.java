// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc6355.RobotsOverParma;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// NavX
	public static AHRS ahrs;
	
    public static SpeedController driveTrainLeftFrontSpeedControler;
    public static SpeedController driveTrainLeftRearSpeedControler;
    public static SpeedController driveTrainRightFrontSpeedControler;
    public static SpeedController driveTrainRightRearSpeedControler;
    public static RobotDrive driveTrainRobotDrive4;

    public static SpeedController shooterLeftTalon;
    public static SpeedController shooterRightTalon;

    public static SpeedController ropeClimberReelController;
    public static SpeedController ropeClimberHingeOnReelController;
    
    public static SpeedController intakeIntakeController;
    
    public static Encoder leftDriveTrainEncoder;
    public static Encoder rightDriveTrainEncoder;

    public static Relay visionRingLightRelay;
    

    public static void init() {
    	
    	visionRingLightRelay = new Relay(0, Relay.Direction.kForward);
    	
        driveTrainLeftFrontSpeedControler = new Victor(9);
        LiveWindow.addActuator("DriveTrain", "LeftFrontSpeedControler", (Victor) driveTrainLeftFrontSpeedControler);
        
        driveTrainLeftRearSpeedControler = new Victor(8);
        LiveWindow.addActuator("DriveTrain", "LeftRearSpeedControler", (Victor) driveTrainLeftRearSpeedControler);
        
        driveTrainRightFrontSpeedControler = new Victor(7);
        LiveWindow.addActuator("DriveTrain", "RightFrontSpeedControler", (Victor) driveTrainRightFrontSpeedControler);
        
        driveTrainRightRearSpeedControler = new Victor(6);
        LiveWindow.addActuator("DriveTrain", "RightRearSpeedControler", (Victor) driveTrainRightRearSpeedControler);
        
        driveTrainRobotDrive4 = new RobotDrive(driveTrainLeftFrontSpeedControler, driveTrainLeftRearSpeedControler,
              driveTrainRightFrontSpeedControler, driveTrainRightRearSpeedControler);
        
        leftDriveTrainEncoder = new Encoder(0, 1, true, EncodingType.k4X);
        rightDriveTrainEncoder = new Encoder(2, 3, true, EncodingType.k4X);
        leftDriveTrainEncoder.setDistancePerPulse(0.0203276115743008);		// 0.009239823442864 == factor for 6" wheels	
        rightDriveTrainEncoder.setDistancePerPulse(0.0203276115743008);		// 0.009239823442864 == factor for 6" wheels
        //leftDriveTrainEncoder.setDistancePerPulse(0.009239823442864);		// 0.0123197642824912 == factor for 8" wheels	
        //rightDriveTrainEncoder.setDistancePerPulse(0.009239823442864);		// 0.0123197642824912 == factor for 8" wheels
        LiveWindow.addSensor("DriveTrain", "leftDriveTrainEncoder", leftDriveTrainEncoder);
        LiveWindow.addSensor("DriveTrain", "rightDriveTrainEncoder", rightDriveTrainEncoder);
        
        
        driveTrainRobotDrive4.setSafetyEnabled(true);
        driveTrainRobotDrive4.setExpiration(0.1);
        driveTrainRobotDrive4.setSensitivity(0.5);
        driveTrainRobotDrive4.setMaxOutput(1.0);
        driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

        
        shooterLeftTalon = new Talon(1);
        LiveWindow.addActuator("Shooter", "LeftTalon", (Talon) shooterLeftTalon);
        
        shooterRightTalon = new Talon(2);
        shooterRightTalon.setInverted(true);
        LiveWindow.addActuator("Shooter", "RightTalon", (Talon) shooterRightTalon);
        
        ropeClimberReelController = new Spark(3);
        LiveWindow.addActuator("Rope Climber", "ReelController", (Spark) ropeClimberReelController);
        
        ropeClimberHingeOnReelController = new Spark(4);
        LiveWindow.addActuator("Rope Climber", "HingeOnReelController", (Spark) ropeClimberHingeOnReelController);
        
        intakeIntakeController = new Spark(5);
        LiveWindow.addActuator("Intake", "IntakeController", (Spark) intakeIntakeController);
        

        try {
			/***********************************************************************
			 * navX-MXP:
			 * - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB.            
			 * - See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
			 * 
			 * navX-Micro:
			 * - Communication via I2C (RoboRIO MXP or Onboard) and USB.
			 * - See http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
			 * 
			 * Multiple navX-model devices on a single robot are supported.
			 ************************************************************************/
            ahrs = new AHRS(SPI.Port.kMXP);
            ahrs.reset();
            
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    }
}
