package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class WaitForTrenchLight extends CommandBase {

    private static final double REQUIRED_TIME = 2.0;

    private Timer timer;

    public WaitForTrenchLight() {
        timer = new Timer();
    }

    @Override
    public void initialize() {
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return timer.get() > REQUIRED_TIME;
    }

}
