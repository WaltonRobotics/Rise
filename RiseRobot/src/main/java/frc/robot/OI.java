package frc.robot;

import static frc.robot.Constants.ButtonMap.SHIFT_DOWN_BUTTON_KEY;
import static frc.robot.Constants.ButtonMap.SHIFT_UP_BUTTON_KEY;
import static frc.robot.Constants.ButtonMap.getButtonIndex;
import static frc.robot.Constants.Joysticks.GAMEPAD_PORT;
import static frc.robot.Constants.Joysticks.LEFT_JOYSTICK_PORT;
import static frc.robot.Constants.Joysticks.RIGHT_JOYSTICK_PORT;

import edu.wpi.first.wpilibj.Joystick;
import frc.utils.EnhancedJoystickButton;
import frc.utils.Gamepad;

public class OI {

  public static Joystick leftJoystick = new Joystick(LEFT_JOYSTICK_PORT);
  public static Joystick rightJoystick = new Joystick(RIGHT_JOYSTICK_PORT);
  public static Gamepad gamepad = new Gamepad(GAMEPAD_PORT);

  /*
  Use the following format to create new buttons.

  public static EnhancedJoystickButton button = new EnhancedJoystickButton(getButtonIndex(key));

  Using an EnhancedJoystickButton lets us have dynamic button mapping.
   */
  public static EnhancedJoystickButton shiftUpButton = new EnhancedJoystickButton(
      getButtonIndex(SHIFT_UP_BUTTON_KEY));
  public static EnhancedJoystickButton shiftDownButton = new EnhancedJoystickButton(
      getButtonIndex(SHIFT_DOWN_BUTTON_KEY));

}
