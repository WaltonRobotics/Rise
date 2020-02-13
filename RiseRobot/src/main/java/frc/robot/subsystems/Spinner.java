package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.utils.ColorSensorHelper;

import static frc.robot.Constants.CANBusIDs.SPINNER_ID;
import static frc.robot.Constants.PneumaticIDs.SPINNER_TOGGLE_ID;

public class Spinner extends SubsystemBase {

    private final TalonSRX spinnerMotor = new TalonSRX(SPINNER_ID);
    private final Solenoid spinnerToggle = new Solenoid(SPINNER_TOGGLE_ID);

    private final ColorSensorV3 colorSensor = new ColorSensorV3(Port.kOnboard);
    private final ColorSensorHelper colorSensorHelper = new ColorSensorHelper(colorSensor);

    public Spinner() {

    }

    public ColorSensorHelper getColorSensorHelper() {
        return colorSensorHelper;
    }

    public void setSpeed(double speed){
        spinnerMotor.set(ControlMode.Velocity, speed);
    }

    public String getColor(){
        return colorSensorHelper.getColorMatch().name();
    }

    public int getEncoderValue(){
        return spinnerMotor.getSelectedSensorPosition();
    }
}
