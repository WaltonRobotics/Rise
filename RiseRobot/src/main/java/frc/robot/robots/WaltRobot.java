package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

public interface WaltRobot {

    double getTrackWidth();

    double getKBeta();

    double getKZeta();

    // Velocity controller
    PIDController getLeftPIDController();

    PIDController getRightPIDController();

    // Turn controller
    ProfiledPIDController getTurnPIDController();

    // Distance controller
    PIDController getDistancePIDController();

    // Shooter feedforward
    SimpleMotorFeedforward getFlywheelFeedforward();

    // Drivetrain feedforward
    SimpleMotorFeedforward getDrivetrainFeedforward();

    // Encoder constants

    double getVelocityFactor();

    double getPositionFactor();

    double getDistancePerPulse();

    Solenoid getShifter();

    double getMinimumShiftingTime();

    int getMountingAngle();

    double getVisionAlignKp();

    double getVisionAlignKs();

    double getMaxAlignmentTime();

    double getVisionAlignTxTolerance();
}
