package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Robot.currentRobot;

public class Drivetrain extends SubsystemBase {

  public static final CANSparkMax rightWheelsMaster = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless);
  public static final CANSparkMax rightWheelsSlave = new CANSparkMax(2, CANSparkMax.MotorType.kBrushless);

  public static final CANSparkMax leftWheelsMaster = new CANSparkMax(3, CANSparkMax.MotorType.kBrushless);
  public static final CANSparkMax leftWheelsSlave = new CANSparkMax(4, CANSparkMax.MotorType.kBrushless);

  private final AHRS ahrs = new AHRS(SPI.Port.kMXP);


  public Drivetrain() {
    ahrs.zeroYaw();
    motorSetUp();
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
    leftWheelsSlave.getEncoder().setVelocityConversionFactor(currentRobot.getRpmToMeters());
    rightWheelsMaster.getEncoder().setVelocityConversionFactor(currentRobot.getRpmToMeters());
    rightWheelsSlave.getEncoder().setVelocityConversionFactor(currentRobot.getRpmToMeters());

  }

  public void setSpeeds(double leftPower, double rightPower) {
    rightWheelsMaster.set(rightPower);
    leftWheelsMaster.set(leftPower);
  }

  public void setArcadeSpeeds(double xSpeed, double zRotation) {
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
}
