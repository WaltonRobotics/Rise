/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.Constants.Joysticks.GAMEPAD_PORT;
import static frc.robot.Constants.Joysticks.RIGHT_JOYSTICK_PORT;
import static frc.robot.Constants.Joysticks.LEFT_JOYSTICK_PORT;

import java.util.Map;
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

  public static class CANBusIDs {

    public static final int DRIVE_RIGHT_MASTER_ID = 1;
    public static final int DRIVE_RIGHT_SLAVE_ID = 2;
    public static final int DRIVE_LEFT_MASTER_ID = 3;
    public static final int DRIVE_LEFT_SLAVE_ID = 4;

    public static final int INTAKE_ID = 5;
    public static final int CONVEYOR_ID = 6;

    public static final int SHOOTER_FLYWHEEL_MASTER_ID = 7;
    public static final int SHOOTER_FLYWHEEL_SLAVE_ID = 8;
    public static final int SHOOTER_TURRET_ID = 9;

    public static final int CLIMBER_ID = 10;

    public static final int SPINNER_ID = 11;

  }

  public static class PneumaticIDs {

    public static final int INTAKE_TOGGLE_ID = 0;
    public static final int CONVEYOR_STOP_ID = 1;

    public static final int CLIMBER_LOCK_ID = 2;
    public static final int CLIMBER_TOGGLE_ID = 3;

    public static final int SPINNER_TOGGLE_ID = 4;

  }

  /**
   * The Joysticks class contains port mappings for the HID controllers.
   */
  public static class Joysticks {

    public static final int LEFT_JOYSTICK_PORT = 0;
    public static final int RIGHT_JOYSTICK_PORT = 1;
    public static final int GAMEPAD_PORT = 2;

  }

  /**
   * The ButtonMapDefaults class contains button mappings represented by String keys in a Map. Use
   * {@code getButtonIndex()} and {@code setButtonIndex()} in order to get the button indices.
   * IntSuppliers are used so that the Map can be dynamic.
   */
  public static class ButtonMapDefaults {

    /*
    Add button keys here. When you add new ones, be sure to add their default mappings below.
    Default indexes can be negative.
     */
    public static final String SHIFT_UP_BUTTON_KEY = "Shift Up";
    public static final String SHIFT_DOWN_BUTTON_KEY = "Shift Down";
    public static final String ALIGN_TARGET_BUTTON_KEY = "Align Target";

    /*
    Add default mappings here, following the template
     */
    public static final Map<String, int[]> defaultMappings = Stream.of(new Object[][]{

//      {BUTTON_KEY,          new int[]{CONTROLLER_PORT, INDEX}},
        {SHIFT_UP_BUTTON_KEY, new int[]{LEFT_JOYSTICK_PORT, 3}},
        {SHIFT_DOWN_BUTTON_KEY, new int[]{LEFT_JOYSTICK_PORT, 2}},
            {ALIGN_TARGET_BUTTON_KEY, new int[]{LEFT_JOYSTICK_PORT, 4}}

    }).collect(Collectors.toMap(n -> (String) n[0], n -> (int[]) n[1]));
  }

}