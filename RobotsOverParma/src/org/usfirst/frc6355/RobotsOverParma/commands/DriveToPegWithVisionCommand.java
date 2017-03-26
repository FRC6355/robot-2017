package org.usfirst.frc6355.RobotsOverParma.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc6355.RobotsOverParma.Robot;
import org.usfirst.frc6355.RobotsOverParma.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class DriveToPegWithVisionCommand extends Command {
	
	private double startTime = 0.0;
	private double driveForwardSeconds = 10.0;	// Max time allowed to drive forward.
	private double driveForwardMagnitude = 0.0;
	private double maxInchesToDrive = 0.0;
	private double initialDistanceValueLeft = 0.0;
	private double initialDistanceValueRight = 0.0;
	
	private double lastVisionSnapshotTime = 0.0;		// When was the last time we checked our heading.
	private double secondsBetweenVisionSnapshots = 0.1;	// Check the vision 2x per second.

	private double initialVisionDistanceValue = -1.0; 	// How far away does vision think we are.
	private double initialEncoderDistanceValue = -1.0; 	// What was the initial encoder reading.
	//private double visionDistanceFactor = 1.0;			// What is the ration between the vision "X" and the Encoder inches.
	//private double visionDerivedTargetInches = 0.0;		// How far should we drive forward based on vision.
	private double visionDistanceSetpoint = 0.0;		// The estimated value of the encoder distance value when at the peg.

	
    public DriveToPegWithVisionCommand(double forwardMagnitude, double maxInchesToDrive) {
    	requires(Robot.driveTrain);
    	System.out.println("Drive To Peg with Vision created.");
    	this.driveForwardMagnitude = forwardMagnitude;
    	this.maxInchesToDrive = maxInchesToDrive;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Reset the navx and turn assist.
		Robot.driveTrain.Stop();

		startTime = Timer.getFPGATimestamp();	// seconds.
    	System.out.println("Drive Fwd init " + startTime + " Magnitude: " + driveForwardMagnitude);
    	
    	initialDistanceValueLeft = Robot.driveTrain.getLeftWheelDistance();
    	initialDistanceValueRight = Robot.driveTrain.getRightWheelDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	// Check if we can / should apply vision correction to the driving.
    	if (Robot.vision.getIsVisionInRange()){
        	System.out.println("Vision: VisionAngle " + Robot.vision.getVisionAngleOffset() 
									+ " NavX Angle: " + Robot.driveTrain.getAngle()
									+ " DriveSP: " + Robot.driveTrain.getTargetAngle()
        							+ " Y: " + Robot.vision.getVisionYOffset()
        							+ " lVST: " + lastVisionSnapshotTime
        							+ " moved: " + this.getDistanceMoved()
        							+ " distSP: " + visionDistanceSetpoint
        			);

        	double currentTime = Timer.getFPGATimestamp();

        	// Check if "secondsBetweenVisionSnapshots" has passed since the last snapshot. 
        	if (lastVisionSnapshotTime == 0.0 || currentTime > lastVisionSnapshotTime + secondsBetweenVisionSnapshots) {
        		// Snapshot the vision again and adjust the target angle.
        		double visionAngle = Robot.vision.getVisionAngleOffset();
        		Robot.driveTrain.setAngle(visionAngle); // * -1.0);
        		
            	System.out.println("UPDATING SNAPSHOT: Vision: Angle " + Robot.vision.getVisionAngleOffset() 
            						+ " Y: " + Robot.vision.getVisionYOffset());

            	if (initialVisionDistanceValue <= -1.0) {
        			// This is the first time vision is active. Capture the current "Y";
        			initialVisionDistanceValue = Robot.vision.getVisionYOffset();
        			initialEncoderDistanceValue = this.getDistanceMoved();

        			visionDistanceSetpoint = this.getDistanceMoved() + initialVisionDistanceValue;
        		} else {
            		// Calculate the distance factor.
        			// This is used later, when we are too close for vision to work.
        			double deltaVision = Math.max(1.0, initialVisionDistanceValue - Robot.vision.getVisionYOffset());
        			double deltaEncoder = Math.max(1.0, (this.getDistanceMoved() - initialEncoderDistanceValue));
        			double visionDistanceFactor = deltaEncoder / deltaVision;
        			
        			
        			// Calculate how many more inches to move based on vision.
        			double visionDerivedTargetInches = Robot.vision.getVisionYOffset() * visionDistanceFactor;
        			
        			visionDistanceSetpoint = this.getDistanceMoved() + visionDerivedTargetInches;

        			System.out.println("deltaVison: " + deltaVision + " deltaEncoder: " + deltaEncoder 
        							+ " visionDistanceFactor: " + visionDistanceFactor + " visionDistanceSetpoint: " 
        							+ visionDistanceSetpoint);
        		}
        		
        		lastVisionSnapshotTime = currentTime;
        	}

        	Robot.driveTrain.DriveAndTurnToSetpoint(driveForwardMagnitude);
    		
    	} else {
    		System.out.println("Vision no longer active.");
    		// Vision no longer in range. Drive straight the remaining distance.
        	Robot.driveTrain.Drive(driveForwardMagnitude, 0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// Check if we have moved far enough.
    	double avgDistance = getDistanceMoved();
    	
    	System.out.println("DTPV: IsFinished: avgDistance: " + avgDistance + " visionDistanceSetpoint: " + visionDistanceSetpoint);
    	
    	if (avgDistance >= this.maxInchesToDrive) {
    		// We reached the max we can drive in this mode.
        	System.out.println("Drive To Peg wtih Vision finished. Reached max distance: " + maxInchesToDrive);
    		return true;
    	}
    	
    	if (avgDistance > visionDistanceSetpoint) {
    		// We drove as far as the vision algorithm said we should.
        	System.out.println("Drive To Peg wtih Vision finished. Reached target distance: " + visionDistanceSetpoint);
    		return true;
    	}

    	// Check if we have reached the max amount of time for this command to run.
    	double currentTime = Timer.getFPGATimestamp();
    	if (startTime + driveForwardSeconds < currentTime){
        	System.out.println("Drive To Peg wtih Vision finished. StartTime: " 
        									+ startTime + " DriveSeconds: "
        									+ driveForwardSeconds + " CurrentTime: "
        									+ currentTime);
    		return true;	// Reached time limit.
    	}
    	
        return false;
    }



    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Drive To Peg wtih Vision end.");
    	Robot.driveTrain.Stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }


	private double getDistanceMoved() {
		double currentLeft = Robot.driveTrain.getLeftWheelDistance();
    	double currentRight = Robot.driveTrain.getRightWheelDistance();
    	
    	double avgDistance = Math.abs(
    							(
    									(currentLeft - initialDistanceValueLeft)
    									+ 
    									(currentRight - initialDistanceValueRight)
								) / 2.0);
		return avgDistance;
	}

}
