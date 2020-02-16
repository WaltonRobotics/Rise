package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.CENTERING_ID;
import static frc.robot.Constants.CANBusIDs.CONVEYOR_BACK_ID;
import static frc.robot.Constants.CANBusIDs.CONVEYOR_FRONT_ID;
import static frc.robot.Constants.CANBusIDs.INTAKE_ID;
import static frc.robot.Constants.DioIDs.BOTTOM_CONVEYOR_SENSOR_ID;
import static frc.robot.Constants.DioIDs.TOP_CONVEYOR_SENSOR_ID;
import static frc.robot.Constants.PneumaticIDs.INTAKE_TOGGLE_ID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeConveyor extends SubsystemBase {


  private final VictorSPX intakeMotor = new VictorSPX(INTAKE_ID);
  private final VictorSPX centeringMotors = new VictorSPX(CENTERING_ID);  // May end up being PWM
  private final VictorSPX conveyorFrontMotor = new VictorSPX(CONVEYOR_FRONT_ID);
  private final VictorSPX conveyorBackMotor = new VictorSPX(CONVEYOR_BACK_ID);

  private final Solenoid intakeToggle = new Solenoid(INTAKE_TOGGLE_ID);

  private final DigitalInput bottomConveyorSensor = new DigitalInput(BOTTOM_CONVEYOR_SENSOR_ID);
  private final DigitalInput topConveyorSensor = new DigitalInput(TOP_CONVEYOR_SENSOR_ID);


  public IntakeConveyor() {
    intakeMotor.setInverted(false);
  }

  public void setIntakeToggle(boolean state) {
    intakeToggle.set(state);
  }

  public void setIntakeMotorOutput(double output) {
    intakeMotor.set(ControlMode.PercentOutput, output);
  }

  public void setCenteringMotorsOutput(double output) {
    centeringMotors.set(ControlMode.PercentOutput, output);
  }

  public void setConveyorFrontMotorOutput(double output) {
    conveyorFrontMotor.set(ControlMode.PercentOutput, output);
  }

  public void setConveyorBackMotorOutput(double output) {
    conveyorBackMotor.set(ControlMode.PercentOutput, output);
  }

  public boolean canPulse() {
    return bottomConveyorSensor.get() && !bottomConveyorSensor.get();
  }

}
