package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class TurnAtAngle extends CommandBase {
    private double targetAngle;

    public TurnAtAngle(double targetAngle) {
        System.out.println("turn at angle" + targetAngle);
        addRequirements(drivetrain);

        this.targetAngle = targetAngle;

        drivetrain.motorSetUp();
    }

    @Override
    public void initialize() {
        currentRobot.getTurnPIDController().setSetpoint(targetAngle);
        drivetrain.zeroHeading();
    }

    @Override
    public void execute() {
        double turnRate = -currentRobot.getTurnPIDController().calculate(drivetrain.getHeading().getDegrees());
        SmartDashboard.putNumber("Turn rate", turnRate);
        SmartDashboard.putNumber("Angle", drivetrain.getHeading().getDegrees());
        SmartDashboard.putNumber("Desired angle", currentRobot.getTurnPIDController().getSetpoint());
        System.out.println(turnRate);
        drivetrain.setSpeeds(turnRate, -turnRate);
    }

    @Override
    public boolean isFinished() {
        return currentRobot.getTurnPIDController().atSetpoint();
    }
}
