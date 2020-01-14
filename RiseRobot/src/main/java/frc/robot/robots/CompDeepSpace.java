package frc.robot.robots;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.util.Units;

public class CompDeepSpace implements WaltRobot {

  // Config constants
  private final int SHIFTER_CHANNEL = 0;

  private final PIDController leftPIDController = new PIDController(1, 0, 0);
  private final PIDController rightPIDController = new PIDController(1, 0, 0);

  private final Solenoid shifter = new Solenoid(SHIFTER_CHANNEL);

  private final double highGearRatio = 5.39;
  private final double wheelDiameter = Units.inchesToMeters(5);
  private final double encoderResolution = 60;

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
  public double getRpmToMeters() {
    return 1 / highGearRatio * Math.PI * wheelDiameter / encoderResolution ;
  }

  @Override
  public Solenoid getShifter() {
    return shifter;
  }
}
