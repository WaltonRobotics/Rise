package frc.robot.command.teleop.routines;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.teleop.CalibrateColor;
import frc.robot.command.teleop.ToColor;

public class PositionControl extends SequentialCommandGroup {
    private int stepsPerSegment;

    public PositionControl(){
        CalibrateColor calibrateColor = new CalibrateColor();
        addCommands(calibrateColor, new ToColor(calibrateColor.getStepsPerSegment()));
    }
}
