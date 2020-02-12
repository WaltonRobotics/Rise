package frc.robot.commands.teleop;

import static frc.robot.OI.barfButton;
import static frc.robot.OI.intakeButton;
import static frc.robot.OI.intakeDownButton;
import static frc.robot.OI.intakeUpButton;
import static frc.robot.OI.shootButton;
import static frc.robot.Robot.intakeConveyor;
import static frc.robot.commands.teleop.IntakeConveyorCommand.State.INANDOUT;
import static frc.robot.commands.teleop.IntakeConveyorCommand.State.INTAKING;
import static frc.robot.commands.teleop.IntakeConveyorCommand.State.OFF;
import static frc.robot.commands.teleop.IntakeConveyorCommand.State.OUTTAKING;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeConveyorCommand extends CommandBase {

  private State currentState;

  private static final double INTAKE_POWER = 0.75;
  private static final double CENTERING_POWER = 0.75;
  private static final double CONVEYOR_FRONT_POWER = 0.75;
  private static final double CONVEYOR_BACK_POWER = 0.9;    // must be > CONVEYOR_FRONT_POWER

  public IntakeConveyorCommand() {
    addRequirements(intakeConveyor);
    currentState = OFF;

    intakeUpButton.whenPressed(() -> intakeConveyor.setIntakeToggle(false));
    intakeDownButton.whenPressed(() -> intakeConveyor.setIntakeToggle(true));
  }

  @Override
  public void execute() {
    currentState.execute();

//    if(intakeButton.get() && shootButton.get()) {
//      currentState = INANDOUT;
//    } else if(intakeButton.get()) {
//      currentState = INTAKING;
//    } else if(shootButton.get()) {
//      currentState = OUTTAKING;
//    } else {
//      currentState = OFF;
//    }

  }

  public enum State {
    OFF {
      @Override
      public void execute() {
        intakeConveyor.setIntakeMotorOutput(0);
        intakeConveyor.setCenteringMotorsOutput(0);
        intakeConveyor.setConveyorFrontMotorOutput(0);
        intakeConveyor.setConveyorBackMotorOutput(0);
      }
    }, INTAKING {
      @Override
      public void execute() {
        intakeConveyor.setIntakeMotorOutput(INTAKE_POWER);
        intakeConveyor.setCenteringMotorsOutput(CENTERING_POWER);
        intakeConveyor.setConveyorFrontMotorOutput(CONVEYOR_FRONT_POWER);
      }
    }, OUTTAKING {
      @Override
      public void execute() {
        intakeConveyor.setConveyorFrontMotorOutput(CONVEYOR_FRONT_POWER);
        intakeConveyor.setConveyorBackMotorOutput(CONVEYOR_BACK_POWER);
      }
    }, INANDOUT {
      @Override
      public void execute() {
        intakeConveyor.setIntakeMotorOutput(INTAKE_POWER);
        intakeConveyor.setCenteringMotorsOutput(CENTERING_POWER);
        intakeConveyor.setConveyorFrontMotorOutput(CONVEYOR_FRONT_POWER);
        intakeConveyor.setConveyorBackMotorOutput(CONVEYOR_BACK_POWER);
      }
    };

    public abstract void execute();
  }
}
