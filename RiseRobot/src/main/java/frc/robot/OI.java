package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.utils.Gamepad;

import static frc.robot.Constants.Joystick.*;

public class OI {
    public static Joystick leftJoystick = new Joystick(LEFT_JOYSTICK_PORT);
    public static Joystick rightJoystick = new Joystick(RIGHT_JOYSTICK_PORT);

    public static JoystickButton shiftUpButton = new JoystickButton(leftJoystick, SHIFT_UP_BUTTON);
    public static JoystickButton shiftDownButton = new JoystickButton(leftJoystick, SHIFT_DOWN_BUTTON);

    public static Gamepad gamepad = new Gamepad(GAMEPAD_PORT);

}
