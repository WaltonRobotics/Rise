package frc.robot.robots;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.util.Units;

import static edu.wpi.first.networktables.EntryListenerFlags.kNew;
import static edu.wpi.first.networktables.EntryListenerFlags.kUpdate;

public class CompDeepSpace implements WaltRobot {

    // Config constants
    private final int shifterChannel = 0;

    private final PIDController leftPIDController = new PIDController(1.62, 0, 0);       //maybe 1.74?
    private final PIDController rightPIDController = new PIDController(1.62, 0, 0);

    private final SimpleMotorFeedforward drivetrainFeedforward = new SimpleMotorFeedforward(0.201, 2.12, 0.551);

    private final Solenoid shifter = new Solenoid(shifterChannel);

    private final double highGearRatio = 1; //6.58905 * 1.051
    private final double wheelDiameter = Units.inchesToMeters(5);

    private ProfiledPIDController turnPIDController;
    private PIDController distancePIDController;

    public CompDeepSpace() {
        TrapezoidProfile.Constraints turnControllerConstraint = new TrapezoidProfile.Constraints(360, 80); //FIXME
        turnPIDController = new ProfiledPIDController(0.011, 0, 0, turnControllerConstraint); //0.009
        turnPIDController.enableContinuousInput(-180f, 180f);
        turnPIDController.setTolerance(1, 1);

        distancePIDController = new PIDController(0.0001, 0, 0);
        distancePIDController.setTolerance(0.05);
    }

    // 32 l
    // 38 w

    @Override
    public double getTrackWidth() {
        return 0.797403467;
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
    public ProfiledPIDController getTurnPIDController() {
        return turnPIDController;
    }

    @Override
    public PIDController getDistancePIDController() {
        return distancePIDController;
    }

    @Override
    public SimpleMotorFeedforward getFlywheelFeedforward() {
        return null;
    }

    @Override
    public SimpleMotorFeedforward getDrivetrainFeedforward() {
        return drivetrainFeedforward;
    }

    @Override
    public double getVelocityFactor() {
        return 0.06038 / 60.;
    }

    @Override
    public double getPositionFactor() {
        return 0.06038;
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
