package frc.robot.commands.auton;
import edu.wpi.first.networktables.NetworkTable;

public class LiveDashboard {
    private final NetworkTable liveDashboardTable = LiveDashboardTable.getTable("Live_Dashboard");

    public static LiveDashboard getInstance() {
        return instance;
    }

    private static final LiveDashboard instance = new LiveDashboard();

    public void setRobotX(double robotX) {
        liveDashboardTable.getEntry("robotX").setDouble(robotX);
    }

    public void setRobotY(double robotY) {
        liveDashboardTable.getEntry("robotY").setDouble(robotY);
    }

    public void setRobotHeading(double robotHeading) {
        liveDashboardTable.getEntry("robotHeading").setDouble(robotHeading);
    }

    public void setFollowingPath(boolean followingPath) {
        liveDashboardTable.getEntry("isFollowingPath").setBoolean(followingPath);
    }

    public void setPathX(double pathX) {
        liveDashboardTable.getEntry("pathX").setDouble(pathX);
    }

    public void setPathY(double pathY) {
        liveDashboardTable.getEntry("pathY").setDouble(pathY);
    }

    public void setPathHeading(double pathHeading) {
        liveDashboardTable.getEntry("pathHeading").setDouble(pathHeading);
    }

    private double robotX = liveDashboardTable.getEntry("robotX").getDouble(0.0);
    private double robotY = liveDashboardTable.getEntry("robotY").getDouble(0.0);
    private double robotHeading = liveDashboardTable.getEntry("robotHeading").getDouble(0.0);
    private boolean isFollowingPath = liveDashboardTable.getEntry("isFollowingPath").getBoolean(false);

    private double pathX = liveDashboardTable.getEntry("pathX").getDouble(0.0);
    private double pathY = liveDashboardTable.getEntry("pathY").getDouble(0.0);
    private double pathHeading = liveDashboardTable.getEntry("pathHeading").getDouble(0.0);

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
