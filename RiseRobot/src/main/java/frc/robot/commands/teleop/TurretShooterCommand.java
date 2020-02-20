package frc.robot.commands.teleop;

import static edu.wpi.first.wpilibj.Timer.getFPGATimestamp;
import static frc.robot.OI.barfButton;
import static frc.robot.OI.gamepad;
import static frc.robot.OI.shootButton;
import static frc.robot.Robot.intakeConveyor;
import static frc.robot.Robot.turretShooter;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeConveyor;
import frc.utils.LimelightHelper;

public class TurretShooterCommand extends CommandBase {

  private static final double JOYSTICK_DEADBAND = 0.125;
  private static final int BARF_SPEED = 500;
  protected static int targetSpeed = 0;

  private static final int SPEED_ERROR = 50;
  private static final double SPEED_ERROR_TIME_LIMIT = IntakeConveyor.PULSE_TIME * 0.9;

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

    // If the flywheel state has changed, run the new state's initialize method
    if (previousFlywheelState != currentFlywheelState) {
      if (shootButton.get()) {
        // If shooting, estimate the target speed
        targetSpeed = (int) turretShooter.estimateTargetSpeed(distanceToTarget);
      }
    } else if (barfButton.get()) {
      targetSpeed = BARF_SPEED;
    } else {
      targetSpeed = 0;
      // Run the initialize method and update previousFlywheelState
      currentFlywheelState.initialize();
      previousFlywheelState = currentFlywheelState;
    }

    // Run the states' execute methods, and update the pointers, as necessary.
    currentFlywheelState = currentFlywheelState.execute();
    currentTurretState = currentTurretState.execute();

    SmartDashboard.putString("Flywheel State", currentFlywheelState.name());
    SmartDashboard.putString("Turret State", currentTurretState.name());

  }


  private enum TurretState {
    MANUAL {
      @Override
      public TurretState execute() {
        double rotateCommand = gamepad.getRightX();
        if (Math.abs(rotateCommand) > JOYSTICK_DEADBAND) {
          turretShooter.setOpenLoopTurretOutput(rotateCommand);
        } else if (LimelightHelper.getTV() == 1) {
          // If it has found a single target, switch
          turretShooter.setOpenLoopTurretOutput(0);
          return TRACKING;
        }
        return this;
      }
    },
    TRACKING {
      @Override
      public TurretState execute() {
        // If it has no targets or more than one, switch
        if (LimelightHelper.getTV() != 1 || Math.abs(gamepad.getRightX()) > JOYSTICK_DEADBAND) {
          turretShooter.setOpenLoopTurretOutput(0);
          return MANUAL;
        }
        // TODO adjust minus/plus to match
        Rotation2d targetAngle =
            Rotation2d.fromDegrees(LimelightHelper.getTX()).minus(turretShooter.getTurretAngle());
        turretShooter.setTurretAngle(targetAngle, false);

        return this;
      }
    };

    public abstract TurretState execute();
  }


  private enum FlywheelState {
    OFF {
      @Override
      public void initialize() {
      }

      @Override
      public FlywheelState execute() {
        if (shootButton.get() || barfButton.get()) {
          return SPINNING_UP;
        }
        return this;
      }
    },
    SPINNING_UP {
      @Override
      public void initialize() {
        turretShooter.isReadyToShoot = false;
      }

      @Override
      public FlywheelState execute() {
        turretShooter.setFlywheelOutput(TalonFXControlMode.Velocity, targetSpeed);

        if (!(shootButton.get() || barfButton.get())) {
          return SPINNING_DOWN;
        }
        if (Math.abs(turretShooter.getFlywheelSpeed() - targetSpeed) < SPEED_ERROR) {
          return SHOOTING;
        }
        return this;
      }
    },
    SHOOTING {
      private double speedErrorTime;

      @Override
      public void initialize() {
        turretShooter.isReadyToShoot = true;
        intakeConveyor.resetBallCount();
        speedErrorTime = getFPGATimestamp();
      }

      @Override
      public FlywheelState execute() {
        turretShooter.setFlywheelOutput(TalonFXControlMode.Velocity, targetSpeed);

        if (!(shootButton.get() || barfButton.get())) {
          return SPINNING_DOWN;
        }
        if (Math.abs(turretShooter.getFlywheelSpeed() - targetSpeed) > SPEED_ERROR) {
          return SPINNING_UP;
        }
        return this;
      }
    },
    SPINNING_DOWN {
      @Override
      public void initialize() {
        turretShooter.isReadyToShoot = false;
      }

      @Override
      public FlywheelState execute() {
        turretShooter.setFlywheelOutput(TalonFXControlMode.PercentOutput, 0);
        if (shootButton.get() || barfButton.get()) {
          return SPINNING_UP;
        }
        if (Math.abs(turretShooter.getFlywheelSpeed()) < SPEED_ERROR) {
          return OFF;
        }
        return this;
      }
    };

    public abstract void initialize();

    public abstract FlywheelState execute();
  }
}

