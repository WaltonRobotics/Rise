package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;

public interface WaltRobot {

    // Ramsete constants
    double getTrackWidth();

    double getKBeta();

    double getKZeta();

    // Velocity controller constants
    PIDController getLeftPIDController();

    PIDController getRightPIDController();

    // Shooter feedforward
    SimpleMotorFeedforward getFlywheelFeedforward();

    // Drivetrain feedforward
    SimpleMotorFeedforward getDrivetrainFeedforward();

    double getRpmToMeters();

    double getDistancePerPulse();

    Solenoid getShifter();

}
