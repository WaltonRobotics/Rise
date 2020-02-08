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
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.CANBusIDs.DRIVE_LEFT_MASTER_ID;
import static frc.robot.Constants.CANBusIDs.DRIVE_LEFT_SLAVE_ID;
import static frc.robot.Constants.CANBusIDs.DRIVE_RIGHT_MASTER_ID;
import static frc.robot.Constants.CANBusIDs.DRIVE_RIGHT_SLAVE_ID;
import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class Drivetrain extends SubsystemBase {

//    private static final Encoder encoderRight = new Encoder(
//            new DigitalInput(0),
//            new DigitalInput(1));
//    private static final Encoder encoderLeft = new Encoder(
//            new DigitalInput(2),
//            new DigitalInput(3));
    private final AHRS ahrs = new AHRS(SPI.Port.kMXP);
    private CANSparkMax rightWheelsMaster = new CANSparkMax(DRIVE_RIGHT_MASTER_ID, CANSparkMax.MotorType.kBrushless);
    private CANSparkMax rightWheelsSlave = new CANSparkMax(DRIVE_RIGHT_SLAVE_ID, CANSparkMax.MotorType.kBrushless);
    private CANSparkMax leftWheelsMaster = new CANSparkMax(DRIVE_LEFT_MASTER_ID, CANSparkMax.MotorType.kBrushless);
    private CANSparkMax leftWheelsSlave = new CANSparkMax(DRIVE_LEFT_SLAVE_ID, CANSparkMax.MotorType.kBrushless);
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
        SmartDashboard.putNumber("Left neo encoder velocity", drivetrain.getCANEncoderLeftVelocity());
        SmartDashboard.putNumber("right neo encoder velocity", drivetrain.getCANEncoderRightVelocity());
        SmartDashboard.putNumber("Left neo encoder distance", drivetrain.getCANEncoderLeftMeters());
        SmartDashboard.putNumber("right neo encoder distance", drivetrain.getCANEncoderRightMeters());
    }

    public void motorSetUp() {
        leftWheelsMaster.restoreFactoryDefaults();
        leftWheelsSlave.restoreFactoryDefaults();
        rightWheelsMaster.restoreFactoryDefaults();
        rightWheelsSlave.restoreFactoryDefaults();

        leftWheelsMaster.setInverted(true);

        leftWheelsSlave.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftWheelsMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightWheelsSlave.setIdleMode(CANSparkMax.IdleMode.kBrake);
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

        double velocityFactor = 1 / 6.58905 * Math.PI * .127 / 60;
        double positionFactor = 1 / 6.58905 * Math.PI * .127;

        leftWheelsMaster.getEncoder().setVelocityConversionFactor(velocityFactor);
        rightWheelsMaster.getEncoder().setVelocityConversionFactor(velocityFactor);

        leftWheelsMaster.getEncoder().setPositionConversionFactor(positionFactor);
        rightWheelsMaster.getEncoder().setPositionConversionFactor(positionFactor);

//        encoderLeft.setDistancePerPulse(currentRobot.getDistancePerPulse());
//        encoderRight.setDistancePerPulse(currentRobot.getDistancePerPulse());
//        encoderRight.setReverseDirection(true);
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
        return Rotation2d.fromDegrees(-ahrs.getAngle());  // counter clock wise positive
    }

    /**
     * Zeroes the heading of the robot.
     */
    private void zeroHeading() {
        ahrs.zeroYaw();
    }

    public DifferentialDriveWheelSpeeds getSpeeds() {
        return new DifferentialDriveWheelSpeeds(
                getCANEncoderLeftVelocity(),
                getCANEncoderRightVelocity()
        );
    }

    public double leftEncoderPulses() {
//        return encoderLeft.get();
        return leftWheelsMaster.getEncoder().getPosition();
    }

    public double rightEncoderPulses() {
//        return encoderRight.get();
        return rightWheelsMaster.getEncoder().getPosition();
    }

    private void updateRobotPose() {
        robotPose = driveOdometry.update(getHeading(), leftWheelsMaster.getEncoder().getPosition(), rightWheelsMaster.getEncoder().getPosition());
    }

    public void reset() {
//        encoderLeft.reset();
//        encoderRight.reset();

        zeroHeading();
        zeroNeoEncoders();
    }

    public void reset(Pose2d startingPose) {
        reset();

        driveOdometry.resetPosition(startingPose, getHeading());
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

    public double getCANEncoderLeftMeters() {
        return leftWheelsMaster.getEncoder().getPosition();
    }

    public double getCANEncoderRightMeters() {
        return rightWheelsMaster.getEncoder().getPosition();
    }

    public double getCANEncoderLeftVelocity() {
        return leftWheelsMaster.getEncoder().getVelocity();
    }

    public double getCANEncoderRightVelocity() {
        return rightWheelsMaster.getEncoder().getVelocity();
    }

    public void zeroNeoEncoders() {
        rightWheelsMaster.getEncoder().setPosition(0);
        leftWheelsMaster.getEncoder().setPosition(0);
    }
}
