package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.util.Units;

public class CompDeepSpace implements WaltRobot {

    // Config constants
    private final int shifterChannel = 0;

    private final PIDController leftPIDController = new PIDController(1.91, 0, 0);
    private final PIDController rightPIDController = new PIDController(1.91, 0, 0);

    private final Solenoid shifter = new Solenoid(shifterChannel);

    private final double highGearRatio = 5.39;
    private final double wheelDiameter = Units.inchesToMeters(5);

    public CompDeepSpace() {

    }

    @Override
    public double getTrackWidth() {
        return 0.996;
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
        return new SimpleMotorFeedforward(0.237, 1.72, 0.46);
    }

    @Override
    public double getRpmToMeters() {
        return 1 / highGearRatio * Math.PI * wheelDiameter;
    }

    @Override
    public double getDistancePerPulse() {
        return 0.000593397;
    }

    @Override
    public Solenoid getShifter() {
        return shifter;
    }

    @Override
    public double getMinimumShiftingTime() {
        return 1.0;
    }
}
