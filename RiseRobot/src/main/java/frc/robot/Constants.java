/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.util.Units;
import static frc.robot.Constants.Joysticks.GAMEPAD_PORT;
import static frc.robot.Constants.Joysticks.LEFT_JOYSTICK_PORT;
import static frc.utils.EnhancedJoystickButton.POV_N;
import static frc.utils.EnhancedJoystickButton.POV_S;

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
    public static final int CENTERING_ID = 6;
    public static final int CONVEYOR_FRONT_ID = 7;
    public static final int CONVEYOR_BACK_ID = 8;

    public static final int SHOOTER_FLYWHEEL_MASTER_ID = 9;
    public static final int SHOOTER_FLYWHEEL_SLAVE_ID = 10;
    public static final int SHOOTER_TURRET_ID = 11;

    public static final int CLIMBER_ID = 12;

    public static final int SPINNER_ID = 13;

  }

  public static class PneumaticIDs {

    public static final int INTAKE_TOGGLE_ID = 0;
    public static final int CONVEYOR_STOP_ID = 1;

    public static final int CLIMBER_LOCK_ID = 2;
    public static final int CLIMBER_TOGGLE_ID = 3;

    public static final int SPINNER_TOGGLE_ID = 4;

  }

  public static class DioIDs {

    public static final int BOTTOM_CONVEYOR_SENSOR_ID = 0;
    public static final int TOP_CONVEYOR_SENSOR_ID = 1;

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
    public static final String INTAKE_UP_KEY = "Intake Up";
    public static final String INTAKE_DOWN_KEY = "Intake Down";
    public static final String INTAKE_ON_KEY = "Intake";
    public static final String SHOOT_KEY = "Shoot";
    public static final String BARF_KEY = "Slow outtake";
    public static final String CLIMBER_UP_BUTTON_KEY = "Climber Up";
    public static final String CLIMBER_DOWN_BUTTON_KEY = "Climber Down";
    public static final String CLIMBER_UNLOCK_BUTTON_KEY = "Unlock Climber";

    /*
    Add default mappings here, following the template
     */
    public static final Map<String, int[]> defaultMappings = Stream.of(new Object[][]{

//      {BUTTON_KEY,          new int[]{CONTROLLER_PORT, INDEX}},
        {SHIFT_UP_BUTTON_KEY, new int[]{LEFT_JOYSTICK_PORT, 3}},
        {SHIFT_DOWN_BUTTON_KEY, new int[]{LEFT_JOYSTICK_PORT, 2}},
        {INTAKE_UP_KEY, new int[]{GAMEPAD_PORT, POV_N}},
        {INTAKE_DOWN_KEY, new int[]{GAMEPAD_PORT, POV_S}},
        {INTAKE_ON_KEY, new int[]{GAMEPAD_PORT, 8}},
        {SHOOT_KEY, new int[]{GAMEPAD_PORT, 6}},
        {BARF_KEY, new int[]{GAMEPAD_PORT, 5}},
        {CLIMBER_UP_BUTTON_KEY, new int[]{GAMEPAD_PORT, POV_N}},
        {CLIMBER_DOWN_BUTTON_KEY, new int[]{GAMEPAD_PORT, POV_S}},
        {CLIMBER_UNLOCK_BUTTON_KEY, new int[]{GAMEPAD_PORT, 4}},

    }).collect(Collectors.toMap(n -> (String) n[0], n -> (int[]) n[1]));
  }

    public static class SmartDashboardKeys {

        public static final String AUTON_SELECT_ID = "Auton Select Id";
        public static final String IS_BLUE = "Is Blue";

    }

    public static class FieldConfiguration {

        public static final double DISTANCE_TO_REFLECTION_LINE = Units.feetToMeters(54) / 2;
        public static final double LIVE_DASHBOARD_FIELD_HEIGHT = Units.feetToMeters(27);

    }
  public static class Turret {

    public static final int TURRET_ENCODER_PORT_1 = 1;
    public static final int TURRET_ENCODER_PORT_2 = 2;

    public static final int TURRET_ROTATIONS_PER_TICK = 1;

  }

}