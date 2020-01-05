package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;

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

    // Left SpeedController group
    SpeedControllerGroup getLeftSpeedControllerGroup();

    // right SpeedController group
    SpeedControllerGroup getRightSpeedControllerGroup();

    double getRpmToMeters();

    Solenoid getShifter();

    void setSpeeds(double leftSpeed, double rightSpeed);
    void setVoltages(double leftVoltage, double rightVoltage);

}
