package frc.robot.commands.teleop;

import static com.ctre.phoenix.motorcontrol.TalonFXControlMode.PercentOutput;
import static edu.wpi.first.wpilibj.Timer.getFPGATimestamp;
import static frc.robot.OI.climberToggleButton;
import static frc.robot.OI.climberUnlockButton;
import static frc.robot.OI.gamepad;
import static frc.robot.Robot.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.utils.EnhancedBoolean;

public class ClimbCommand extends CommandBase {

  private static final double HOLD_POWER = 0.05;   // Functions as a deadband
  private static final double DEADBAND = 0.125;
  //  private static final double DEADBAND_LOW = -0.125;
  private static final double MAX_EXTEND_POWER = 0.4;
  private static final double KICK_BACK_POWER = 0.1;
  private static final double KICK_BACK_TIME = 0.1;

  private EnhancedBoolean extending;
  private double kickBackStart;

  public ClimbCommand() {
    addRequirements(climber);

    extending = new EnhancedBoolean();

    climberToggleButton.whenPressed(() -> climber.toggleClimberDeploy());
  }

  @Override
  public void execute() {
    if (climberUnlockButton.get()) {
      climber.setClimberLock(true);

      double extendCommand = -gamepad.getLeftY();

      extending.set(extendCommand > DEADBAND);

      if(!extending.get()) {
        kickBackStart = getFPGATimestamp();
      }

      if (extending.get() && getFPGATimestamp() - kickBackStart < KICK_BACK_TIME) {
        climber.setClimberMotorOutput(PercentOutput, -KICK_BACK_POWER);
      } else if (Math.abs(extendCommand) > DEADBAND) {
        climber.setClimberMotorOutput(PercentOutput, Math.min(extendCommand, MAX_EXTEND_POWER));
      } else {
        climber.setClimberMotorOutput(PercentOutput, -HOLD_POWER);
      }
    } else {
      climber.setClimberLock(false);
      climber.setClimberMotorOutput(PercentOutput, 0);
    }
  }
}
