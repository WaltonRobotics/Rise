package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.CONVEYOR_ID;
import static frc.robot.Constants.CANBusIDs.INTAKE_ID;
import static frc.robot.Constants.PneumaticIDs.CONVEYOR_STOP_ID;
import static frc.robot.Constants.PneumaticIDs.INTAKE_TOGGLE_ID;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeConveyor extends SubsystemBase {

  private VictorSPX intakeMotor = new VictorSPX(INTAKE_ID);
  private VictorSPX conveyorMotor = new VictorSPX(CONVEYOR_ID);

  private Solenoid intakeToggle = new Solenoid(INTAKE_TOGGLE_ID);
  private Solenoid conveyorStop = new Solenoid(CONVEYOR_STOP_ID);

  public IntakeConveyor() {

  }

}
