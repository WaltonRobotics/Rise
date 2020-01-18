package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class DriveStraight extends CommandBase {

    private double desiredDistance;
    private double power;

    public DriveStraight(double desiredDistance, double power) {
        this.desiredDistance = desiredDistance;
        this.power = power;
    }

    @Override
    public void initialize() {
        drivetrain.reset();
    }

    @Override

    public void execute() {
        double turnRate = -currentRobot.getDistancePIDController().calculate(drivetrain.getHeading().getDegrees(), 0);

        SmartDashboard.putNumber("Turn rate", turnRate);
        SmartDashboard.putNumber("Left meters", drivetrain.leftMetersTravelled());
        SmartDashboard.putNumber("Right meters", drivetrain.rightMetersTravelled());
        drivetrain.setArcadeSpeeds(power, turnRate);
    }

    @Override
    public boolean isFinished() {
        return Math.abs((drivetrain.leftMetersTravelled() + drivetrain.rightMetersTravelled()) / 2) >= desiredDistance;
    }
}
