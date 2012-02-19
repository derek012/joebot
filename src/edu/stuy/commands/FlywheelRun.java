/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Kevin Wang
 */
public class FlywheelRun extends CommandBase {

    double distanceInches;
    double[] speeds;
    boolean automatic;
    boolean isFMSAttached = false;
    
    public FlywheelRun(double distanceInches, double[] speeds) {
        requires(flywheel);
        setDistanceInches(distanceInches);
        this.speeds = speeds;
        automatic = false;
    }

    public FlywheelRun() {
        requires(flywheel);
        this.speeds = Flywheel.speedsTopHoop;
        automatic = true;
    }
    
    private void setDistanceInches(double distanceInches) {
        this.distanceInches = distanceInches;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // SmartDashboard should override OI or passed-in distance when tuning
        if (useSmartDashboardTuning()) {
                tuneShooter();
                return;
        }
        if (automatic) {
            setDistanceInches(CommandBase.oi.getDistanceFromDistanceButton());
            if (CommandBase.oi.getHoopHeightButton()) {
                this.speeds = Flywheel.speedsTopHoop;
            } else {
                this.speeds = Flywheel.speedsMiddleHoop;
            }
        }
        double[] rpm = flywheel.lookupRPM(distanceInches, speeds);
        flywheel.setFlywheelSpeeds(rpm[0], rpm[1]);
        SmartDashboard.putDouble("setRPMtop", rpm[0]);
        SmartDashboard.putDouble("setRPMottom", rpm[1]);
    }

    private boolean useSmartDashboardTuning() {
        if (isFMSAttached || DriverStation.getInstance().isFMSAttached()) {
            isFMSAttached = true;
            return false;
        }
        boolean useSmartDashboardTuning = false;
        try {
            try {
                useSmartDashboardTuning = SmartDashboard.getBoolean("useSDBtuning");
            } catch (NetworkTableKeyNotDefined e) {
                useSmartDashboardTuning = false;
                SmartDashboard.putBoolean("useSDBtuning", false);
            }
        }
        catch (Exception e) { // Don't use SmartDashboard if anything went wrong
                              // (i.e. SmartDashboard throws another Exception)
        }
        return useSmartDashboardTuning;
    }

    private void tuneShooter() {
        double setRpmTop = 0;
        double setRpmBottom = 0;
        try {
            setRpmTop = SmartDashboard.getDouble("setRPMtop");
            setRpmBottom = SmartDashboard.getDouble("setRPMbottom");
        } catch (NetworkTableKeyNotDefined e) {
            SmartDashboard.putDouble("setRPMtop", 0);
            SmartDashboard.putDouble("setRPMbottom", 0);
        }
        CommandBase.flywheel.setFlywheelSpeeds(setRpmTop, setRpmBottom);


        double rpmTop = Flywheel.upperRoller.getRPM();
        double rpmBottom = Flywheel.lowerRoller.getRPM();
        Flywheel.upperRoller.setPID("upper");
        Flywheel.lowerRoller.setPID("lower");
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
