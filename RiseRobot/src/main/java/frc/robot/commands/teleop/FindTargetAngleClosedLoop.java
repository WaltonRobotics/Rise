package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.utils.LimelightHelper;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class FindTargetAngleClosedLoop extends CommandBase {

    @Override
    public void initialize() {
        System.out.println("command initialized");
    }

    @Override
    public void execute() {
        double tx = LimelightHelper.getTX();
        double steeringAdjust = 0.0;

        if (tx > 1.0) {
            steeringAdjust = currentRobot.getVisionAlignKp() * tx + currentRobot.getVisionAlignKs();
        } else if (tx < 1.0) {
            steeringAdjust = currentRobot.getVisionAlignKp() * tx - currentRobot.getVisionAlignKs();
        }

        drivetrain.setSpeeds(steeringAdjust, -steeringAdjust);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.setSpeeds(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(LimelightHelper.getTX()) <= currentRobot.getVisionAlignTxTolerance();
    }
}
