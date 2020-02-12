package frc.robot.commands.teleop;

import static frc.robot.OI.intakeButton;
import static frc.robot.Robot.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeCommand extends CommandBase {

  private static final double INTAKE_OUTPUT = 0.75;

  public IntakeCommand() {
    addRequirements(intake);
  }

  @Override
  public void execute() {
    if(intakeButton.get()) {
      intake.setMotorPercentOutput(INTAKE_OUTPUT);
    } else {
      intake.setMotorPercentOutput(0);
    }
  }
}
