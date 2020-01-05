package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;

public class CompDeepSpace implements WaltRobot {

  // Config constants
  private final int SHIFTER_CHANNEL = 0;

  private final PIDController leftPIDController = new PIDController(1, 0, 0);
  private final PIDController rightPIDController = new PIDController(1, 0, 0);

  private final Solenoid shifter = new Solenoid(SHIFTER_CHANNEL);

  public CompDeepSpace() {


  }

  @Override
  public double getTrackWidth() {
    return 0.78;
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
  public SimpleMotorFeedforward getFlywheelFeedforward() {
    return null;
  }

  @Override
  public SimpleMotorFeedforward getDrivetrainFeedforward() {
    return new SimpleMotorFeedforward(0.178, 3.19, 0.462);
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
    return 0.006649704450098395;
  }

  @Override
  public Solenoid getShifter() {
    return shifter;
  }

  @Override
  public void setSpeeds(double leftSpeed, double rightSpeed) {

  }

  @Override
  public void setVoltages(double leftVoltage, double rightVoltage) {

  }
}
