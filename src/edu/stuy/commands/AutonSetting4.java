/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author 694
 */
import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting4 extends CommandGroup {

    /**
     * Shoots at key. Then backs up and knocks down bridge.
     */
    public AutonSetting4() {
        double distanceInches = Flywheel.distances[Flywheel.KEY_INDEX];
        addParallel(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
        addSequential(new ConveyAutomatic(Autonomous.CONVEY_AUTO_TIME));
        
        addSequential(new TusksExtend());
        addSequential(new AutonBackUpToBridge(Autonomous.INCHES_TO_BRIDGE - Autonomous.INCHES_TO_FENDER));
        addSequential(new TusksRetract());
    }
}
