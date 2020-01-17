package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.drivetrain;

public class TurnAtAngle extends CommandBase {
    private double targetAngle;
    private double turnRate;
    public static PIDController turnController;

    public TurnAtAngle(double targetAngle){
        addRequirements(drivetrain);
        this.targetAngle = targetAngle;

        turnController = new PIDController(1, 0, 0);
        turnController.enableContinuousInput(-180f, 180f);
        turnController.setTolerance(2.0);
    }

    @Override
    public void initialize() {
        turnController.setSetpoint(targetAngle);
    }

    @Override
    public void execute(){
        turnRate = turnController.calculate(drivetrain.getHeading().getDegrees());
        drivetrain.setArcadeSpeeds(0, turnRate);
    }

    @Override
    public boolean isFinished() {
        return turnController.atSetpoint();
    }
}
