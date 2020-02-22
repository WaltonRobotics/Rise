package frc.robot.subsystems;

import static edu.wpi.first.wpilibj.Timer.getFPGATimestamp;
import static frc.robot.Constants.CANBusIDs.CONVEYOR_BACK_ID;
import static frc.robot.Constants.CANBusIDs.CONVEYOR_FRONT_ID;
import static frc.robot.Constants.CANBusIDs.INTAKE_ID;
import static frc.robot.Constants.DioIDs.BACK_CONVEYOR_SENSOR_ID;
import static frc.robot.Constants.DioIDs.FRONT_CONVEYOR_SENSOR_ID;
import static frc.robot.Constants.PneumaticIDs.INTAKE_TOGGLE_ID;
import static frc.robot.OI.*;
import static frc.robot.OI.intakeDownButton;
import static frc.robot.Robot.intakeConveyor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.EnhancedBoolean;

public class IntakeConveyor extends SubsystemBase {

  public static final double INTAKE_POWER = 0.75;
//  public static final double CENTERING_POWER = 0.75;
  public static final double FRONT_CONVEYOR_POWER = 1;
  public static final double BACK_CONVEYOR_POWER = 1;
  public static final double PULSE_TIME = 0.32; // seconds  TODO adjust
  public static final double FRONT_SENSOR_GET_TIME_LIMIT = PULSE_TIME;

  private final VictorSPX intakeMotor = new VictorSPX(INTAKE_ID);
//  private final VictorSPX centeringMotors = new VictorSPX(CENTERING_ID);  // May end up being PWM
  private final VictorSPX frontConveyorMotor = new VictorSPX(CONVEYOR_FRONT_ID);
  private final VictorSPX backConveyorMotor = new VictorSPX(CONVEYOR_BACK_ID);

  private final Solenoid intakeToggle = new Solenoid(INTAKE_TOGGLE_ID);

  private final DigitalInput frontConveyorSensor = new DigitalInput(FRONT_CONVEYOR_SENSOR_ID);
  private final DigitalInput backConveyorSensor = new DigitalInput(BACK_CONVEYOR_SENSOR_ID);
  private int ballCount;
  private EnhancedBoolean frontSensorGet;
  private double frontSensorGetStart;
  private boolean isInFront;


  public IntakeConveyor() {
    intakeMotor.setInverted(false);
//    centeringMotors.setInverted(true);
    frontConveyorMotor.setInverted(true);
    ballCount = 0;
    isInFront = true;
    frontSensorGet = new EnhancedBoolean();

    overrideFrontConveyorButton.whenPressed(() -> setFrontConveyorMotorOutput(FRONT_CONVEYOR_POWER));
    overrideBackConveyorButton.whenPressed(() -> setBackConveyorMotorOutput(BACK_CONVEYOR_POWER));
//    overrideIntakeButton.whenPressed(() -> setIntakeMotorOutput(INTAKE_POWER));
//    overrideCenteringButton.whenPressed(() -> setCenteringMotorsOutput(CENTERING_POWER));

    intakeUpButton.whenPressed(() -> intakeConveyor.setIntakeToggle(false));
    intakeDownButton.whenPressed(() -> intakeConveyor.setIntakeToggle(true));
    intakeButton.whenPressed(() -> intakeConveyor.setIntakeToggle(true));
    intakeButton.whenReleased(() -> intakeConveyor.setIntakeToggle(false));
  }

  @Override
  public void periodic() {

    frontSensorGet.set(!frontConveyorSensor.get());
    if(frontSensorGet.isRisingEdge()) {
//      ballCount++;
      frontSensorGetStart = getFPGATimestamp();
      isInFront = false;
    }

    if(isInFront) {
      frontSensorGetStart = getFPGATimestamp();
    } else {
      if(getFPGATimestamp() - frontSensorGetStart > FRONT_SENSOR_GET_TIME_LIMIT) {
//        ballCount++;
        isInFront = true;
      }
    }

//    setIntakeMotorOutput(INTAKE_POWER);
//    setBackConveyorMotorOutput(BACK_CONVEYOR_POWER);
//    setFrontConveyorMotorOutput(FRONT_CONVEYOR_POWER);

    SmartDashboard.putBoolean("Front Conveyor Sensor", frontSensorGet.get());
    SmartDashboard.putNumber("Ball Count", ballCount);
  }

  public void setIntakeToggle(boolean state) {
    intakeToggle.set(state);
  }

  public void setIntakeMotorOutput(double output) {
    intakeMotor.set(ControlMode.PercentOutput, output);
  }

//  public void setCenteringMotorsOutput(double output) {
//    centeringMotors.set(ControlMode.PercentOutput, output);
//  }

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
    return ballCount <= 3 &&
        frontSensorGet.get();
  }

}
