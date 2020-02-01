package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import static edu.wpi.first.networktables.EntryListenerFlags.kNew;
import static edu.wpi.first.networktables.EntryListenerFlags.kUpdate;

public class TurretShooter extends SubsystemBase {
    TalonFX masterFlywheel;
//    TalonFX slaveFlywheel;
//    PIDController flywheel = new PIDController(0,0,0);
//    TalonSRX HoodMotor = new TalonSRX(0);
//    TalonSRX TurretMotor = new TalonSRX(0);

    NetworkTableInstance nti;
    NetworkTable pidTable;
    NetworkTableEntry targetSpeed;
    NetworkTableEntry encoderSpeed;
    NetworkTableEntry flywheelOutput;

    public TurretShooter() {
        nti = NetworkTableInstance.getDefault();
        pidTable = nti.getTable("Flywheel PID");
        encoderSpeed = pidTable.getEntry("Encoder Speed");
        targetSpeed = pidTable.getEntry("Flywheel Speed");
        flywheelOutput = pidTable.getEntry("Percent Output");
        masterFlywheel = new TalonFX(9);
        masterFlywheel.setInverted(true);
        masterFlywheel.selectProfileSlot(0, 0);
        sendToNT();

//        slaveFlywheel.follow(masterFlywheel);
    }

    @Override
    public void periodic() {
        setVelocity();
    }

    private void setVelocity(){
//        masterFlywheel.set(TalonFXControlMode.Current, flywheel.calculate(turretEncoder.getDistance()));
//        slaveFlywheel.set(TalonFXControlMode.Current, flywheel.calculate(turretEncoder.getDistance()));
        masterFlywheel.set(TalonFXControlMode.Velocity,
                targetSpeed.getDouble(0));
        encoderSpeed.setNumber(masterFlywheel.getSelectedSensorVelocity());
        flywheelOutput.setNumber(masterFlywheel.getMotorOutputPercent());
    }

    private void sendToNT() {
        NetworkTableEntry kF = pidTable.getEntry("Flywheel F");
        NetworkTableEntry kP = pidTable.getEntry("Flywheel P");
        NetworkTableEntry kI = pidTable.getEntry("Flywheel I");
        NetworkTableEntry kD = pidTable.getEntry("Flywheel D");
        NetworkTableEntry speed = pidTable.getEntry("Flywheel Speed");

        kF.setDefaultNumber(0.06399989);
        kP.setDefaultNumber(0.498689);
        kI.setDefaultNumber(0);
        kD.setDefaultNumber(0);
        speed.setDefaultNumber(0);

        kF.addListener(event -> masterFlywheel.config_kF(0, event.value.getDouble()), kNew | kUpdate);
        kP.addListener(event -> masterFlywheel.config_kP(0, event.value.getDouble()), kNew | kUpdate);
        kI.addListener(event -> masterFlywheel.config_kI(0, event.value.getDouble()), kNew | kUpdate);
        kD.addListener(event -> masterFlywheel.config_kD(0, event.value.getDouble()), kNew | kUpdate);
    }
}
