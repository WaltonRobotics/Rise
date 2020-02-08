package frc.robot.robots;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.util.Units;

import static edu.wpi.first.networktables.EntryListenerFlags.kNew;
import static edu.wpi.first.networktables.EntryListenerFlags.kUpdate;

public class CompDeepSpace implements WaltRobot {

    // Config constants
    private final int shifterChannel = 0;

    private final PIDController leftPIDController = new PIDController(1.45, 0, 0);
    private final PIDController rightPIDController = new PIDController(1.45, 0, 0);

    private final SimpleMotorFeedforward drivetrainFeedforward = new SimpleMotorFeedforward(0.194, 2.11, 0.525);

    private final Solenoid shifter = new Solenoid(shifterChannel);

    private final double highGearRatio = 6.58905 * 1.051;
    private final double wheelDiameter = Units.inchesToMeters(5);

    private PIDController turnPIDController;
    private PIDController distancePIDController;

    public CompDeepSpace() {
        turnPIDController = new PIDController(0.01, 0, 0);
        turnPIDController.enableContinuousInput(-180f, 180f);
        turnPIDController.setTolerance(0.5, 0.5);

        distancePIDController = new PIDController(0.0001, 0, 0);
        distancePIDController.setTolerance(0.05);

        sendTurnToAngleToNT();
    }

    // 32 l
    // 38 w

    @Override
    public double getTrackWidth() {
        return 0.790048721621379;
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
    public PIDController getTurnPIDController() {
        return turnPIDController;
    }

    @Override
    public PIDController getDistancePIDController() {
        return distancePIDController;
    }

    @Override
    public SimpleMotorFeedforward getFlywheelFeedforward() {
        return null;
    }

    @Override
    public SimpleMotorFeedforward getDrivetrainFeedforward() {
        return drivetrainFeedforward;
    }

    @Override
    public double getVelocityFactor() {
        return 1 / highGearRatio * Math.PI * wheelDiameter / 60;
    }

    @Override
    public double getPositionFactor() {
        return 1 / highGearRatio * Math.PI * wheelDiameter;
    }

    @Override
    public double getDistancePerPulse() {
        return 0.0005706796580;
    }

    @Override
    public Solenoid getShifter() {
        return shifter;
    }

    @Override
    public double getMinimumShiftingTime() {
        return 1.0;
    }

    @Override
    public double getTrajectoryTimeAdditive() {
        return 0.0;
    }

    @Override
    public double getMaxAlignmentTime() {
        return 3.0;
    }

    @Override
    public double getVisionAlignKp() {
        return 0.0005;
    }

    @Override
    public double getVisionAlignKs() {
        return 0.1;
    }

    @Override
    public double getVisionAlignTxTolerance() {
        return 0.5;
    }

    private void sendTurnToAngleToNT() {
        NetworkTableInstance nti = NetworkTableInstance.getDefault();
        NetworkTable turnToPID = nti.getTable("Turn To Angle PID");
        NetworkTableEntry ttP = turnToPID.getEntry("P");
        NetworkTableEntry ttI = turnToPID.getEntry("I");
        NetworkTableEntry ttD = turnToPID.getEntry("D");

        ttP.setDefaultNumber(0.008);
        ttI.setDefaultNumber(0);
        ttD.setDefaultNumber(0);

        ttP.addListener(notification -> turnPIDController.setP(notification.value.getDouble()), kNew | kUpdate);
        ttI.addListener(notification -> turnPIDController.setI(notification.value.getDouble()), kNew | kUpdate);
        ttD.addListener(notification -> turnPIDController.setD(notification.value.getDouble()), kNew | kUpdate);
    }

}
