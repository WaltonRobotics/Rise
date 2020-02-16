package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.SPINNER_ID;
import static frc.robot.Constants.PneumaticIDs.SPINNER_TOGGLE_ID;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Solenoid;
import frc.utils.ColorSensorHelper;
import java.util.ArrayList;
import java.util.function.Supplier;

public class Spinner extends WaltSubsystem {

  private final TalonSRX spinnerMotor = new TalonSRX(SPINNER_ID);
  private final Solenoid spinnerToggle = new Solenoid(SPINNER_TOGGLE_ID);

  private final ColorSensorV3 colorSensor = new ColorSensorV3(Port.kOnboard);
  private final ColorSensorHelper colorSensorHelper = new ColorSensorHelper(colorSensor);

  public Spinner() {

  }

  public ColorSensorHelper getColorSensorHelper() {
    return colorSensorHelper;
  }

  public void setSpeed(double speed) {
    spinnerMotor.set(ControlMode.Velocity, speed);
  }

  public String getColor() {
    return colorSensorHelper.getColorMatch().name();
  }

  public int getEncoderValue() {
    return spinnerMotor.getSelectedSensorPosition();
  }

  @Override
  public Supplier<ArrayList<String>> getPitCheckFunction() {
    return () -> {
      ArrayList<String> failures = new ArrayList<>();

      if(spinnerMotor.getLastError() != ErrorCode.OK) failures.add("Spinner Motor Controller");

      return failures;
    };
  }

  @Override
  public void sendToNT() {

  }
}
