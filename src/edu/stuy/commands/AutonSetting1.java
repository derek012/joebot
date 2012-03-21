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

public class AutonSetting1 extends CommandGroup {

    /**
     * Shoots from close key. Then backs up and knocks down bridge.
     */
    public AutonSetting1() {
        double distanceInches = Flywheel.distances[Flywheel.CLOSE_KEY_INDEX];
        addParallel(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
        addSequential(new ConveyAutomaticAuton(Autonomous.CONVEY_AUTO_TIME));
        
        addSequential(new TusksExtend());


        addSequential(new AutonBackUpToBridge(Autonomous.t_closeKeyToBridge));
    }
}
