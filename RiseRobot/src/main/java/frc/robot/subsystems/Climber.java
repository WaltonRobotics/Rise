package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.CLIMBER_ID;
import static frc.robot.Constants.PneumaticIDs.CLIMBER_LOCK_ID;
import static frc.robot.Constants.PneumaticIDs.CLIMBER_TOGGLE_ID;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.CANBusIDs.CLIMBER_ID;
import static frc.robot.Constants.PneumaticIDs.CLIMBER_LOCK_ID;
import static frc.robot.Constants.PneumaticIDs.CLIMBER_TOGGLE_ID;

public class Climber extends SubsystemBase {

  private final TalonFX climberMotor = new TalonFX(CLIMBER_ID);

  private final Solenoid climberToggle = new Solenoid(CLIMBER_TOGGLE_ID);
  private final Solenoid climberLock = new Solenoid(CLIMBER_LOCK_ID);

  public Climber() {
    climberMotor.selectProfileSlot(0, 0);
    climberMotor.setNeutralMode(NeutralMode.Coast);
    climberMotor.setInverted(false);
  }

  public void setClimberToggle(boolean value) {
    climberToggle.set(value);
  }

  public void toggleClimberDeploy() {
    climberToggle.set(!climberToggle.get());
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
