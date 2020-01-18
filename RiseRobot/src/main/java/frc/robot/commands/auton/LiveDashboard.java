package frc.robot.commands.auton;
import edu.wpi.first.networktables.NetworkTable;

public class LiveDashboard {
    private final NetworkTable liveDashboardTable = LiveDashboardTable.getTable("Live_Dashboard");

    private double robotX = liveDashboardTable.getEntry("robotX").getDouble(0.0);
    private double robotY = liveDashboardTable.getEntry("robotY").getDouble(0.0);
    private double robotHeading = liveDashboardTable.getEntry("robotHeading").getDouble(0.0);
    private boolean isFollowingPath = liveDashboardTable.getEntry("robotHeading").getBoolean(false);

    private double pathX = liveDashboardTable.getEntry("pathX").getDouble(0.0);
    private double pathY = liveDashboardTable.getEntry("pathX").getDouble(0.0);
    private double pathHeading = liveDashboardTable.getEntry("pathX").getDouble(0.0);

    public double getRobotX() {
        return robotX;
    }

    public double getRobotY() {
        return robotY;
    }

    public double getRobotHeading() {
        return robotHeading;
    }

    public boolean isFollowingPath() {
        return isFollowingPath;
    }

    public double getPathX() {
        return pathX;
    }

    public double getPathY() {
        return pathY;
    }

    public double getPathHeading() {
        return pathHeading;
    }
}
