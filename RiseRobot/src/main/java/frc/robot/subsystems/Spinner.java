package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.ColorSensorHelper;

public class Spinner extends SubsystemBase {
  private Talon genericMotor = new Talon(0);
  private Encoder genericEncoder = new Encoder(0,0);
  private ColorSensorHelper colorSensorHelper = new ColorSensorHelper();

  public Spinner() {
    SmartDashboard.putNumber("IRSensorLimit", 200);
  }

  @Override
  public void periodic() {
    SmartDashboard.putString("Color", getColor());
    SmartDashboard.putNumber("IR Sensor", getColorSensorHelper().getProximity());
  }

  public ColorSensorHelper getColorSensorHelper() {
    return colorSensorHelper;
  }

  public void setSpeed(double speed){
    genericMotor.setSpeed(speed);
  }

  public String getColor(){
    return colorSensorHelper.getColorMatch().name();
  }

  public int getEncoderValue(){
    return genericEncoder.get();
  }

  public String getExpectedColor(){
    switch (getColor()){
      case "RED":
        return "GREEN";
      case "GREEN":
        return "BLUE";
      case "BLUE":
        return "YELLOW";
      case "YELLOW":
        return "RED";
    }
    return "NONE";
  }
}
