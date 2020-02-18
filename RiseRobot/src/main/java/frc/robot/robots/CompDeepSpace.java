package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.util.Units;

public class CompDeepSpace implements WaltRobot {

    // Config constants
    private final int shifterChannel = 0;

    private final PIDController leftPIDController = new PIDController(2, 0, 0);   //maybe 2.53    //maybe 1.74? maybe 1.62?
    private final PIDController rightPIDController = new PIDController(2, 0, 0);  //maybe 2.53

    private final SimpleMotorFeedforward drivetrainFeedforward = new SimpleMotorFeedforward(0.199, 2.13, 0.534);

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
        return 0.79872077;
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

    @Override
    public double getVisionAlignKp() {
        return 0;
    }

    @Override
    public double getVisionAlignKs() {
        return 0;
    }

    @Override
    public double getMaxAlignmentTime() {
        return 0;
    }

    @Override
    public double getVisionAlignTxTolerance() {
        return 0;
    }

    @Override
    public String toString() {
        return "Competition Deep Space";
    }
}
