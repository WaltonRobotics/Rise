package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.auton.TurnAtAngle;
import frc.utils.LimelightHelper;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class FindTargetAngle extends CommandBase {

    private double targetAngle;

    @Override
    public void initialize() {
        System.out.println("command initialized");
        double tx = LimelightHelper.getTX();
        double heading = drivetrain.getHeading().getDegrees();
        targetAngle = heading - tx;
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        if (LimelightHelper.getTV() == 1) {
            System.out.println("Running turn command");
            CommandScheduler.getInstance().schedule(new TurnAtAngle(targetAngle).withTimeout(currentRobot.getMaxAlignmentTime()));
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
