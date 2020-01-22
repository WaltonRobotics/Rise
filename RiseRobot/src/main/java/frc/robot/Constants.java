/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.OI.gamepad;
import static frc.robot.OI.leftJoystick;
import static frc.robot.OI.rightJoystick;

import edu.wpi.first.wpilibj.Joystick;
import frc.utils.DynamicButtonMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  /**
   * The Joysticks class contains port mappings for the HID controllers.
   */
  public static class Joysticks {

    public static final int LEFT_JOYSTICK_PORT = 0;
    public static final int RIGHT_JOYSTICK_PORT = 1;
    public static final int GAMEPAD_PORT = 2;

    /*
    If new controllers are added, place them in the initializer list.
     */
    static final List<Joystick> controllers = Stream.of(
        new Joystick[]{
            // This is the initializer list. Place new controllers here.
            leftJoystick, rightJoystick, gamepad,
        }).collect(Collectors.toList());

  }

  /**
   * The ButtonMapDefaults class contains button mappings represented by String keys in a Map. Use
   * {@code getButtonIndex()} and {@code setButtonIndex()} in order to get the button indices.
   * IntSuppliers are used so that the Map can be dynamic.
   */
  public static class ButtonMapDefaults {

    /*
    Add button keys here. When you add new ones, you should also run writeButtonMapToFile().
    This will update the Json file to match the default values.
     */
    public static final String SHIFT_UP_BUTTON_KEY = "Shift Up";
    public static final String SHIFT_DOWN_BUTTON_KEY = "Shift Down";

    /*
    Add default mappings here.
     */
    public static void setDefaults(){
      DynamicButtonMap.addDefaultMapping(SHIFT_UP_BUTTON_KEY, leftJoystick, 3);
      DynamicButtonMap.addDefaultMapping(SHIFT_DOWN_BUTTON_KEY, leftJoystick, 2);
    }
  }

}
