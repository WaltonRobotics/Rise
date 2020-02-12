package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.util.Units;

public class CompDeepSpace implements WaltRobot {

    // Config constants
    private final int shifterChannel = 0;

    private final PIDController leftPIDController = new PIDController(1.49, 0, 0);
    private final PIDController rightPIDController = new PIDController(0.5, 0, 0);

    private final Solenoid shifter = new Solenoid(shifterChannel);

    private final double highGearRatio = 6.58905;
    private final double wheelDiameter = Units.inchesToMeters(5);

    public CompDeepSpace() {

    }

    // 32 l
    // 38 w

    @Override
    public double getTrackWidth() {
        return 0.790048721621379;
    }

    @Override
    public double getKBeta() {
        return 2.0;
    }

    @Override
    public double getKZeta() {
        return 0.7;
    }

    @Override
    public PIDController getLeftPIDController() {
        return leftPIDController;
    }

    @Override
    public PIDController getRightPIDController() {
        return rightPIDController;
    }

    @Override
    public PIDController getTurnPIDController() {
        return new PIDController(0.008, 0, 0);
    }

    @Override
    public PIDController getDistancePIDController() {
        return new PIDController(0.0001, 0, 0);
    }

    @Override
    public SimpleMotorFeedforward getFlywheelFeedforward() {
        return null;
    }

    @Override
    public SimpleMotorFeedforward getDrivetrainFeedforward() {
        return new SimpleMotorFeedforward(0.194, 2.11, 0.525);
    }

    @Override
    public double getRpmToMeters() {
        return 1 / highGearRatio * Math.PI * wheelDiameter;
    }

    @Override
    public double getDistancePerPulse() {
        return 0.0005706796580;
    }

    @Override
    public Solenoid getShifter() {
        return shifter;
    }

    @Override
    public double getMinimumShiftingTime() {
        return 1.0;
    }

    @Override
    public int getMountingAngle() {
        return 20;
    }
}
