package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.spinner;

public class StopSpinning extends CommandBase {

    public StopSpinning() {
        addRequirements(spinner);
    }

    @Override
    public void initialize() {
        // TODO: Stop spinning control panel here
    }

}
