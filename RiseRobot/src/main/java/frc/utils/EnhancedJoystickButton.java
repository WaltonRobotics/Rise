package frc.utils;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import java.util.function.IntSupplier;

/**
 * JoystickButton wrapper class with built in rising and falling edge detection.
 *
 * @author Russell Newton, Walton Robotics
 */
public class EnhancedJoystickButton extends JoystickButton {

  private final IntSupplier buttonNumberGetter;
  private final GenericHID m_joystick;
  private boolean current;
  private boolean previous;

  /**
   * Create a new EnhancedJoystickButton on {@code joystick}. Because {@code buttonNumberGetter} is
   * an IntSupplier, the actual button index can be dynamically set.
   */
  public EnhancedJoystickButton(Joystick joystick, IntSupplier buttonNumberGetter) {
    super(joystick, 0);
    this.m_joystick = joystick;
    this.buttonNumberGetter = buttonNumberGetter;
    whenPressed(new SetValue(true));
    whenReleased(new SetValue(false));
  }

  /**
   * Create a new EnhancedJoystickButton at {@code buttonNumber} on {@code joystick}.
   */
  public EnhancedJoystickButton(Joystick joystick, int buttonNumber) {
    this(joystick, () -> buttonNumber);
  }

  /**
   * Returns true if the button has just been pressed.
   */
  public boolean isRisingEdge() {
    return current && !previous;
  }

  /**
   * Returns true if the button has just been released.
   */
  public boolean isFallingEdge() {
    return !current && previous;
  }

  @Override
  public boolean get() {
    return m_joystick.getRawButton(buttonNumberGetter.getAsInt());
  }

  /**
   * This handles the value setting, because you can only run commands on rising and falling
   * action.
   *
   * @author Russell Newton, Walton Robotics
   */
  private class SetValue extends CommandBase {

    /**
     * Sets the {@code EnhancedJoystickButton} values, as necessary.
     */
    public SetValue(boolean value) {
      previous = current;
      current = value;
    }

    /**
     * Stop immediately.
     */
    @Override
    public boolean isFinished() {
      return true;
    }
  }

}