package frc.robot.commands.auton;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LiveDashboardTable {
    private static final NetworkTableInstance instance = NetworkTableInstance.getDefault();

    public NetworkTableEntry getEntry(String name){
            return instance.getEntry(name);
    }

    public static NetworkTable getTable(String name){
        return instance.getTable(name);
    }
}
