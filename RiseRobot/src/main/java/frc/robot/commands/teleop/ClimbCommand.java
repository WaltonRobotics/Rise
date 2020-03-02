package frc.robot.commands.teleop;

import static com.ctre.phoenix.motorcontrol.TalonFXControlMode.PercentOutput;
import static frc.robot.OI.climberToggleButton;
import static frc.robot.OI.climberUnlockButton;
import static frc.robot.OI.gamepad;
import static frc.robot.Robot.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimbCommand extends CommandBase {

//  private ToggleState toggleState, previousToggleState;
  private static final double HOLD_POWER = 0.1;
  private static final double DEADBAND_LOW = -0.125;
  private static final double MAX_EXTEND_POWER = 0.4;

  public ClimbCommand() {
    addRequirements(climber);
//    toggleState = previousToggleState = DOWN;

    climberToggleButton.whenPressed(() -> climber.toggleClimberDeploy());
  }

  @Override
  public void execute() {
    // Run the corresponding initialize method upon change
//    if (toggleState != previousToggleState) {
//      toggleState.setSolenoidStates();
//      previousToggleState = toggleState;
//    }

    // Only move when the motor when it is unlocked
//    if (toggleState == UP) {
      if (climberUnlockButton.get()) {
        climber.setClimberLock(false);

        double extendCommand = gamepad.getLeftY();

        if(extendCommand > HOLD_POWER || extendCommand < DEADBAND_LOW) {
          climber.setClimberMotorOutput(PercentOutput, Math.min(extendCommand, MAX_EXTEND_POWER));
        } else {
          climber.setClimberMotorOutput(PercentOutput, HOLD_POWER);
        }
      } else {
        climber.setClimberLock(true);
        climber.setClimberMotorOutput(PercentOutput, 0);
      }
//    }
  }
}
