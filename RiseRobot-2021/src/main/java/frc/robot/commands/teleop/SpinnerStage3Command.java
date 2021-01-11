package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utils.ColorSensorHelper;
import frc.robot.utils.ColorSensorHelper.ControlPanelColor;
import frc.robot.utils.EnhancedBoolean;

import static com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;
import static edu.wpi.first.wpilibj.Timer.getFPGATimestamp;
import static frc.robot.OI.spinToColorButton;
import static frc.robot.Robot.spinner;
import static frc.robot.utils.ColorSensorHelper.ControlPanelColor.*;

public class SpinnerStage3Command extends CommandBase {

    private final static double SPIN_POWER = 0.5;

    private ControlPanelColor currentColor;
    private ControlPanelColor targetColor;
    private boolean isFinished;
    private boolean moveCW;
    private EnhancedBoolean isOnColor;
    private double onColorStartTime, onColorTime;
    private boolean backtrack;

    public SpinnerStage3Command() {
        isOnColor = new EnhancedBoolean();
    }

    @Override
    public void initialize() {
        onColorStartTime = -1;
        isOnColor.set(false);
        isOnColor.set(false);
        String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.length() > 0) {
            switch (gameData.charAt(0)) {
                // Stagger, because we want to move to where the field sensor will see the right color
                case 'B':
                    targetColor = RED;
                    break;
                case 'G':
                    targetColor = YELLOW;
                    break;
                case 'R':
                    targetColor = BLUE;
                    break;
                case 'Y':
                    targetColor = GREEN;
                    break;
                default:
                    targetColor = NONE;
                    System.out.println("Corrupt game data!");
                    isFinished = true;
                    break;
            }
        } else {
            System.out.println("Stage 3 Control Panel is not ready yet!");
            isFinished = true;
        }

        currentColor = ColorSensorHelper.getColorMatch();
        if (currentColor == NONE) {
            System.out.println("Color sensor cannot determine the color it is seeing!");
            isFinished = true;
        }

        moveCW = currentColor.moveCW(targetColor);
    }

    @Override
    public void execute() {
        // Check for transition to target and transition off
        currentColor = ColorSensorHelper.getColorMatch();
        isOnColor.set(currentColor == targetColor);
        if (isOnColor.isRisingEdge()) {
            onColorStartTime = getFPGATimestamp();
        }
        if (isOnColor.isFallingEdge()) {
            onColorTime = getFPGATimestamp() - onColorStartTime;
            backtrack = true;
            onColorStartTime = getFPGATimestamp();
            moveCW = !moveCW;
        }

        // If completely gone through, go back for half the time
        if (backtrack) {
            if (getFPGATimestamp() - onColorStartTime >= onColorTime / 2) {
                spinner.hasSpunStage3 = true;
                isFinished = true;
            }
        }
        spinner.set(PercentOutput, getSpinPower());
    }

    private double getSpinPower() {
        return moveCW ? SPIN_POWER : -SPIN_POWER;
    }

    @Override
    public void end(boolean interrupted) {
        spinner.set(PercentOutput, 0);
    }

    @Override
    public boolean isFinished() {
        return isFinished || !spinToColorButton.get();
    }
}
