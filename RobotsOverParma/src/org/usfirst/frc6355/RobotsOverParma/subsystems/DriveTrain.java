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

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class DriveTrain extends Subsystem implements PIDOutput {
	static final double kP = 0.03;
    static final double kI = 0.00;
    static final double kD = 0.00;
    static final double kF = 0.00;
    static final double kToleranceDegrees = 2.0f;
    double rotateToAngleRate = 0.00; 
    boolean isDriveStraightMode = false;
    

    static PIDController turnController;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController leftFrontSpeedControler = RobotMap.driveTrainLeftFrontSpeedControler;
    private final SpeedController leftRearSpeedControler = RobotMap.driveTrainLeftRearSpeedControler;
    private final SpeedController rightFrontSpeedControler = RobotMap.driveTrainRightFrontSpeedControler;
    private final SpeedController rightRearSpeedControler = RobotMap.driveTrainRightRearSpeedControler;
    private final RobotDrive robotDrive4 = RobotMap.driveTrainRobotDrive4;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public DriveTrain(){
    	turnController = new PIDController(kP, kI, kD, kF, RobotMap.ahrs, this);
        turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-1.0, 1.0);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setContinuous(true);
        turnController.disable();
	
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new DriveWithJoystickComand());
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
    public void arcadeDrive(Joystick joy) {
    	//robotDrive4.arcadeDrive(joy);

    	if(joy.getAxis(AxisType.kY) < -0.1 || joy.getAxis(AxisType.kY) >0.1)
    	{
	    	double xAxis = joy.getAxis(AxisType.kX);
	    	if(-0.1 < xAxis && xAxis < 0.1){
	    		if(!isDriveStraightMode){
	    			setAngleToCurrentAngle();
	    		}
	    		SmartDashboard.putNumber("Robot Drive Correction", rotateToAngleRate);
	    		SmartDashboard.putBoolean("Robot Drive Correction Active", true);
	    		robotDrive4.arcadeDrive(joy.getAxis(AxisType.kY), joy.getAxis(AxisType.kX) + rotateToAngleRate);
	    	}
	    	else{
	    		SmartDashboard.putBoolean("Robot Drive Correction Active", false);
	    		robotDrive4.arcadeDrive(joy.getAxis(AxisType.kY), joy.getAxis(AxisType.kX));
	    		isDriveStraightMode = false;
	    	}
    	}
    	else{
    		SmartDashboard.putBoolean("Robot Drive Correction Active", false);
    		robotDrive4.arcadeDrive(joy.getAxis(AxisType.kY), joy.getAxis(AxisType.kX));
    		isDriveStraightMode = false;
    	}
    }
    
    
    public void arcadeDriveForwardOnly(Joystick joy)
    {
    	robotDrive4.arcadeDrive(joy.getAxis(AxisType.kY), 0);
    	
    }
    public void setAngleToCurrentAngle(){
    	isDriveStraightMode = true;
		// Acquire current yaw angle, using this as the target angle.
    	turnController.reset();
		turnController.setSetpoint(RobotMap.ahrs.getAngle());
		SmartDashboard.putNumber("Robot Drive Angle", RobotMap.ahrs.getAngle());
		rotateToAngleRate = 0; // This value will be updated in the pidWrite() method.
			
		if(!turnController.isEnabled()) {
			turnController.enable();
		}
		
    	
    }
    public void setAngle(double offset){
    	
			// Acquire current yaw angle, using this as the target angle.
			turnController.setSetpoint(RobotMap.ahrs.getYaw()+offset);
			rotateToAngleRate = 0; // This value will be updated in the pidWrite() method.
		if(!turnController.isEnabled()) {
			turnController.enable();
		}
    	
    }


    @Override
    /* This function is invoked periodically by the PID Controller, */
    /* based upon navX MXP yaw angle input and PID Coefficients.    */
    public void pidWrite(double output) {
        rotateToAngleRate = output;
        
        if(rotateToAngleRate > 0.2){
        	rotateToAngleRate = 0.2;
        }else if(rotateToAngleRate<-0.2){
        	rotateToAngleRate = -0.2;
        }
    }
		
	
}


