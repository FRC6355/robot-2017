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

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;


/**
 * Controls the vision subsystem, which includes
 * a Raspberry PI, a ring light, and a feed of data
 * from a network table.
 */
public class Vision extends Subsystem {
	public static final String kNetworkTableNameGear = "vision/gear";
	public static final String kNetworkTableValueGear_X = "x";
	public static final String kNetworkTableValueGear_Y = "y";
	public static final String kNetworkTableValueGear_Angle = "angle";
	
	public static final double kMinXValue = -120.0; 	// 10'
	public static final double kMaxXValue = 120.0; 		// 10'
	public static final double kMinYValue = -120.0; 	// 10'
	public static final double kMaxYValue = 120.0; 		// 10'

	private NetworkTable table;
    private final Relay visionRingLightRelay = RobotMap.visionRingLightRelay;

	@Override
	protected void initDefaultCommand() {
	}
	
	public void init(){
		table = NetworkTable.getTable(kNetworkTableNameGear);
	}
    
	public void turnRingLightOn(){
		visionRingLightRelay.set(Value.kOn);
	}
	
	public void turnRingLightOff(){
		visionRingLightRelay.set(Value.kOff);
	}
	
	public boolean getIsVisionInRange(){
		double x = table.getNumber(kNetworkTableValueGear_X, -1000);
		
		if (x == -1000){
			return false;
		}
		
		return true;
	}
	
	public double getVisionXOffset(){
		double x = table.getNumber(kNetworkTableValueGear_X, 0.0);
		
		if (x < kMinXValue || x > kMaxXValue)
			return 0.0;
		
		return x;
	}

	public double getVisionYOffset(){
		double y = table.getNumber(kNetworkTableValueGear_Y, 0.0);
		
		if (y < kMinYValue || y > kMaxYValue)
			return 0.0;
		
		return y;
	}

	public double getVisionAngleOffset(){
		double angle = table.getNumber(kNetworkTableValueGear_Angle, 0.0);
		return angle % 360.0;
	}

}

