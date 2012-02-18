/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author Kevin Wang
 */
public class FlywheelRun extends CommandBase {

    double distanceInches;
    double[] speeds;
    
    public FlywheelRun(double distanceInches, double[] speeds) {
        System.out.println("FLywheel constructed");
        requires(flywheel);
        setDistanceInches(distanceInches);
        this.speeds = speeds;
        System.out.println("distance inches: " + distanceInches);
    }
    
    public void setDistanceInches(double distanceInches) {
        this.distanceInches = distanceInches;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double[] rpm = flywheel.lookupRPM(distanceInches, speeds);
        flywheel.setFlywheelSpeeds(rpm[0], rpm[1]);
        System.out.println("flywheel speeds aer " + rpm[0]);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        flywheel.setFlywheelSpeeds(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
