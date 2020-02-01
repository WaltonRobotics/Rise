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
    }

    @Override
    public void initialize() {
        System.out.println("turning to " + targetAngle);

        currentRobot.getTurnPIDController().setSetpoint(targetAngle);
    }

    @Override
    public void execute() {
        double turnRate = -currentRobot.getTurnPIDController().calculate(drivetrain.getHeading().getDegrees());
        drivetrain.setSpeeds(turnRate, -turnRate);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.setSpeeds(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
