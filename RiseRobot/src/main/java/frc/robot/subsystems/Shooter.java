package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.MotorConstants;
import frc.utils.TalonFXFactory;

public class Shooter extends SubsystemBase {

  public enum ControlState {
    SPIN_UP_SS_ESTIMATION,
    SPIN_UP_NORMAL_PID,
    HOLD_UNTIL_READY,
    SHOOT,
    DISABLED
  };

  private ControlState currentState;

  private final PIDController speedController = new PIDController(1, 0, 0);

  private static final TalonFX shooterMaster = new TalonFX(0);
  private static final TalonFX shooterSlave = makeSlave(1, false);

  private static final SupplyCurrentLimitConfiguration CURRENT_LIMITING_ENABLED = new SupplyCurrentLimitConfiguration(true, 38, 40, 2.0);
  private static final SupplyCurrentLimitConfiguration CURRENT_LIMITING_DISABLED = new SupplyCurrentLimitConfiguration();

  private static final double G = 2;
  private static final double J = 0.00032;
  private static final double ks = 0.0;
  private static final double kv = 0.0;
  private static final double ka = 0.0;
  private static final double flywheelRadius = 4;
  private static final MotorConstants falconConstants = new MotorConstants(0, 0, 0, 0, 0);

  // Slow for now
  private static double rampUpClosedLoopRate = 4.0;
  private static double shootClosedLoopRate = 6.0;

  // Integrated flywheel acceleration
  private double integratedAngularVelocity = 0.0;
  private double previousLinearVelocity = 0.0;
  private double setpointRpm = 0.0;

  public Shooter() {
    shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    shooterMaster.setInverted(false);
    shooterMaster.configVoltageCompSaturation(12);
    shooterMaster.enableVoltageCompensation(true);
    shooterMaster.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_10Ms);
    shooterMaster.configVelocityMeasurementWindow(32);
    shooterMaster.configClosedloopRamp(0);
    shooterMaster.configPeakOutputForward(1.0);
    shooterMaster.configPeakOutputReverse(-1.0);
    shooterMaster.configSupplyCurrentLimit(CURRENT_LIMITING_ENABLED);

    // More CAN bandwidth
    shooterMaster.setStatusFramePeriod(StatusFrame.Status_1_General, 2);
    shooterMaster.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, 2);
    // shooterMaster.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 2);

    currentState = ControlState.DISABLED;
  }

  private void configureForRampUpSSEstimation(double desiredRpm) {
    currentState = ControlState.SPIN_UP_SS_ESTIMATION;
    shooterMaster.configSupplyCurrentLimit(CURRENT_LIMITING_DISABLED);
    shooterMaster.configClosedloopRamp(rampUpClosedLoopRate);
    integratedAngularVelocity = 0.0;
    previousLinearVelocity = 0.0;
    setpointRpm = desiredRpm;
  }

  private void configureForRampUpNormalPID(double desiredRpm) {
    currentState = ControlState.SPIN_UP_NORMAL_PID;
    shooterMaster.configSupplyCurrentLimit(CURRENT_LIMITING_DISABLED);
    shooterMaster.configClosedloopRamp(rampUpClosedLoopRate);
    previousLinearVelocity = 0.0;
    setpointRpm = desiredRpm;
  }

  private void configureForHoldUntilReady() {
    currentState = ControlState.HOLD_UNTIL_READY;
    shooterMaster.configSupplyCurrentLimit(CURRENT_LIMITING_DISABLED);
    shooterMaster.configClosedloopRamp(rampUpClosedLoopRate);
  }

  private void configureForShoot() {
    currentState = ControlState.SHOOT;
    shooterMaster.configSupplyCurrentLimit(CURRENT_LIMITING_DISABLED);
    shooterMaster.configClosedloopRamp(shootClosedLoopRate);
  }

  private void runControlLoop() {
    double omegaM = shooterMaster.getSelectedSensorVelocity() / 2048.0 * Math.PI * 2.0;
    double V = shooterMaster.getMotorOutputVoltage();
    double setpointLinearVelocity = setpointRpm * Math.PI * 2.0 * flywheelRadius;

    if (currentState == ControlState.SPIN_UP_SS_ESTIMATION) {
      double prevLinearVelocity = integratedAngularVelocity * flywheelRadius;
      // Equations of motion
      double angularAcceleration = (G * falconConstants.Kt * V) / (falconConstants.R * J) -
              (G * falconConstants.Kt * omegaM) / (falconConstants.Kv * falconConstants.R * J);

      integratedAngularVelocity += angularAcceleration;

      double currentLinearVelocity = integratedAngularVelocity * flywheelRadius;

      double feedforward = ks * Math.signum(setpointLinearVelocity) + kv * setpointLinearVelocity + ka * (setpointLinearVelocity - prevLinearVelocity);

      shooterMaster.set(ControlMode.PercentOutput, (feedforward + speedController.calculate(currentLinearVelocity, setpointLinearVelocity) / 12.0));
    } else if (currentState == ControlState.SPIN_UP_NORMAL_PID) {
      double currentLinearVelocity = shooterMaster.getSelectedSensorVelocity() / 2048.0 * Math.PI * 2.0 * flywheelRadius;
      double feedforward = ks * Math.signum(setpointLinearVelocity) + kv * setpointLinearVelocity + ka * (setpointLinearVelocity - previousLinearVelocity);

      shooterMaster.set(ControlMode.PercentOutput, (feedforward + speedController.calculate(currentLinearVelocity, setpointLinearVelocity)) / 12.0);

      previousLinearVelocity = currentLinearVelocity;
    }
  }

  private static TalonFX makeSlave(int talonId, boolean flipOutput) {
    TalonFX slave = TalonFXFactory.createSlaveTalonFX(talonId, shooterMaster);
    slave.setInverted(flipOutput ? InvertType.InvertMotorOutput : InvertType.None);
    return slave;
  }
}
