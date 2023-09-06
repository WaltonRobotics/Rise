package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.OI.leftJoystick;
import static frc.robot.OI.rightJoystick;
import static frc.robot.OI.driver;
import static frc.robot.Robot.controller;
import static frc.robot.Robot.arcade;
import static frc.robot.Robot.drivetrain;

public class DriveCommand extends CommandBase {

    private double deadBand = 0.1;

    public DriveCommand() {
        addRequirements(drivetrain);
    }

    public double getLeftJoystickY() {
        return Math.abs(leftJoystick.getY()) > deadBand ? -leftJoystick.getY() : 0;
    }

    public double getRightJoystickY() {
        return Math.abs(rightJoystick.getY()) > deadBand ? -rightJoystick.getY() : 0;
    }

    public double getRightJoystickX() {
        return Math.abs(rightJoystick.getX()) > deadBand ? -rightJoystick.getX() : 0;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        // (Joysticks inverted because limelight is facing backwards)

//        if(turnToTargetButton.get()) {
//            double steerCmd = LimelightHelper.getTX() * 0.05;
//            drivetrain.setArcadeSpeeds(0 , steerCmd);
//            System.out.println(steerCmd);
//        }
////
//        else {
        if (controller) {
            if (arcade) {
                drivetrain.setArcadeSpeeds(driver.getLeftY() * 0.7, driver.getRightX() * 0.7);
            } else {
                drivetrain.setDutyCycles(driver.getRightY() * 0.7, driver.getLeftY() * 0.7);
            }
        } else { 
            if (arcade) {
                drivetrain.setArcadeSpeeds(-getLeftJoystickY() * 0.7, -getRightJoystickX() * 0.7);
            } else {
                drivetrain.setDutyCycles(-getRightJoystickY() * 0.7, -getLeftJoystickY() * 0.7);
            }
        }
//        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
