package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class DriveAndTurn extends SequentialCommandGroup {

    public DriveAndTurn(){
        addCommands(new ShiftUp(),
                new DriveStraight(1, 1),
                new TurnAtAngle(45));
    }

}
