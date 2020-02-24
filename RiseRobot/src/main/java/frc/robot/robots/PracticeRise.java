package frc.robot.robots;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.utils.interpolatingmap.InterpolatingDouble;
import frc.utils.interpolatingmap.InterpolatingTreeMap;

public class PracticeRise implements WaltRobot {

    private final PIDController leftPIDController = new PIDController(1.4, 0, 0);
    private final PIDController rightPIDController = new PIDController(1.4, 0, 0);

    private final SimpleMotorFeedforward drivetrainFeedforward = new SimpleMotorFeedforward(0.165, 2.04, 0); //0.487

    private ProfiledPIDController turnPIDController = new ProfiledPIDController(0.012, 0, 0,
            new TrapezoidProfile.Constraints(360, 80)); //0.009

    private final InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> shooterCalibrationMap = new InterpolatingTreeMap<>();

    public PracticeRise() {
        turnPIDController.enableContinuousInput(-180f, 180f);
        turnPIDController.setTolerance(1, 1);
        populateShooterLUT();
    }

    @Override
    public double getTrackWidth() {
        return 0.729784476;
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
    public SimpleMotorFeedforward getFlywheelFeedforward() {
        return null;
    }

    @Override
    public SimpleMotorFeedforward getDrivetrainFeedforward() {
        return drivetrainFeedforward;
    }

    @Override
    public double getVelocityFactor() {
        return 0.0610634006918382 / 60.;
    }

    @Override
    public double getPositionFactor() {
        return 0.0610634006918382;
    }

    @Override
    public double getDistancePerPulse() {
        return 1;
    }

    @Override
    public double getMinimumShiftingTime() {
        return 0;
    }

    @Override
    public double getLimelightMountingAngle() {
        return 35;
    }

    @Override
    public double getLimelightMountingHeight() {
        return 24;
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
    public InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> getShooterCalibrationMap() {
        return shooterCalibrationMap;
    }

    @Override
    public void populateShooterLUT() {
        shooterCalibrationMap.put(new InterpolatingDouble(9.96), new InterpolatingDouble(16800.0));
        shooterCalibrationMap.put(new InterpolatingDouble(12.718), new InterpolatingDouble(16500.0));
        shooterCalibrationMap.put(new InterpolatingDouble(15.2), new InterpolatingDouble(16400.0));
        shooterCalibrationMap.put(new InterpolatingDouble(17.04), new InterpolatingDouble(16800.0));
        shooterCalibrationMap.put(new InterpolatingDouble(19.9), new InterpolatingDouble(17700.0));
    }

    @Override
    public String toString() {
        return "Practice Rise";
    }

}
