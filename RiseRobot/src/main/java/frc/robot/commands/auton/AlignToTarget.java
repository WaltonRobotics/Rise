package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class AlignToTarget extends CommandBase {

    private double targetAngle;
    private PIDController turnController;

    public AlignToTarget(DoubleSupplier targetAngleSupplier) {
        addRequirements(drivetrain);
        this.targetAngle = targetAngleSupplier.getAsDouble();
        turnController = new PIDController(0.05, 0, 0); //TODO: Tune but goal is for 1 oscillation
        turnController.setTolerance(1);
    }

    @Override
    public void initialize() {
        turnController.reset();
    }

    @Override
    public void execute() {
        double turnRate = turnController.calculate(drivetrain.getHeading().getDegrees(),
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
