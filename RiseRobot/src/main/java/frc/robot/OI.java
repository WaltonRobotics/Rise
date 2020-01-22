package frc.robot;

import static frc.robot.Constants.ButtonMapDefaults.SHIFT_DOWN_BUTTON_KEY;
import static frc.robot.Constants.ButtonMapDefaults.SHIFT_UP_BUTTON_KEY;
import static frc.robot.Constants.Joysticks.GAMEPAD_PORT;
import static frc.robot.Constants.Joysticks.LEFT_JOYSTICK_PORT;
import static frc.robot.Constants.Joysticks.RIGHT_JOYSTICK_PORT;
import static frc.robot.Robot.dynamicButtonMap;

import edu.wpi.first.wpilibj.Joystick;
import frc.utils.EnhancedJoystickButton;
import frc.utils.Gamepad;

public class OI {

  public static Joystick leftJoystick = new Joystick(LEFT_JOYSTICK_PORT);
  public static Joystick rightJoystick = new Joystick(RIGHT_JOYSTICK_PORT);
  public static Gamepad gamepad = new Gamepad(GAMEPAD_PORT);

  /*
  Use the following format to create new buttons.

  button = new EnhancedJoystickButton(
      dynamicButtonMap.getButtonIndex(key));

  Using an EnhancedJoystickButton lets us have dynamic button mapping.
   */
  public static EnhancedJoystickButton shiftUpButton;
  public static EnhancedJoystickButton shiftDownButton;

  public static void setButtonValues() {
    shiftUpButton = new EnhancedJoystickButton(dynamicButtonMap.getButtonIndex(SHIFT_UP_BUTTON_KEY));
    shiftDownButton = new EnhancedJoystickButton(dynamicButtonMap.getButtonIndex(SHIFT_DOWN_BUTTON_KEY));
  }

}
