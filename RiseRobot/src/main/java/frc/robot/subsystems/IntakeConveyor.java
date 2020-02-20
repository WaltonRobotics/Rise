package frc.robot.subsystems;

import static edu.wpi.first.wpilibj.Timer.getFPGATimestamp;
import static frc.robot.Constants.CANBusIDs.CENTERING_ID;
import static frc.robot.Constants.CANBusIDs.CONVEYOR_BACK_ID;
import static frc.robot.Constants.CANBusIDs.CONVEYOR_FRONT_ID;
import static frc.robot.Constants.CANBusIDs.INTAKE_ID;
import static frc.robot.Constants.DioIDs.BACK_CONVEYOR_SENSOR_ID;
import static frc.robot.Constants.DioIDs.FRONT_CONVEYOR_SENSOR_ID;
import static frc.robot.Constants.PneumaticIDs.INTAKE_TOGGLE_ID;
import static frc.robot.OI.overrideBackConveyorButton;
import static frc.robot.OI.overrideCenteringButton;
import static frc.robot.OI.overrideFrontConveyorButton;
import static frc.robot.OI.overrideIntakeButton;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeConveyor extends SubsystemBase {

  public static final double INTAKE_POWER = 0.75;
  public static final double CENTERING_POWER = 0.75;
  public static final double FRONT_CONVEYOR_POWER = 1;
  public static final double BACK_CONVEYOR_POWER = 0.75;
  public static final double PULSE_POWER = 0.75;  // TODO adjust
  public static final double PULSE_TIME = 0.5; // seconds  TODO adjust
  private static final double BALL_MOVE_TIME = 0.2; // TODO adjust

  private final VictorSPX intakeMotor = new VictorSPX(INTAKE_ID);
  private final VictorSPX centeringMotors = new VictorSPX(CENTERING_ID);  // May end up being PWM
  private final VictorSPX frontConveyorMotor = new VictorSPX(CONVEYOR_FRONT_ID);
  private final VictorSPX backConveyorMotor = new VictorSPX(CONVEYOR_BACK_ID);

  private final Solenoid intakeToggle = new Solenoid(INTAKE_TOGGLE_ID);

  private final DigitalInput frontConveyorSensor = new DigitalInput(FRONT_CONVEYOR_SENSOR_ID);
  private final DigitalInput backConveyorSensor = new DigitalInput(BACK_CONVEYOR_SENSOR_ID);
  private int ballCount;
  private double ballStartTimeFront;


  public IntakeConveyor() {
    intakeMotor.setInverted(false);
    centeringMotors.setInverted(true);
    frontConveyorMotor.setInverted(true);
    ballCount = 0;
    ballStartTimeFront = 0;

    overrideFrontConveyorButton.whenPressed(() -> setFrontConveyorMotorOutput(FRONT_CONVEYOR_POWER));
    overrideBackConveyorButton.whenPressed(() -> setBackConveyorMotorOutput(BACK_CONVEYOR_POWER));
    overrideIntakeButton.whenPressed(() -> setIntakeMotorOutput(INTAKE_POWER));
    overrideCenteringButton.whenPressed(() -> setBackConveyorMotorOutput(CENTERING_POWER));
  }

  @Override
  public void periodic() {
    if (!frontConveyorSensor.get()) {
      if (getFPGATimestamp() - ballStartTimeFront >= BALL_MOVE_TIME) {
        ballCount++;
      }
      ballStartTimeFront = getFPGATimestamp();
    }
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

  public void setFrontConveyorMotorOutput(double output) {
    frontConveyorMotor.set(ControlMode.PercentOutput, output);
  }

  public void setBackConveyorMotorOutput(double output) {
    backConveyorMotor.set(ControlMode.PercentOutput, output);
  }

  public void resetBallCount() {
    ballCount = 0;
  }

  public boolean canPulse() {
    return ballCount >= 3 && !backConveyorSensor.get() &&
        !overrideBackConveyorButton.get() && !overrideFrontConveyorButton.get();
  }

}
