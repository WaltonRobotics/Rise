package frc.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Paths;
import frc.robot.commands.auton.RamseteTrackingCommand;

import static frc.robot.Robot.drivetrain;

public class TestForward extends SequentialCommandGroup {

    public TestForward() {
        addCommands(
                new InstantCommand(() -> System.out.println("test forward")),
                new WaitCommand(2.0),
                new RamseteTrackingCommand(Paths.TestTrajectories.generateTestForward())
        );
    }
}
