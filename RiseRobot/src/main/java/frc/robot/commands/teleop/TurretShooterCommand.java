package frc.robot.commands.teleop;

import static frc.robot.OI.barfButton;
import static frc.robot.OI.gamepad;
import static frc.robot.OI.shootButton;
import static frc.robot.Robot.turretShooter;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.utils.LimelightHelper;
import frc.utils.map.InterpolatingDouble;

public class TurretShooterCommand extends CommandBase {

  private static final double JOYSTICK_DEADBAND = 0.125;
  private static final double SPEED_ERROR = 50;
  private static final double BARF_SPEED = 500;
  private FlywheelState currentFlywheelState, previousFlywheelState;
  private TurretState currentTurretState;

  public TurretShooterCommand() {
    addRequirements(turretShooter);

    currentFlywheelState = previousFlywheelState = FlywheelState.OFF;
    currentTurretState = TurretState.MANUAL;
  }

  @Override
  public void execute() {

    double distanceToTarget = LimelightHelper.getDistanceMeters();

    if (previousFlywheelState != currentFlywheelState) {
      double speedArg;
      if(shootButton.get()) {
        speedArg = turretShooter.<InterpolatingDouble, InterpolatingDouble>
            estimateTargetSpeed(new InterpolatingDouble(distanceToTarget)).value;
      } else if(barfButton.get()) {
        speedArg = BARF_SPEED;
      } else {
        speedArg = 0;
      }
      currentFlywheelState.initialize(speedArg);
    }

    currentFlywheelState = currentFlywheelState.execute();
    currentTurretState = currentTurretState.execute();

  }


  private enum TurretState {
    MANUAL {
      @Override
      public TurretState execute() {
        double rotateCommand = gamepad.getRightX();
        if (Math.abs(rotateCommand) > JOYSTICK_DEADBAND) {
          turretShooter.setOpenLoopTurretOutput(rotateCommand);
        }
        if (LimelightHelper.getTV() == 1) {
          turretShooter.setOpenLoopTurretOutput(0);
          return TRACKING;
        }
        return this;
      }
    },
    TRACKING {
      @Override
      public TurretState execute() {
        Rotation2d targetAngle =
            Rotation2d.fromDegrees(LimelightHelper.getTX()).minus(turretShooter.getTurretAngle());
        turretShooter.setTurretAngle(targetAngle, false);

        if (LimelightHelper.getTV() != 1) {
          turretShooter.setOpenLoopTurretOutput(0);
          return MANUAL;
        }
        return this;
      }
    };

    public abstract TurretState execute();
  }


  private enum FlywheelState {
    OFF {
      @Override
      public void initialize(Object... args) {
      }

      @Override
      public FlywheelState execute() {
        return this;
      }
    },
    SPINNING_UP {
      double flywheelSpeed;

      /**
       * @param args the first arg should be the target flywheel speed
       */
      @Override
      public void initialize(Object... args) {
        flywheelSpeed = (Double) args[0];
        turretShooter.isReadyToShoot = false;
      }

      @Override
      public FlywheelState execute() {
        turretShooter.setFlywheelOutput(TalonFXControlMode.Velocity, flywheelSpeed);

        if(!shootButton.get() && !barfButton.get()) {
          return SPINNING_DOWN;
        }
        if (Math.abs(turretShooter.getFlywheelSpeed()) < SPEED_ERROR) {
          return SHOOTING;
        }
        return this;
      }
    },
    SHOOTING {
      @Override
      public void initialize(Object... args) {
        turretShooter.isReadyToShoot = true;
      }

      @Override
      public FlywheelState execute() {
        if(!shootButton.get() && !barfButton.get()) {
          return SPINNING_DOWN;
        }
        if (Math.abs(turretShooter.getFlywheelSpeed()) < SPEED_ERROR) {
          return SPINNING_UP;
        }
        return this;
      }
    },
    SPINNING_DOWN {
      @Override
      public void initialize(Object... args) {
        turretShooter.isReadyToShoot = false;
      }

      @Override
      public FlywheelState execute() {
        turretShooter.setFlywheelOutput(TalonFXControlMode.PercentOutput, 0);

        if (Math.abs(turretShooter.getFlywheelSpeed()) < SPEED_ERROR) {
          return OFF;
        }
        return this;
      }
    };

    public abstract void initialize(Object... args);

    public abstract FlywheelState execute();
  }
}

