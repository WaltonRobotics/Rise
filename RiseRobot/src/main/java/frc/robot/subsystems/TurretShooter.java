package frc.robot.subsystems;

import static edu.wpi.first.wpilibj.Timer.getFPGATimestamp;
import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_MASTER_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_SLAVE_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_TURRET_ID;
import static frc.robot.Constants.Turret.TURRET_ENCODER_PORT_1;
import static frc.robot.Constants.Turret.TURRET_ENCODER_PORT_2;
import static frc.robot.Constants.Turret.TURRET_ROTATIONS_PER_TICK;
import static frc.robot.Robot.drivetrain;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import frc.utils.ShootingParameters;
import frc.utils.map.Interpolable;
import frc.utils.map.InterpolatingDelaunayMap;
import frc.utils.map.InterpolatingDouble;
import frc.utils.map.InterpolatingTreeMap;
import frc.utils.map.InverseInterpolable;
import frc.utils.map.JsonableInterpolatingMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Supplier;


public class TurretShooter extends WaltSubsystem {

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
  public <K extends InverseInterpolable<K> & Comparable<K>, V extends Interpolable<V>> V
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
      return InterpolatingTreeMap.<InterpolatingDouble, InterpolatingDouble>
          _fromJson(new File(JSON_FILE_LOCATION));
    } catch (IOException e) {
      System.out.println("Unable to load " + JSON_FILE_LOCATION);
      e.printStackTrace();
      try {
        if (IS_DELAUNAY_MAP) {
          return InterpolatingDelaunayMap._fromJson(new File(DEPLOY_FILE_LOCATION));
        }
        return InterpolatingTreeMap.<InterpolatingDouble, InterpolatingDouble>
            _fromJson(new File(DEPLOY_FILE_LOCATION));
      } catch (IOException e2) {
        System.out.println("Unable to load " + DEPLOY_FILE_LOCATION);
        if (IS_DELAUNAY_MAP) {
          return new InterpolatingDelaunayMap();
        }
        return new InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble>();
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

  @Override
  public Supplier<ArrayList<String>> getPitCheckFunction() {
    return () -> {
      ArrayList<String> failures = new ArrayList<>();

      if (flywheelMaster.getLastError() != ErrorCode.OK) {
        failures.add("Flywheel Master Controller");
      }
      if (flywheelSlave.getLastError() != ErrorCode.OK) {
        failures.add("Flywheel Slave Controller");
      }
      if (turretMotor.getLastError() != ErrorCode.OK) {
        failures.add("Turret Motor Controller");
      } else {
        double previousEncoderReading = turretEncoder.getDistance();
        double startTime = getFPGATimestamp();
        turretMotor.set(TalonSRXControlMode.PercentOutput, 0.2);
        while(getFPGATimestamp() - startTime < 0.2);
        if (turretEncoder.getDistance() == previousEncoderReading) {
          failures.add("Turret Encoder");
        }
      }
      if (knownDataMap.isEmpty()) {
        failures.add("Shooter Map Loading");
      }

      return failures;
    };
  }

  @Override
  public void sendToNT() {

  }
}
