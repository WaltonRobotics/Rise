package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_MASTER_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_SLAVE_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_TURRET_ID;
import static frc.robot.Constants.Shooter.defaultShooterRPM;
import static frc.robot.Constants.Turret.TURRET_ENCODER_PORT_1;
import static frc.robot.Constants.Turret.TURRET_ENCODER_PORT_2;
import static frc.robot.Constants.Turret.TURRET_ROTATIONS_PER_TICK;
import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.interpolatingMap.*;


public class TurretShooter extends SubsystemBase {

  private final TalonFX flywheelMaster = new TalonFX(SHOOTER_FLYWHEEL_MASTER_ID);
  private final TalonFX flywheelSlave = new TalonFX(SHOOTER_FLYWHEEL_SLAVE_ID);
  private final TalonSRX turretMotor = new TalonSRX(SHOOTER_TURRET_ID);
  private final Encoder turretEncoder = new Encoder(TURRET_ENCODER_PORT_1, TURRET_ENCODER_PORT_2);

  public boolean isReadyToShoot = false;


  public TurretShooter() {

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
   * Returns interpolated target Speed in RPMS using interpolating tree map
   * @param distance the distance to target in meters
   * @return RPMs to set the flywheel
   */
  public double estimateTargetSpeed(double distance) {
    InterpolatingDouble result = currentRobot.getShooterCalibrationMap().get(new InterpolatingDouble(distance));
    if (result != null) {
      return result.value;
    } else {
      return defaultShooterRPM;
    }
  }

  public InterpolatingTreeMap getKnownDataMap() {
    return currentRobot.getShooterCalibrationMap();
  }
}
