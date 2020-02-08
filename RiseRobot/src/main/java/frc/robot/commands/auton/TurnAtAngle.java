package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class TurnAtAngle extends CommandBase {
    private double targetAngle;

    public TurnAtAngle(double targetAngle) {
        addRequirements(drivetrain);

        this.targetAngle = targetAngle;

        drivetrain.motorSetUp();

        currentRobot.getTurnPIDController().enableContinuousInput(-180f, 180f);
        currentRobot.getTurnPIDController().setTolerance(2.0);
    }

    @Override
    public void initialize() {
        currentRobot.getTurnPIDController().setSetpoint(targetAngle);
        drivetrain.reset();
    }

    @Override
    public void execute() {
        double turnRate = -currentRobot.getTurnPIDController().calculate(drivetrain.getHeading().getDegrees());
        SmartDashboard.putNumber("Turn rate", turnRate);
        drivetrain.setArcadeSpeeds(0, turnRate);
    }

    @Override
    public boolean isFinished() {
        return currentRobot.getTurnPIDController().atSetpoint();
    }
}
