package frc.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.util.Units;

public class LimelightHelper {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    private static NetworkTableEntry tx = table.getEntry("tx");
    private static NetworkTableEntry ty = table.getEntry("ty");
    private static NetworkTableEntry ta = table.getEntry("ta");
    private static NetworkTableEntry tv = table.getEntry("tv");
    private static double limelightAngle = 10;
    private static double limelightHeight = 10;
    private static double targetHeight = 10;
    private LimelightHelper() {
    }

    public static double getTX() {
        return tx.getDouble(0);
    }

    public static double getTY() {
        return ty.getDouble(0);
    }

    public static double getTA() {
        return ta.getDouble(0);
    }

    public static double getTV() {
        return tv.getDouble(0);
    }

    public static double getDistance() {
        return targetHeight - limelightHeight / Math.tan(Units.degreesToRadians(20 + getTY()));
    }
}
