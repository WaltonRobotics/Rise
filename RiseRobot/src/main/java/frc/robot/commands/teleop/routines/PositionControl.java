package frc.robot.commands.teleop.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.teleop.CalibrateColor;
import frc.robot.commands.teleop.ToColor;

public class PositionControl extends SequentialCommandGroup {
    private int stepsPerSegment;

    public PositionControl(){
        CalibrateColor calibrateColor = new CalibrateColor();
        addCommands(calibrateColor, new ToColor(calibrateColor.getStepsPerSegment()));
    }
}
