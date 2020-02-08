package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_MASTER_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_SLAVE_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_TURRET_ID;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.DelaunayInterpolatingMap;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TurretShooter extends SubsystemBase {

  private static final String JSON_FILE_LOCATION = "/home/lvuser/shooter_data.json";
  private static final String DEPLOY_FILE_LOCATION = "/home/lvuser/deploy/shooter_data.json";
  private static final Map<Vector2d, Double> DEFAULT_DATA_MAP = Map.of(
      new Vector2d(-1, -1), -1.0,
      new Vector2d(-2, -2), -2.0,
      new Vector2d(-3, -3), -3.0
  );

  private final TalonFX flywheelMaster = new TalonFX(SHOOTER_FLYWHEEL_MASTER_ID);
  private final TalonFX flywheelSlave = new TalonFX(SHOOTER_FLYWHEEL_SLAVE_ID);
  private final TalonSRX turretMotor = new TalonSRX(SHOOTER_TURRET_ID);
  private final DelaunayInterpolatingMap knownDataMap;

  private double targetSpeed = 0;

  public TurretShooter() {
    knownDataMap = loadMap();

    flywheelMaster.selectProfileSlot(0, 0);
    flywheelMaster.setInverted(true);

    flywheelSlave.follow(flywheelMaster);
  }

  @Override
  public void periodic() {
    flywheelMaster.set(TalonFXControlMode.Velocity, targetSpeed);
  }

  public void setTargetSpeed(double targetSpeed) {
    this.targetSpeed = targetSpeed;
  }

  /**
   * @return the interpolated output value for the inputData or null if there is less than 3 points
   * in the data map or the inputData can't be interpolated for.
   */
  public Double estimateTargetSpeed(Vector2d inputData) {
    if (!knownDataMap.isTriangulated) {
      if (!knownDataMap.triangulate()) {
        return null;
      }
    }
    return knownDataMap.get(inputData);
  }

  /**
   * @return the interpolated output value for the inputData or null if there is less than 3 points
   * in the data map or the inputData can't be interpolated for.
   */
  public Double estimateTargetSpeed(double inputOne, double inputTwo) {
    return estimateTargetSpeed(new Vector2d(inputOne, inputTwo));
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
        return new DelaunayInterpolatingMap(DEFAULT_DATA_MAP);
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
