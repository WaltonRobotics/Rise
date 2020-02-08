package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class DropIntake extends SequentialCommandGroup {

    public DropIntake() {
        addCommands(new InstantCommand(() -> System.out.println("Dropping intake")), new WaitCommand(0.001));
    }

}
