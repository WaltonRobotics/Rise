package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.utils.LimelightHelper;

import static frc.robot.OI.gamepad;
import static frc.robot.Robot.turretShooter;

public class TurretShooterCommand extends CommandBase {

    public TurretShooterCommand() {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        boolean isManual = Math.abs(gamepad.getLeftX()) > 0.1;
        boolean hasTarget = LimelightHelper.getTV() > 0;

        if(isManual) {
            turretShooter.setOpenLoopTurretSpeeds(gamepad.getLeftX());

            if(hasTarget) {
                double targetAngle = LimelightHelper.getTX(); //TODO: This is wrong this is angle to turn
                double distanceToTarget = LimelightHelper.getDistanceMeters();
            }
        }

        else {
            if(hasTarget) {
                double targetAngle = LimelightHelper.getTX(); //TODO: This is wrong. This is angle to turn
                double distanceToTarget = LimelightHelper.getDistanceMeters();

                
            }
        }

    }

    @Override
    public void end(boolean interrupted) {

    }
}

