package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.CANBusIDs.CONVEYOR_ID;
import static frc.robot.Constants.CANBusIDs.INTAKE_ID;
import static frc.robot.Constants.PneumaticIDs.CONVEYOR_STOP_ID;
import static frc.robot.Constants.PneumaticIDs.INTAKE_TOGGLE_ID;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private final VictorSPX intakeMotor = new VictorSPX(INTAKE_ID);
  // Will need another controller for centering motors

  private final Solenoid intakeToggle = new Solenoid(INTAKE_TOGGLE_ID);

  public Intake() {

  }

}
