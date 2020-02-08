package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class ShiftUp extends SequentialCommandGroup {

    public ShiftUp() {
        addCommands(new InstantCommand(() -> drivetrain.shiftUp(), drivetrain),
                new WaitCommand(currentRobot.getMinimumShiftingTime()));
    }

}
