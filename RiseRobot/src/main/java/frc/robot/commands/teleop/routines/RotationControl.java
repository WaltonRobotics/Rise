package frc.robot.commands.teleop.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.DoRotation;

public class RotationControl extends SequentialCommandGroup {

    public RotationControl() {
        addCommands(new DoRotation());
    }

}
