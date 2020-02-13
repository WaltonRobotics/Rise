package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.CLIMBER_ID;
import static frc.robot.Constants.PneumaticIDs.CLIMBER_LOCK_ID;
import static frc.robot.Constants.PneumaticIDs.CLIMBER_TOGGLE_FIRST_ID;
import static frc.robot.Constants.PneumaticIDs.CLIMBER_TOGGLE_SECOND_ID;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {

  private final TalonFX climberMotor = new TalonFX(CLIMBER_ID);

  private final Solenoid climberToggleFirst = new Solenoid(CLIMBER_TOGGLE_FIRST_ID);
  private final Solenoid climberToggleSecond = new Solenoid(CLIMBER_TOGGLE_SECOND_ID);
  private final Solenoid climberLock = new Solenoid(CLIMBER_LOCK_ID);

  public Climber() {
    climberMotor.selectProfileSlot(0, 0);
    climberMotor.setInverted(false);
  }

  public void setClimberToggleFirst(boolean on) {
    if (climberToggleFirst.get() != on) {
      climberToggleFirst.set(on);
    }
  }

  public void setClimberToggleSecond(boolean on) {
    if (climberToggleSecond.get() != on) {
      climberToggleSecond.set(on);
    }
  }

  public void setClimberLock(boolean on) {
    if (climberLock.get() != on) {
      climberLock.set(on);
    }
  }

  public void setClimberMotorOutput(TalonFXControlMode controlMode, double value) {
    climberMotor.set(controlMode, value);
  }

}
