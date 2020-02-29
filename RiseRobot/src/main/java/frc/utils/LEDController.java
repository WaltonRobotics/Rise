package frc.utils;


import static frc.robot.Constants.DioIDs.LED1_ID;
import static frc.robot.Constants.DioIDs.LED2_ID;

import edu.wpi.first.wpilibj.DigitalOutput;

public final class LEDController {

  private static DigitalOutput LED1 = new DigitalOutput(LED1_ID);
  private static DigitalOutput LED2 = new DigitalOutput(LED2_ID);

  private LEDController() {
  }

  public static void setLEDAutoAlignMode() {
    LED1.set(true);
    LED2.set(true);
  }

  public static void setLEDFoundTargetMode() {
    LED1.set(false);
    LED2.set(true);
  }

  public static void setLEDNoTargetFoundMode() {
    LED1.set(false);
    LED2.set(false);
  }
}
