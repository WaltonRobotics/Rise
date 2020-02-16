package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.CLIMBER_ID;
import static frc.robot.Constants.PneumaticIDs.CLIMBER_LOCK_ID;
import static frc.robot.Constants.PneumaticIDs.CLIMBER_TOGGLE_ID;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Solenoid;
import java.util.ArrayList;
import java.util.function.Supplier;

public class Climber extends WaltSubsystem {

  private final TalonFX climberMotor = new TalonFX(CLIMBER_ID);

  private final Solenoid climberToggle = new Solenoid(CLIMBER_TOGGLE_ID);
  private final Solenoid climberLock = new Solenoid(CLIMBER_LOCK_ID);

  public Climber() {
    climberMotor.selectProfileSlot(0, 0);
    climberMotor.setInverted(false);
  }

  public void setClimberToggle(boolean on) {
    if (climberToggle.get() != on) {
      climberToggle.set(on);
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

  @Override
  public Supplier<ArrayList<String>> getPitCheckFunction() {
    return () -> {
      ArrayList<String> failures = new ArrayList<>();

      if(climberMotor.getLastError() != ErrorCode.OK) {
        failures.add("Climber motor");
      }

      return failures;
    };
  }

  @Override
  public void sendToNT() {

  }
}
