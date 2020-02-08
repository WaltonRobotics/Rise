package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class DriveStraight extends CommandBase {

    private double desiredDistance;
    private double power;

    public DriveStraight(double desiredDistance) {
        this.desiredDistance = desiredDistance;
    }

    @Override
    public void initialize() {
        drivetrain.zeroNeoEncoders();
    }

    @Override
    public void execute() {
        double turnRate = -currentRobot.getTurnPIDController().calculate(drivetrain.getHeading().getDegrees(), 0);
        double driveCommand = currentRobot.getDistancePIDController().calculate(getAveragedDistance(), desiredDistance);

        SmartDashboard.putNumber("Turn rate", turnRate);

        drivetrain.setArcadeSpeeds(driveCommand, turnRate);
    }

    @Override
    public boolean isFinished() {
        return currentRobot.getDistancePIDController().atSetpoint();
    }

    private double getAveragedDistance() {
        return Math.abs(drivetrain.getCANEncoderLeftMeters()) + Math.abs(drivetrain.getCANEncoderRightMeters()) / 2;
    }
}
