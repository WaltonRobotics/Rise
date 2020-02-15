package frc.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.util.Units;

import static frc.robot.Robot.currentRobot;

public class LimelightHelper {
    private LimelightHelper() {
    }

    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry tx = table.getEntry("tx");
    private static NetworkTableEntry ty = table.getEntry("ty");
    private static NetworkTableEntry ta = table.getEntry("ta");
    private static NetworkTableEntry tv = table.getEntry("tv");

    private static double limelightAngle = 10;
    private static double limelightHeight = 10;
    private static double targetHeight = 10;

    /**
     * @return tx The x angle from target in degrees
     */
    public static double getTX() {
        return tx.getDouble(0);
    }

    /**
     * @return ty The y angle from target in degrees
     */
    public static double getTY() {
        return ty.getDouble(0);
    }

    /**
     * @return ta The area of the target
     */
    public static double getTA() {
        return ta.getDouble(0);
    }

    /**
     * @return tv The number of targets in the field of view
     */
    public static double getTV() {
        return tv.getDouble(0);
    }

    /**
     * @return distance The distance to the target in meters
     */
    public static double getDistanceMeters() {
        return Units.inchesToMeters(targetHeight - limelightHeight / Math.tan(Units.degreesToRadians(currentRobot.getMountingAngle() + getTY())));
    }
}
