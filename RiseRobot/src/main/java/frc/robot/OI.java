package frc.robot;

import static frc.robot.Constants.ButtonMapDefaults.*;
import static frc.robot.Constants.Joysticks.GAMEPAD_PORT;
import static frc.robot.Constants.Joysticks.LEFT_JOYSTICK_PORT;
import static frc.robot.Constants.Joysticks.RIGHT_JOYSTICK_PORT;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import frc.utils.DynamicButtonMap;
import frc.utils.EnhancedJoystickButton;
import frc.utils.Gamepad;

public class OI {

  public static Joystick leftJoystick = new Joystick(LEFT_JOYSTICK_PORT);
  public static Joystick rightJoystick = new Joystick(RIGHT_JOYSTICK_PORT);
  public static Gamepad gamepad = new Gamepad(GAMEPAD_PORT);

  public static DynamicButtonMap buttonMap = new DynamicButtonMap(
      new GenericHID[]{leftJoystick, rightJoystick, gamepad}, defaultMappings);

  /*
  Use the following format to create new buttons.

  button = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(key));

  Using an EnhancedJoystickButton lets us have dynamic button mapping.
   */
  public static EnhancedJoystickButton shiftUpButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(SHIFT_UP_BUTTON_KEY));
  public static EnhancedJoystickButton shiftDownButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(SHIFT_DOWN_BUTTON_KEY));
  public static EnhancedJoystickButton alignButton = new EnhancedJoystickButton(
          buttonMap.getButtonIndex(ALIGN_TARGET_BUTTON_KEY));

  static {
    alignButton.whenPressed(new FindTargetAngle());
  }

}
