package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.SPINNER_ID;
import static frc.robot.Constants.PneumaticIDs.SPINNER_TOGGLE_ID;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Spinner extends SubsystemBase {

    private final TalonSRX spinnerMotor = new TalonSRX(SPINNER_ID);

    private final Solenoid spinnerToggle = new Solenoid(SPINNER_TOGGLE_ID);

    public Spinner() {

    }
}
