package frc.robot.commands.teleop;

import static frc.robot.OI.gamepad;
import static frc.robot.Robot.turretShooter;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.utils.LimelightHelper;

public class TurretShooterCommand extends CommandBase {

  public enum ShooterState {
    MANUAL {
      @Override
      public void initialize() {

      }

      @Override
      public void execute() {

      }
    },
    TRACKING {
      @Override
      public void initialize() {

      }

      @Override
      public void execute() {

      }
    },
    SPINNING_UP {
      @Override
      public void initialize() {

      }

      @Override
      public void execute() {

      }
    },
    SHOOTING {
      @Override
      public void initialize() {

      }

      @Override
      public void execute() {

      }
    },
    SPINNING_DOWN {
      @Override
      public void initialize() {

      }

      @Override
      public void execute() {

      }
    };

    public abstract void initialize();
    public abstract void execute();
  }

  public TurretShooterCommand() {

  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {

//    boolean isManual = Math.abs(gamepad.getLeftX()) > 0.1;
//    boolean hasTarget = LimelightHelper.getTV() > 0;
//
//    if (isManual) {
//      turretShooter.setOpenLoopTurretOutput(gamepad.getLeftX());
//
//      if (hasTarget) {
//        Rotation2d targetAngle = Rotation2d.fromDegrees(LimelightHelper.getTX()).plus();
//        double distanceToTarget = LimelightHelper.getDistanceMeters();
//      }
//    } else {
//      if (hasTarget) {
//        double targetAngle = LimelightHelper.getTX(); //TODO: This is wrong. This is angle to turn
//        double distanceToTarget = LimelightHelper.getDistanceMeters();
//
//
//      }
//    }

  }

  @Override
  public void end(boolean interrupted) {

  }
}

