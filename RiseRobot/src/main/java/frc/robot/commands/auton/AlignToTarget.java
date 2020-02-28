package frc.robot.commands.auton;

import frc.utils.LimelightHelper;

import static frc.robot.Robot.drivetrain;

public class AlignToTarget extends TurnToDynamicAngle {

    public AlignToTarget() {
        super(() -> LimelightHelper.getTX() + drivetrain.getHeading().getDegrees());
    }

}
