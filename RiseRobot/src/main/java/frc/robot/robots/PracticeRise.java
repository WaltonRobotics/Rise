package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
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
  public SimpleMotorFeedforward getFlywheelFeedforward() {
    return null;
  }

  @Override
  public SimpleMotorFeedforward getDrivetrainFeedforward() {
    return null;
  }

  @Override
  public SpeedControllerGroup getLeftSpeedControllerGroup() {
    return null;
  }

  @Override
  public SpeedControllerGroup getRightSpeedControllerGroup() {
    return null;
  }

  @Override
  public double getRpmToMeters() {
    return 0;
  }

  @Override
  public Solenoid getShifter() {
    return null;
  }

  @Override
  public void setSpeeds(double leftSpeed, double rightSpeed) {

  }

  @Override
  public void setVoltages(double leftVoltage, double rightVoltage) {

  }
}
