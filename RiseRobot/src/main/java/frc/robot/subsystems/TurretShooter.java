package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_MASTER_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_SLAVE_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_TURRET_ID;
import static frc.robot.Constants.Turret.TURRET_ENCODER_PORT_1;
import static frc.robot.Constants.Turret.TURRET_ENCODER_PORT_2;
import static frc.robot.Constants.Turret.TURRET_ROTATIONS_PER_TICK;
import static frc.robot.Robot.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.ShootingParameters;
import frc.utils.map.Interpolable;
import frc.utils.map.InterpolatingDelaunayMap;
import frc.utils.map.InterpolatingDoubleTreeMap;
import frc.utils.map.InterpolatingTreeMap;
import frc.utils.map.InverseInterpolable;
import frc.utils.json.JsonableInterpolatingMap;
import java.io.File;
import java.io.IOException;


public class TurretShooter extends SubsystemBase {

  public static final boolean IS_DELAUNAY_MAP = false;
  private static final String JSON_FILE_LOCATION = "/home/lvuser/shooter_data.json";
  private static final String DEPLOY_FILE_LOCATION = "/home/lvuser/deploy/shooter_data.json";

  private final TalonFX flywheelMaster = new TalonFX(SHOOTER_FLYWHEEL_MASTER_ID);
  private final TalonFX flywheelSlave = new TalonFX(SHOOTER_FLYWHEEL_SLAVE_ID);
  private final TalonSRX turretMotor = new TalonSRX(SHOOTER_TURRET_ID);
  private final JsonableInterpolatingMap knownDataMap;
  private final Encoder turretEncoder = new Encoder(TURRET_ENCODER_PORT_1, TURRET_ENCODER_PORT_2);

  public boolean isReadyToShoot = false;


  public TurretShooter() {
    knownDataMap = loadMap();

    flywheelMaster.selectProfileSlot(0, 0);

    flywheelMaster.setInverted(true);
    flywheelSlave.setInverted(InvertType.OpposeMaster);
    flywheelSlave.follow(flywheelMaster);

  }

  @Override
  public void periodic() {

  }

  public void setFlywheelOutput(TalonFXControlMode controlMode, double output) {
    flywheelMaster.set(controlMode, output);
  }

  public double getFlywheelSpeed() {
    return flywheelMaster.getSensorCollection().getIntegratedSensorVelocity();
  }

  public void setTurretAngle(Rotation2d angle, boolean fieldOriented) {
    if (fieldOriented) {
      turretMotor.set(ControlMode.Position,
          angle.getRadians() - drivetrain.getHeading().getRadians() / (2 * Math.PI
              * TURRET_ROTATIONS_PER_TICK));
    } else {
      turretMotor.set(ControlMode.Position,
          angle.getRadians() / (2 * Math.PI * TURRET_ROTATIONS_PER_TICK));
    }
  }

  public Rotation2d getTurretAngle() {
    return Rotation2d.fromDegrees(turretEncoder.get() / (2 * Math.PI * TURRET_ROTATIONS_PER_TICK));
  }

  public void setOpenLoopTurretOutput(double outputValue) {
    turretMotor.set(ControlMode.PercentOutput, outputValue);
  }

  /**
   * @return the interpolated output value for the input or null if there are less than 3 points in
   * the data map, if the input can't be interpolated for, or if knownDataMap is not an {@code
   * InterpolatingDelaunayMap}.
   */
  public Double estimateTargetSpeed(ShootingParameters input) {
    if (knownDataMap instanceof InterpolatingDelaunayMap) {
      return ((InterpolatingDelaunayMap) knownDataMap).get(input.asTuple());
    }
    return null;
  }

  /**
   * @param <K> the key type of knownDataMap.
   * @param <V> the value type of knownDataMap.
   * @return the interpolated output value for the input or null if the input can't be interpolated
   * for or if knownDataMap is not an {@code InterpolatingTreeMap}.
   */
  public <K extends InverseInterpolable<K> & Comparable<K>,
      V extends Interpolable<V>> V
  estimateTargetSpeed(K input) {
    if (knownDataMap instanceof InterpolatingTreeMap) {
      return ((InterpolatingTreeMap<K, V>) knownDataMap).get(input);
    }
    return null;
  }

  /**
   * Attempts to return a {@code JsonableInterpolatingMap} deserialized from {@code
   * JSON_FILE_LOCATION} or {@code DEPLOY_FILE_LOCATION}. Returns at empty map if the file cannot be
   * loaded.
   *
   * The type of map is dependent on {@code IS_DELAUNAY_MAP}.
   */
  private JsonableInterpolatingMap loadMap() {
    try {
      if (IS_DELAUNAY_MAP) {
        return InterpolatingDelaunayMap._fromJson(new File(JSON_FILE_LOCATION));
      }
      return InterpolatingDoubleTreeMap._fromJson(new File(JSON_FILE_LOCATION));
    } catch (IOException e) {
      System.out.println("Unable to load " + JSON_FILE_LOCATION);
      e.printStackTrace();
      try {
        if (IS_DELAUNAY_MAP) {
          return InterpolatingDelaunayMap._fromJson(new File(DEPLOY_FILE_LOCATION));
        }
        return InterpolatingDoubleTreeMap._fromJson(new File(DEPLOY_FILE_LOCATION));
      } catch (IOException e2) {
        System.out.println("Unable to load " + DEPLOY_FILE_LOCATION);
        if (IS_DELAUNAY_MAP) {
          return new InterpolatingDelaunayMap();
        }
        return new InterpolatingDoubleTreeMap();
      }
    }
  }

  public JsonableInterpolatingMap getKnownDataMap() {
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
