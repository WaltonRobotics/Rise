package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.utils.LimelightHelper;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class TurnAtAngle extends CommandBase {
    private double targetAngle;
    private int atTargetCount;

    public TurnAtAngle(double targetAngle) {
        addRequirements(drivetrain);

        this.targetAngle = targetAngle;
    }

    @Override
    public void initialize() {
        System.out.println("turning to " + targetAngle);

        currentRobot.getTurnPIDController().setSetpoint(targetAngle);

        atTargetCount = 0;

        System.out.println("P CONSTANT: " + currentRobot.getTurnPIDController().getP());
        System.out.println("I CONSTANT: " + currentRobot.getTurnPIDController().getI());

        SmartDashboard.putNumber("Turn Setpoint", currentRobot.getTurnPIDController().getSetpoint());
    }

    @Override
    public void execute() {
        double turnRate = -currentRobot.getTurnPIDController().calculate(drivetrain.getHeading().getDegrees());
        SmartDashboard.putNumber("Velocity error", currentRobot.getTurnPIDController().getVelocityError());
        SmartDashboard.putNumber("Position error", currentRobot.getTurnPIDController().getPositionError());
        drivetrain.setSpeeds(turnRate, -turnRate);

        if (currentRobot.getTurnPIDController().atSetpoint()) {
            atTargetCount++;
        } else {
            atTargetCount = 0;
        }

//        if (atTargetCount == 10) {
//            double tx = LimelightHelper.getTX();
//            double heading = drivetrain.getHeading().getDegrees();
//            System.out.println("OLD SETPOINT: " + heading);
//            System.out.println("NEW SETPOINT: " + (heading - tx));
//            currentRobot.getTurnPIDController().setSetpoint(heading - tx);
//            SmartDashboard.putNumber("Turn Setpoint", currentRobot.getTurnPIDController().getSetpoint());
//        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("AT TARGET COUNT: " + atTargetCount);
        drivetrain.setSpeeds(0, 0);
    }

    @Override
    public boolean isFinished() {
        return currentRobot.getTurnPIDController().atSetpoint();
    }
}
