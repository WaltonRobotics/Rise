package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.intakeConveyor;

/**
 * Does not end by default. Should be interrupted.
 */
public class EnableIntakeCommand extends CommandBase {
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        intakeConveyor.autoShouldIntake = true;
    }

    @Override
    public void end(boolean interrupted) {
        intakeConveyor.autoShouldIntake = false;
    }
}
