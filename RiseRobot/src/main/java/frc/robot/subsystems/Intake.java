package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.INTAKE_ID;
import static frc.robot.Constants.PneumaticIDs.INTAKE_TOGGLE_ID;
import static frc.robot.OI.intakeDownButton;
import static frc.robot.OI.intakeUpButton;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private final VictorSPX intakeMotor = new VictorSPX(INTAKE_ID);
  // Will need another controller for centering motors

  private final Solenoid intakeToggle = new Solenoid(INTAKE_TOGGLE_ID);

  public Intake() {
    intakeMotor.setInverted(false);

    intakeUpButton.whenPressed(() -> setIntakeToggle(false));
    intakeDownButton.whenPressed(() -> setIntakeToggle(true));
  }

  public void setIntakeToggle(boolean state) {
    intakeToggle.set(state);
  }

  public void setMotorPercentOutput(double percentOutput) {
    intakeMotor.set(ControlMode.PercentOutput, percentOutput);
  }

}
