package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.CANBusIDs.*;

public class TurretShooter extends SubsystemBase {

    private final TalonFX flywheelMaster = new TalonFX(SHOOTER_FLYWHEEL_MASTER_ID);
    private final TalonFX flywheelSlave = new TalonFX(SHOOTER_FLYWHEEL_SLAVE_ID);

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
