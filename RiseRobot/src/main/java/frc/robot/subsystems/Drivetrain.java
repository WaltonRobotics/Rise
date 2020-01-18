package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Robot.currentRobot;

public class Drivetrain extends SubsystemBase {

    private static final Encoder encoderRight = new Encoder(
            new DigitalInput(0),
            new DigitalInput(1));
    private static final Encoder encoderLeft = new Encoder(
            new DigitalInput(2),
            new DigitalInput(3));
    private final AHRS ahrs = new AHRS(SPI.Port.kMXP);
    private CANSparkMax rightWheelsMaster = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless);
    private CANSparkMax rightWheelsSlave = new CANSparkMax(2, CANSparkMax.MotorType.kBrushless);
    private CANSparkMax leftWheelsMaster = new CANSparkMax(3, CANSparkMax.MotorType.kBrushless);
    private CANSparkMax leftWheelsSlave = new CANSparkMax(4, CANSparkMax.MotorType.kBrushless);
    private DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(currentRobot.getTrackWidth());
    private DifferentialDriveOdometry driveOdometry = new DifferentialDriveOdometry(getHeading());

    private RamseteController ramseteController = new RamseteController(currentRobot.getKBeta(), currentRobot.getKZeta());

    private Pose2d robotPose = new Pose2d();
    private boolean isHighGear = true;

    public Drivetrain() {
        motorSetUp();
        zeroHeading();
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

        encoderLeft.setDistancePerPulse(currentRobot.getDistancePerPulse());
        encoderRight.setDistancePerPulse(currentRobot.getDistancePerPulse());
    }

    public void setSpeeds(double leftPower, double rightPower) {
        leftWheelsMaster.set(leftPower);
        rightWheelsMaster.set(rightPower);
    }

    public void setVoltages(double leftVoltage, double rightVoltage) {
        leftWheelsMaster.setVoltage(leftVoltage);
        rightWheelsMaster.setVoltage(rightVoltage);
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

    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(-ahrs.getYaw());  // counter clock wise positive
    }

    /**
     * Zeroes the heading of the robot.
     */
    private void zeroHeading() {
        ahrs.zeroYaw();
    }

    public DifferentialDriveWheelSpeeds getSpeeds() {
        return new DifferentialDriveWheelSpeeds(
                leftWheelsMaster.getEncoder().getVelocity() / 60,
                rightWheelsMaster.getEncoder().getVelocity() / 60
        );
    }

    public double leftMetersTravelled() {
        return encoderLeft.getDistance();
    }

    public double rightMetersTravelled() {
        return encoderRight.getDistance();
    }

    private void updateRobotPose() {
        robotPose = driveOdometry.update(getHeading(), leftWheelsMaster.getEncoder().getPosition(), rightWheelsMaster.getEncoder().getPosition());
    }

    public void reset() {
        leftWheelsMaster.getEncoder().setPosition(0);
        rightWheelsMaster.getEncoder().setPosition(0);

        encoderLeft.reset();
        encoderRight.reset();

        driveOdometry.resetPosition(new Pose2d(), getHeading());

        zeroHeading();
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
