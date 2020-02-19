package frc.robot.commands.teleop;

import static edu.wpi.first.wpilibj.Timer.getFPGATimestamp;
import static frc.robot.OI.intakeButton;
import static frc.robot.OI.intakeDownButton;
import static frc.robot.OI.intakeUpButton;
import static frc.robot.Robot.intakeConveyor;
import static frc.robot.Robot.turretShooter;
import static frc.robot.subsystems.IntakeConveyor.CENTERING_POWER;
import static frc.robot.subsystems.IntakeConveyor.CONVEYOR_POWER;
import static frc.robot.subsystems.IntakeConveyor.INTAKE_POWER;
import static frc.robot.subsystems.IntakeConveyor.PULSE_POWER;
import static frc.robot.subsystems.IntakeConveyor.PULSE_TIME;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeConveyorCommand extends CommandBase {

  private static double pulseStart;
  private State currentState;

  public IntakeConveyorCommand() {
    addRequirements(intakeConveyor);
    currentState = State.OFF;
    pulseStart = -1;

    intakeUpButton.whenPressed(() -> intakeConveyor.setIntakeToggle(false));
    intakeDownButton.whenPressed(() -> intakeConveyor.setIntakeToggle(true));
  }

  @Override
  public void execute() {
    currentState = currentState.execute();
    SmartDashboard.putString("Intake Conveyor State", currentState.name());
  }

  private static boolean shouldPulse() {
    return (pulseStart != -1 && getFPGATimestamp() - pulseStart < PULSE_TIME) ||
        intakeConveyor.canPulse();
  }

  private enum State {
    OFF {
      @Override
      public State execute() {
        pulseStart = -1;
        intakeConveyor.setIntakeMotorOutput(0);
        intakeConveyor.setCenteringMotorsOutput(0);
        intakeConveyor.setFrontConveyorMotorOutput(0);
        intakeConveyor.setBackConveyorMotorOutput(0);

        return determineState();
      }
    }, INTAKING {
      @Override
      public State execute() {
        pulseStart = -1;
        intakeConveyor.setIntakeMotorOutput(INTAKE_POWER);
        intakeConveyor.setCenteringMotorsOutput(CENTERING_POWER);
        intakeConveyor.setFrontConveyorMotorOutput(CONVEYOR_POWER);

        return determineState();
      }
    }, OUTTAKING {
      @Override
      public State execute() {
        pulseStart = -1;
        intakeConveyor.setFrontConveyorMotorOutput(CONVEYOR_POWER);
        intakeConveyor.setBackConveyorMotorOutput(CONVEYOR_POWER);

        return determineState();
      }
    }, IN_AND_OUT {
      @Override
      public State execute() {
        pulseStart = -1;
        intakeConveyor.setIntakeMotorOutput(INTAKE_POWER);
        intakeConveyor.setCenteringMotorsOutput(CENTERING_POWER);
        intakeConveyor.setFrontConveyorMotorOutput(CONVEYOR_POWER);
        intakeConveyor.setBackConveyorMotorOutput(CONVEYOR_POWER);

        return determineState();
      }
    }, PULSING {
      @Override
      public State execute() {
        // Start timer, if necessary
        if (pulseStart == -1) {
          pulseStart = getFPGATimestamp();
        }

        // Pulse
        intakeConveyor.setBackConveyorMotorOutput(PULSE_POWER);

        return determineState();
      }
    }, IN_AND_PULSING {
      @Override
      public State execute() {
        // Start timer, if necessary
        if (pulseStart == -1) {
          pulseStart = getFPGATimestamp();
        }

        // Pulse
        intakeConveyor.setIntakeMotorOutput(INTAKE_POWER);
        intakeConveyor.setCenteringMotorsOutput(CENTERING_POWER);
        intakeConveyor.setFrontConveyorMotorOutput(CONVEYOR_POWER);
        intakeConveyor.setBackConveyorMotorOutput(PULSE_POWER);

        return determineState();
      }
    };

    protected State determineState() {
      if (intakeButton.get() && turretShooter.isReadyToShoot) {
        return IN_AND_OUT;
      }
      if (intakeButton.get()) {
        if (shouldPulse()) {
          return IN_AND_PULSING;
        }
        return INTAKING;
      }
      if (turretShooter.isReadyToShoot) {
        return OUTTAKING;
      }
      if (shouldPulse()) {
        return PULSING;
      }
      return OFF;
    }

    public abstract State execute();
  }
}
