package frc.utils;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.MotorSafety;

public class TalonFXFactory {

    public static class Configuration {
        public double NOMINAL_OUTPUT = 0.0;
        public double PEAK_OUTPUT = 1.0;
        public boolean ENABLE_CURRENT_LIMIT = false;
        public boolean ENABLE_SOFT_LIMIT = false;
        public LimitSwitchSource FWD_LIMIT_SWITCH_SOURCE = LimitSwitchSource.Deactivated;
        public LimitSwitchNormal FWD_LIMIT_SWITCH_NORMAL = LimitSwitchNormal.NormallyOpen;
        public LimitSwitchSource REV_LIMIT_SWITCH_SOURCE = LimitSwitchSource.Deactivated;
        public LimitSwitchNormal REV_LIMIT_SWITCH_NORMAL = LimitSwitchNormal.NormallyOpen;
        public int INPUT_CURRENT_LIMIT = 38;
        public int INPUT_CURRENT_TRIGGER = 40;
        public double INPUT_CURRENT_TRIGGET_TIME = 2.0;
        public int FORWARD_SOFT_LIMIT = 0;
        public boolean INVERTED = false;
        public int REVERSE_SOFT_LIMIT = 0;

        public int CONTROL_FRAME_PERIOD_MS = 5;
        public int MOTION_CONTROL_FRAME_PERIOD_MS = 100;
        public int GENERAL_STATUS_FRAME_RATE_MS = 5;
        public int FEEDBACK_STATUS_FRAME_RATE_MS = 100;
        public int QUAD_ENCODER_STATUS_FRAME_RATE_MS = 100;
        public int ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 100;
        public int PULSE_WIDTH_STATUS_FRAME_RATE_MS = 100;

        public VelocityMeasPeriod VELOCITY_MEASUREMENT_PERIOD = VelocityMeasPeriod.Period_100Ms;
        public int VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW = 64;

        public double OPEN_LOOP_RAMP_RATE = 0;
        public double CLOSED_LOOP_RAMP_RATE = 0;
    }

    private static final Configuration kDefaultConfiguration = new Configuration();
    private static final Configuration kSlaveConfiguration = new Configuration();

    static {
        kSlaveConfiguration.CONTROL_FRAME_PERIOD_MS = 1000;
        kSlaveConfiguration.MOTION_CONTROL_FRAME_PERIOD_MS = 1000;
        kSlaveConfiguration.GENERAL_STATUS_FRAME_RATE_MS = 1000;
        kSlaveConfiguration.FEEDBACK_STATUS_FRAME_RATE_MS = 1000;
        kSlaveConfiguration.QUAD_ENCODER_STATUS_FRAME_RATE_MS = 1000;
        kSlaveConfiguration.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 1000;
        kSlaveConfiguration.PULSE_WIDTH_STATUS_FRAME_RATE_MS = 1000;
    }

    // Create a TalonFX with the default (out of the box) configuration.
    public static TalonFX createDefaultTalonFX(int id) {
        return createTalonFX(id, kDefaultConfiguration);
    }

    public static TalonFX createSlaveTalonFX(int id, IMotorController master) {
        final TalonFX talon = createTalonFX(id, kSlaveConfiguration);
        talon.follow(master);
        return talon;
    }

    public static TalonFX createTalonFX(int id, Configuration config) {
        TalonFX talon = new TalonFX(id);
        talon.changeMotionControlFramePeriod(config.MOTION_CONTROL_FRAME_PERIOD_MS);
        talon.setIntegralAccumulator(0.0);
        talon.clearMotionProfileHasUnderrun();
        talon.clearMotionProfileTrajectories();
        talon.clearStickyFaults();
        talon.configForwardLimitSwitchSource(config.FWD_LIMIT_SWITCH_SOURCE, config.FWD_LIMIT_SWITCH_NORMAL);
        talon.configReverseLimitSwitchSource(config.REV_LIMIT_SWITCH_SOURCE, config.REV_LIMIT_SWITCH_NORMAL);
        talon.configNominalOutputForward(config.NOMINAL_OUTPUT);
        talon.configPeakOutputForward(config.PEAK_OUTPUT);
        talon.configNominalOutputForward(-config.NOMINAL_OUTPUT);
        talon.configPeakOutputForward(-config.PEAK_OUTPUT);
        talon.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(config.ENABLE_CURRENT_LIMIT, config.INPUT_CURRENT_LIMIT, config.INPUT_CURRENT_TRIGGER, config.INPUT_CURRENT_TRIGGET_TIME));
        talon.configForwardSoftLimitEnable(config.ENABLE_SOFT_LIMIT);
        talon.configForwardSoftLimitThreshold(config.FORWARD_SOFT_LIMIT);
        talon.configReverseSoftLimitEnable(config.ENABLE_SOFT_LIMIT);
        talon.configReverseSoftLimitThreshold(config.REVERSE_SOFT_LIMIT);
        talon.configClearPositionOnLimitF(false, 0);
        talon.configClearPositionOnLimitR(false, 0);
        talon.setSelectedSensorPosition(0);
        talon.setInverted(config.INVERTED);
        talon.configVelocityMeasurementPeriod(config.VELOCITY_MEASUREMENT_PERIOD);
        talon.configVelocityMeasurementWindow(config.VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW);
        talon.configOpenloopRamp(config.OPEN_LOOP_RAMP_RATE);
        talon.configClosedloopRamp(config.CLOSED_LOOP_RAMP_RATE);

        talon.setStatusFramePeriod(StatusFrame.Status_1_General, config.GENERAL_STATUS_FRAME_RATE_MS);
        talon.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, config.FEEDBACK_STATUS_FRAME_RATE_MS);
        talon.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat,
                config.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS);
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth, config.PULSE_WIDTH_STATUS_FRAME_RATE_MS);

        return talon;
    }

}
