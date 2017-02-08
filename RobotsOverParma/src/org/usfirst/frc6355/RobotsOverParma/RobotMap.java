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

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
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
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static SpeedController driveTrainLeftFrontSpeedControler;
    public static SpeedController driveTrainLeftRearSpeedControler;
    public static SpeedController driveTrainRightFrontSpeedControler;
    public static SpeedController driveTrainRightRearSpeedControler;
    public static RobotDrive driveTrainRobotDrive4;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
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
        
        driveTrainRobotDrive4.setSafetyEnabled(true);
        driveTrainRobotDrive4.setExpiration(0.1);
        driveTrainRobotDrive4.setSensitivity(0.5);
        driveTrainRobotDrive4.setMaxOutput(1.0);
        driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
        driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
        driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveTrainRobotDrive4.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }
}
