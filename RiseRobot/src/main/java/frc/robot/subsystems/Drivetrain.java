package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;

import static frc.robot.Robot.currentRobot;

public class Drivetrain extends SubsystemBase {

  private static double DEADBAND = 0.1;

  public static final CANSparkMax rightWheelsMaster = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless);
  public static final CANSparkMax rightWheelsSlave = new CANSparkMax(2, CANSparkMax.MotorType.kBrushless);

  public static final CANSparkMax leftWheelsMaster = new CANSparkMax(3, CANSparkMax.MotorType.kBrushless);
  public static final CANSparkMax leftWheelsSlave = new CANSparkMax(4, CANSparkMax.MotorType.kBrushless);

  private final AHRS ahrs = new AHRS(SPI.Port.kMXP);

  private DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(currentRobot.getTrackWidth());
  private DifferentialDriveOdometry driveOdometry = new DifferentialDriveOdometry(getHeading());

  private RamseteController ramseteController = new RamseteController(currentRobot.getKBeta(), currentRobot.getKZeta());

  private Pose2d robotPose = new Pose2d();
  private boolean isHighGear = true;

  public SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0.237, 1.72, 0.46);

  public PIDController leftPIDController = new PIDController(1.91, 0, 0);
  public PIDController rightPIDController = new PIDController(1.91, 0, 0);

  public Drivetrain() {
    ahrs.zeroYaw();
    motorSetUp();
  }

  @Override
  public void periodic() {
    updateRobotPose();
    isHighGear = currentRobot.getShifter().get();
    SmartDashboard.putNumber("Angle", getHeading().getDegrees());
  }

  public void motorSetUp() {
    leftWheelsMaster.restoreFactoryDefaults();
    leftWheelsSlave.restoreFactoryDefaults();
    rightWheelsMaster.restoreFactoryDefaults();
    rightWheelsSlave.restoreFactoryDefaults();

    leftWheelsMaster.setInverted(true);

    leftWheelsSlave.setIdleMode(CANSparkMax.IdleMode.kCoast);
    leftWheelsMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
    rightWheelsSlave.setIdleMode(CANSparkMax.IdleMode.kCoast);
    rightWheelsMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);

    leftWheelsSlave.follow(leftWheelsMaster);
    rightWheelsSlave.follow(rightWheelsMaster);

    leftWheelsMaster.setOpenLoopRampRate(0);
    leftWheelsSlave.setOpenLoopRampRate(0);
    rightWheelsMaster.setOpenLoopRampRate(0);
    rightWheelsSlave.setOpenLoopRampRate(0);

    leftWheelsMaster.setSmartCurrentLimit(38);
    leftWheelsSlave.setSmartCurrentLimit(38);
    rightWheelsMaster.setSmartCurrentLimit(38);
    rightWheelsSlave.setSmartCurrentLimit(38);

    leftWheelsMaster.getEncoder().setVelocityConversionFactor(currentRobot.getRpmToMeters());
    rightWheelsMaster.getEncoder().setVelocityConversionFactor(currentRobot.getRpmToMeters());

    leftWheelsMaster.getEncoder().setPositionConversionFactor(currentRobot.getRpmToMeters());
    rightWheelsMaster.getEncoder().setPositionConversionFactor(currentRobot.getRpmToMeters());

  }

  public void setSpeeds(double leftPower, double rightPower) {
    leftWheelsMaster.set(leftPower);
    rightWheelsMaster.set(rightPower);
  }

  public void setVoltages(double leftVoltage, double rightVoltage) {
    System.out.println(leftVoltage);
    System.out.println(rightVoltage);
    leftWheelsMaster.setVoltage(leftVoltage);
    rightWheelsMaster.setVoltage(rightVoltage);
  }

  protected double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  public void setArcadeSpeeds(double xSpeed, double zRotation, boolean squareInputs) {
    xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
    zRotation = Math.copySign(zRotation * zRotation, zRotation);

    double leftMotorOutput;
    double rightMotorOutput;

    xSpeed = Math
            .max(-1.0 + Math.abs(zRotation),
                    Math.min(1.0 - Math.abs(zRotation), xSpeed));

    leftMotorOutput = xSpeed + zRotation;
    rightMotorOutput = xSpeed - zRotation;

    setSpeeds(leftMotorOutput, rightMotorOutput);
  }

  public void shiftUp() {
    if (!currentRobot.getShifter().get()) {
      System.out.println("Shifted Up");
      currentRobot.getShifter().set(true);
    }
  }

  public void shiftDown() {
    if (currentRobot.getShifter().get()) {
      System.out.println("Shifted Down");
      currentRobot.getShifter().set(false);
    }
  }

  public Rotation2d getHeading() {
    return Rotation2d.fromDegrees(-ahrs.getYaw());  // counter clock wise positive
  }

  public AHRS getAhrs() {
    return ahrs;
  }

  /**
   * Zeroes the heading of the robot.
   */
  public void zeroHeading() {
    ahrs.zeroYaw();
  }

  public DifferentialDriveWheelSpeeds getSpeeds() {
    return new DifferentialDriveWheelSpeeds(
      leftWheelsMaster.getEncoder().getVelocity() / 60,
      rightWheelsMaster.getEncoder().getVelocity() / 60
    );
  }

  public double leftMetersTravelled() {
    return leftWheelsMaster.getEncoder().getPosition();
  }

  public double rightMetersTravelled() {
    return rightWheelsMaster.getEncoder().getPosition();
  }

  public void updateRobotPose() {
    robotPose = driveOdometry.update(getHeading(), leftWheelsMaster.getEncoder().getPosition(), rightWheelsMaster.getEncoder().getPosition());
  }

  public void reset() {
    leftWheelsMaster.getEncoder().setPosition(0);
    rightWheelsMaster.getEncoder().setPosition(0);

    driveOdometry.resetPosition(new Pose2d(), getHeading());

    ahrs.reset();
  }

  public boolean isHighGear() {
    return isHighGear;
  }

  public RamseteController getRamseteController() {
    return ramseteController;
  }

  public DifferentialDriveKinematics getDriveKinematics() {
    return driveKinematics;
  }

  public Pose2d getRobotPose() {
    return robotPose;
  }
}
