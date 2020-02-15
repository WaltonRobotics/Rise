package frc.robot.subsystems;

import static frc.robot.Constants.CANBusIDs.CONVEYOR_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_MASTER_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_FLYWHEEL_SLAVE_ID;
import static frc.robot.Constants.CANBusIDs.SHOOTER_TURRET_ID;
import static frc.robot.Constants.PneumaticIDs.CONVEYOR_STOP_ID;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class TurretShooter extends SubsystemBase {

    private final TalonFX flywheelMaster = new TalonFX(SHOOTER_FLYWHEEL_MASTER_ID);
    private final TalonFX flywheelSlave = new TalonFX(SHOOTER_FLYWHEEL_SLAVE_ID);

  private final VictorSPX conveyorMotor = new VictorSPX(CONVEYOR_ID);
  private final Solenoid conveyorStop = new Solenoid(CONVEYOR_STOP_ID);

    private final TalonSRX turretMotor = new TalonSRX(SHOOTER_TURRET_ID);

    private double targetSpeed = 0;

    public TurretShooter() {
        flywheelMaster.selectProfileSlot(0, 0);
        flywheelMaster.setInverted(true);

        flywheelSlave.follow(flywheelMaster);
    }

    public void setTargetSpeed(double targetSpeed) {
        this.targetSpeed = targetSpeed;
    }
}
