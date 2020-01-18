package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.ColorSensorHelper;

public class Spinner extends SubsystemBase {

  private ColorSensorHelper colorSensorHelper = new ColorSensorHelper();

  public Spinner() {
    SmartDashboard.putNumber("IRSensorLimit", 400);
  }

  @Override
  public void periodic() {
    SmartDashboard.putString("Color", colorSensorHelper.getColorMatch().name());
    SmartDashboard.putNumber("IR Sensor", getColorSensorHelper().getProximity());
  }

  public ColorSensorHelper getColorSensorHelper() {
    return colorSensorHelper;
  }
}
