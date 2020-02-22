package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class TurnAtAngle extends CommandBase {

    private double targetAngle;

    public TurnAtAngle(double targetAngle) {
        this.targetAngle = targetAngle;
    }

    @Override
    public void initialize() {
        currentRobot.getTurnPIDController().reset(new TrapezoidProfile.State(drivetrain.getHeading().getDegrees(), drivetrain.getAngularVelocity()));
    }

    @Override
    public void execute() {
        double turnRate = -currentRobot.getTurnPIDController().calculate(drivetrain.getHeading().getDegrees(),
                drivetrain.getHeading().plus(Rotation2d.fromDegrees(targetAngle)).getDegrees());
        System.out.println("turning rate" + turnRate);
        System.out.println("Target angle" + drivetrain.getHeading().plus(Rotation2d.fromDegrees(targetAngle)).getDegrees());
        drivetrain.setDutyCycles(turnRate, -turnRate);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.setDutyCycles(0, 0);
    }

    @Override
    public boolean isFinished() {
        return currentRobot.getTurnPIDController().atGoal();
    }
}
