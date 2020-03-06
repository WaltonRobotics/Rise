package frc.robot.commands.teleop;

import static com.ctre.phoenix.motorcontrol.TalonFXControlMode.PercentOutput;
import static frc.robot.OI.climberToggleButton;
import static frc.robot.OI.gamepad;
import static frc.robot.Robot.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.utils.LEDController;

public class ClimbCommand extends CommandBase {

  private static final double HOLD_POWER = 0.075;
  private static final double DEADBAND = 0.125;
  //  private static final double DEADBAND_LOW = -0.125;
  private static final double MAX_EXTEND_POWER = 0.7;

  public ClimbCommand() {
    addRequirements(climber);
  }

  @Override
  public void execute() {
    double climbCommand = -gamepad.getLeftY();

    if (!climber.getClimberToggle() && Math.abs(climbCommand) > DEADBAND) {
      climber.setClimberMotorOutput(PercentOutput, Math.min(climbCommand, MAX_EXTEND_POWER));
      LEDController.setLEDClimbingMode();
    } else {
      climber.setClimberMotorOutput(PercentOutput, -HOLD_POWER);
      LEDController.setLEDPassiveMode();
    }
  }

  @Override
  public void end(boolean interrupted) {
    climber.setClimberMotorOutput(PercentOutput, 0);
  }
}
