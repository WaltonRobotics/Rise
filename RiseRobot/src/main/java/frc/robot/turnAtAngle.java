package frc.robot;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.drivetrain;

public class turnAtAngle extends CommandBase implements PIDOutput {
    private double targetAngle;
    private double turnRate;
    public static PIDController turnController;

    public turnAtAngle(double targetAngle){
        addRequirements(drivetrain);
        this.targetAngle = targetAngle;

        turnController = new PIDController(1, 0, 0, 0, drivetrain.getAhrs(), this);
        turnController.enableContinuousInput(-180f, 180f);
        turnController.setTolerance(2.0);
    }

    @Override
    public void initialize() {
        turnController.setSetpoint(targetAngle);
    }

    @Override
    public void execute(){
        drivetrain.setArcadeSpeeds(0, turnRate);
    }

    @Override
    public boolean isFinished() {
        return turnController.atSetpoint();
    }

    @Override
    public void pidWrite(double output){
        turnRate = output;
    }
}
