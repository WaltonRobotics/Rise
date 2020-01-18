package frc.robot.commands.auton;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.geometry.Pose2d;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LiveDashboard {
    private final NetworkTable liveDashboardTable = LiveDashboardTable.getTable("Live_Dashboard");

    double robotX = liveDashboardTable.getEntry("robotX").getDouble(0.0);
    double robotY = liveDashboardTable.getEntry("robotY").getDouble(0.0);
    double robotHeading = liveDashboardTable.getEntry("robotHeading").getDouble(0.0);
    boolean isFollowingPath = liveDashboardTable.getEntry("robotHeading").getBoolean(false);

    double pathX = liveDashboardTable.getEntry("pathX").getDouble(0.0);
    double pathY = liveDashboardTable.getEntry("pathX").getDouble(0.0);
    double pathHeading = liveDashboardTable.getEntry("pathX").getDouble(0.0);
}
