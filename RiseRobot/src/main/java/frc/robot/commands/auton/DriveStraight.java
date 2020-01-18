package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.drivetrain;

public class DriveStraight extends CommandBase {

    private PIDController distanceController;
    private double desiredDistance;
    private double power;

    public DriveStraight(double desiredDistance, double power) {
        this.desiredDistance = desiredDistance;
        this.power = power;

        distanceController = new PIDController(1, 0, 0);
    }

    @Override
    public void initialize() {
        drivetrain.reset();
    }

    @Override

    public void execute() {
        double turnRate = distanceController.calculate(drivetrain.getHeading().getDegrees(), 0);

        drivetrain.setArcadeSpeeds(power, turnRate);
    }

    @Override
    public boolean isFinished() {
        return Math.abs((drivetrain.leftMetersTravelled() + drivetrain.rightMetersTravelled()) / 2) >= desiredDistance;
    }
}
