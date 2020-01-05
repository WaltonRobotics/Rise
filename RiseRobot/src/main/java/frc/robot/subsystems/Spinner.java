package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.ColorSensorHelper;

public class Spinner extends SubsystemBase {

  private ColorSensorHelper colorSensorHelper = new ColorSensorHelper();

  public Spinner() {

  }

  public ColorSensorHelper getColorSensorHelper() {
    return colorSensorHelper;
  }
}
