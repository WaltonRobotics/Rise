package frc.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * Because JoystickButton is bad and doesn't have rising and falling edges, this
 * one does.
 *
 * @author Russell Newton
 */
public class EnhancedJoystickButton extends JoystickButton {

    private boolean current;
    private boolean previous;

    /**
     * Create a new EnhancedJoystickButton at {@code buttonNumber} on
     * {@code joystick}.
     */
    public EnhancedJoystickButton(Joystick joystick, int buttonNumber) {
        super(joystick, buttonNumber);
        whenPressed(new SetValue(true));
        whenReleased(new SetValue(false));
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

    /**
     * This handles the value setting, because you can only run commands on rising
     * and falling action.
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