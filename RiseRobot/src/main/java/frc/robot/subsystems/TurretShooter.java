package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_MASTER_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_SLAVE_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_TURRET_ID;
import static frc.robot.Constants.Turret.*;
import static frc.robot.Robot.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.DelaunayInterpolatingMap;
import frc.utils.ShootingParameters;
import java.io.File;
import java.io.IOException;

public class TurretShooter extends SubsystemBase {

  private static final String JSON_FILE_LOCATION = "/home/lvuser/shooter_data.json";
  private static final String DEPLOY_FILE_LOCATION = "/home/lvuser/deploy/shooter_data.json";

  private final TalonFX flywheelMaster = new TalonFX(SHOOTER_FLYWHEEL_MASTER_ID);
  private final TalonFX flywheelSlave = new TalonFX(SHOOTER_FLYWHEEL_SLAVE_ID);

  private final TalonSRX turretMotor = new TalonSRX(SHOOTER_TURRET_ID);

  private final DelaunayInterpolatingMap knownDataMap;

  private final Encoder turretEncoder = new Encoder(TURRET_ENCODER_PORT_1, TURRET_ENCODER_PORT_2);

  public TurretShooter() {
    knownDataMap = loadMap();

    flywheelMaster.selectProfileSlot(0, 0);

    flywheelMaster.setInverted(true);
    flywheelSlave.follow(flywheelMaster);
    flywheelSlave.setInverted(true);

  }

  @Override
  public void periodic() {

  }

  public void setFlywheelSpeed(double targetSpeed) {
    flywheelMaster.set(ControlMode.Velocity, targetSpeed);
  }

  public void setTurretAngle(Rotation2d angle, boolean fieldOriented) {
    if(fieldOriented) {
      turretMotor.set(ControlMode.Position, angle.getRadians() - drivetrain.getHeading().getRadians() / (2 * Math.PI * TURRET_ROTATIONS_PER_TICK));
    }
    else {
      turretMotor.set(ControlMode.Position, angle.getRadians() / (2 * Math.PI * TURRET_ROTATIONS_PER_TICK));
    }
  }

  public Rotation2d getTurretAngle() {
    return Rotation2d.fromDegrees(turretEncoder.get() / (2 * Math.PI * TURRET_ROTATIONS_PER_TICK));
  }

  public void setOpenLoopTurretSpeeds(double speed) {
    turretMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * @return the interpolated output value for the inputData or null if there is less than 3 points
   * in the data map or the inputData can't be interpolated for.
   */
  public Double estimateTargetSpeed(ShootingParameters inputData) {
    return knownDataMap.get(inputData.asTuple());
  }

  private DelaunayInterpolatingMap loadMap() {
    try {
      return DelaunayInterpolatingMap.fromJson(new File(JSON_FILE_LOCATION));
    } catch (IOException e) {
      System.out.println("Unable to load " + JSON_FILE_LOCATION);
      e.printStackTrace();
      try {
        return DelaunayInterpolatingMap.fromJson(new File(DEPLOY_FILE_LOCATION));
      } catch (IOException e2) {
        System.out.println("Unable to load " + DEPLOY_FILE_LOCATION);
        return new DelaunayInterpolatingMap();
      }
    }
  }

  public DelaunayInterpolatingMap getKnownDataMap() {
    return knownDataMap;
  }

  /**
   * Updates the file on the robot.
   */
  public void updateDataFile() {
    try {
      knownDataMap.toJson(new File(JSON_FILE_LOCATION));
    } catch (IOException e) {
      System.out.println("Unable to save to file " + JSON_FILE_LOCATION);
      e.printStackTrace();
    }
  }
}
