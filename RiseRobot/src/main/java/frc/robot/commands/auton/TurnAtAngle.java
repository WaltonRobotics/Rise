package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.drivetrain;

public class TurnAtAngle extends CommandBase {
    private double targetAngle;
    private PIDController turnController;

    public TurnAtAngle(double targetAngle){
        drivetrain.motorSetUp();
        addRequirements(drivetrain);
        this.targetAngle = targetAngle;

        turnController = new PIDController(0.008, 0, 0);
        turnController.enableContinuousInput(-180f, 180f);
        turnController.setTolerance(2.0);
    }

    @Override
    public void initialize() {
        turnController.setSetpoint(targetAngle);
        drivetrain.reset();
    }

    @Override
    public void execute(){
        double turnRate = turnController.calculate(drivetrain.getHeading().getDegrees());
        SmartDashboard.putNumber("Turn rate", turnRate);
        drivetrain.setSpeeds(-turnRate, turnRate);
    }

    @Override
    public boolean isFinished() {
        return turnController.atSetpoint();
    }
}
