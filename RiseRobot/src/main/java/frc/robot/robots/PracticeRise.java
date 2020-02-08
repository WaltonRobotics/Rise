package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

public class PracticeRise implements WaltRobot {
    @Override
    public double getTrackWidth() {
        return 0;
    }

    @Override
    public double getKBeta() {
        return 0;
    }

    @Override
    public double getKZeta() {
        return 0;
    }

    @Override
    public PIDController getLeftPIDController() {
        return null;
    }

    @Override
    public PIDController getRightPIDController() {
        return null;
    }

    @Override
    public PIDController getTurnPIDController() {
        return null;
    }

    @Override
    public PIDController getDistancePIDController() {
        return null;
    }

    @Override
    public SimpleMotorFeedforward getFlywheelFeedforward() {
        return null;
    }

    @Override
    public SimpleMotorFeedforward getDrivetrainFeedforward() {
        return null;
    }

    @Override
    public double getVelocityFactor() {
        return 0;
    }

    @Override
    public double getPositionFactor() {
        return 0;
    }

    @Override
    public double getDistancePerPulse() {
        return 0;
    }

    @Override
    public Solenoid getShifter() {
        return null;
    }

    @Override
    public double getMinimumShiftingTime() {
        return 0;
    }

    @Override
    public double getTrajectoryTimeAdditive() {
        return 5.0;
    }

    @Override
    public double getMaxAlignmentTime() {
        return 0;
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
    public double getVisionAlignTxTolerance() {
        return 0;
    }

}
