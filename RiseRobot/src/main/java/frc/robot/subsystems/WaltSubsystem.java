package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.ArrayList;
import java.util.function.Supplier;

public abstract class WaltSubsystem extends SubsystemBase {

  protected NetworkTable pitCheckNT;

  public WaltSubsystem() {
    pitCheckNT = NetworkTableInstance.getDefault().getTable("Pit Check").
        getSubTable(getClass().getName());
  }

  @Override
  public void periodic() {
    sendToNT();
  }

  /**
   * @return a supplier of any failures that were found during the pit check.
   */
  public abstract Supplier<ArrayList<String>> getPitCheckFunction();

  public abstract void sendToNT();

}
