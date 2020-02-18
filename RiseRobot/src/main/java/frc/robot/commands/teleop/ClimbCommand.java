package frc.robot.commands.teleop;

import static com.ctre.phoenix.motorcontrol.TalonFXControlMode.PercentOutput;
import static frc.robot.OI.climberDownButton;
import static frc.robot.OI.climberUnlockButton;
import static frc.robot.OI.climberUpButton;
import static frc.robot.OI.gamepad;
import static frc.robot.Robot.climber;
import static frc.robot.commands.teleop.ClimbCommand.ToggleState.DOWN;
import static frc.robot.commands.teleop.ClimbCommand.ToggleState.UP;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimbCommand extends CommandBase {

  private ToggleState toggleState, previousToggleState;
  private static final double HOLD_POWER = 0.1;
  private static final double DEADBAND_LOW = -0.125;
  private static final double MAX_EXTEND_POWER = 0.4;

  public ClimbCommand() {
    addRequirements(climber);
    toggleState = previousToggleState = DOWN;

    climberUpButton.whenPressed(() -> toggleState = UP);
    climberDownButton.whenPressed(() -> toggleState = DOWN);
  }

  @Override
  public void execute() {
    // Run the corresponding initialize method upon change
    if (toggleState != previousToggleState) {
      toggleState.setSolenoidStates();
      previousToggleState = toggleState;
    }

    // Only move when the motor when it is up and unlocked
    if (toggleState == UP) {
      if (climberUnlockButton.get()) {
        climber.setClimberLock(true);

        double extendCommand = gamepad.getLeftY();

        if(extendCommand > HOLD_POWER || extendCommand < DEADBAND_LOW) {
          climber.setClimberMotorOutput(PercentOutput, Math.min(extendCommand, MAX_EXTEND_POWER));
        } else {
          climber.setClimberMotorOutput(PercentOutput, HOLD_POWER);
        }
      } else {
        climber.setClimberLock(false);
        climber.setClimberMotorOutput(PercentOutput, 0);
      }
    }
  }

  public enum ToggleState {
    // Will need to update the true/false values for the initialize methods when we see function
    DOWN {
      @Override
      public void setSolenoidStates() {
        climber.setClimberToggle(false);
      }
    }, UP {
      @Override
      public void setSolenoidStates() {
        climber.setClimberToggle(true);
      }
    };

    public abstract void setSolenoidStates();
  }
}
