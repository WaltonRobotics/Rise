package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.CANBusIDs.*;
import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class Drivetrain extends SubsystemBase {

    private final AHRS ahrs = new AHRS(SPI.Port.kMXP);
    private final CANSparkMax rightWheelsMaster = new CANSparkMax(DRIVE_RIGHT_MASTER_ID, CANSparkMax.MotorType.kBrushless);
    private final CANSparkMax rightWheelsSlave = new CANSparkMax(DRIVE_RIGHT_SLAVE_ID, CANSparkMax.MotorType.kBrushless);
    private final CANSparkMax leftWheelsMaster = new CANSparkMax(DRIVE_LEFT_MASTER_ID, CANSparkMax.MotorType.kBrushless);
    private final CANSparkMax leftWheelsSlave = new CANSparkMax(DRIVE_LEFT_SLAVE_ID, CANSparkMax.MotorType.kBrushless);
    private DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(currentRobot.getTrackWidth());
    private DifferentialDriveOdometry driveOdometry = new DifferentialDriveOdometry(getHeading());

    private RamseteController ramseteController = new RamseteController();

    private Pose2d robotPose = new Pose2d();
    private boolean isHighGear = true;

    public Drivetrain() {
        motorSetUp();
        resetHardware();
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
        leftWheelsMaster.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightWheelsSlave.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightWheelsMaster.setIdleMode(CANSparkMax.IdleMode.kCoast);

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

        leftWheelsMaster.getEncoder().setVelocityConversionFactor(currentRobot.getVelocityFactor());
        rightWheelsMaster.getEncoder().setVelocityConversionFactor(currentRobot.getVelocityFactor());

        leftWheelsMaster.getEncoder().setPositionConversionFactor(currentRobot.getPositionFactor());
        rightWheelsMaster.getEncoder().setPositionConversionFactor(currentRobot.getPositionFactor());

        leftWheelsMaster.getPIDController().setP(currentRobot.getLeftPIDController().getP(), 0);
        leftWheelsMaster.getPIDController().setI(currentRobot.getLeftPIDController().getI(), 0);
        leftWheelsMaster.getPIDController().setD(currentRobot.getLeftPIDController().getD(), 0);

        rightWheelsMaster.getPIDController().setP(currentRobot.getRightPIDController().getP(), 0);
        rightWheelsMaster.getPIDController().setI(currentRobot.getRightPIDController().getI(), 0);
        rightWheelsMaster.getPIDController().setD(currentRobot.getRightPIDController().getD(), 0);

    }

    public void setDutyCycles(double leftDutyCycle, double rightDutyCycle) {
        leftWheelsMaster.set(leftDutyCycle);
        rightWheelsMaster.set(rightDutyCycle);
    }

    public void setVoltages(double leftVoltage, double rightVoltage) {
        leftWheelsMaster.setVoltage(leftVoltage);
        rightWheelsMaster.setVoltage(rightVoltage);
    }

    public void setVelocities(double leftVelocity, double leftFeedForward, double rightVelocity, double rightFeedForward, int sparkMaxPIDSlot) {
        leftWheelsMaster.getPIDController().setReference(leftVelocity, ControlType.kVelocity, sparkMaxPIDSlot, leftFeedForward);
        rightWheelsMaster.getPIDController().setReference(rightVelocity, ControlType.kVelocity, sparkMaxPIDSlot, rightFeedForward);
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

        setDutyCycles(leftMotorOutput, rightMotorOutput);
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
    public void zeroHeading() {
        ahrs.zeroYaw();
    }

    public DifferentialDriveWheelSpeeds getSpeeds() {
        return new DifferentialDriveWheelSpeeds(
                getCANEncoderLeftVelocity(),
                getCANEncoderRightVelocity()
        );
    }

    private void updateRobotPose() {
        robotPose = driveOdometry.update(getHeading(), leftWheelsMaster.getEncoder().getPosition(), rightWheelsMaster.getEncoder().getPosition());
    }

    public void resetHardware() {
        zeroNeoEncoders();
        zeroHeading();
    }

    public void resetOdometry(Pose2d startingPose) {
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
