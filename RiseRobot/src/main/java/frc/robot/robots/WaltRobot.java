package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

public interface WaltRobot {

    // Ramsete constants
    double getTrackWidth();

    double getKBeta();

    double getKZeta();

    // Velocity controller
    PIDController getLeftPIDController();

    PIDController getRightPIDController();

    // Turn controller
    PIDController getTurnPIDController();

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

    double getTrajectoryTimeAdditive();

    double getMaxAlignmentTime();

}
