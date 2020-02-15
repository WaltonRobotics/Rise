package frc.robot.command.teleop.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.teleop.DoRotation;
import frc.robot.command.teleop.StopSpinning;
import frc.robot.command.teleop.WaitForTrenchLight;

public class RotationControl extends SequentialCommandGroup {

    public RotationControl() {
        addCommands(new DoRotation());
    }

}
