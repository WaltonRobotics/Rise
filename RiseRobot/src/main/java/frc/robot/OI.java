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
  public static EnhancedJoystickButton intakeUpButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(INTAKE_UP_KEY));
  public static EnhancedJoystickButton intakeDownButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(INTAKE_DOWN_KEY));
  public static EnhancedJoystickButton intakeButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(INTAKE_ON_KEY));
  public static EnhancedJoystickButton shootButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(SHOOT_KEY));
  public static EnhancedJoystickButton barfButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(BARF_KEY));
  public static EnhancedJoystickButton climberUpButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(CLIMBER_UP_BUTTON_KEY));
  public static EnhancedJoystickButton climberDownButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(CLIMBER_DOWN_BUTTON_KEY));
  public static EnhancedJoystickButton climberUnlockButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(CLIMBER_UNLOCK_BUTTON_KEY));
  public static EnhancedJoystickButton overrideFrontConveyorButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(OVERRIDE_FRONT_CONVEYOR_ON));
  public static EnhancedJoystickButton overrideBackConveyorButton = new EnhancedJoystickButton(
      buttonMap.getButtonIndex(OVERRIDE_BACK_CONVEYOR_ON));
//  public static EnhancedJoystickButton overrideIntakeButton = new EnhancedJoystickButton(
//      buttonMap.getButtonIndex(OVERRIDE_INTAKE_ON)); //Shouldn't be necessary, intake button works
//  public static EnhancedJoystickButton overrideCenteringButton = new EnhancedJoystickButton(
//      buttonMap.getButtonIndex(OVERRIDE_CENTERING_ON));
  public static EnhancedJoystickButton turnToTargetButton = new EnhancedJoystickButton(
          buttonMap.getButtonIndex(TURN_TO_TARGET));
}
