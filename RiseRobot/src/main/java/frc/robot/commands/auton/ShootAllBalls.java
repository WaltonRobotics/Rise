package frc.robot.commands.auton;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.utils.LimelightHelper;

import static frc.robot.Constants.Shooter.shooterTolerance;
import static frc.robot.Robot.intakeConveyor;
import static frc.robot.Robot.turretShooter;
import static frc.robot.subsystems.IntakeConveyor.BACK_CONVEYOR_POWER;
import static frc.robot.subsystems.IntakeConveyor.FRONT_CONVEYOR_POWER;


public class ShootAllBalls extends CommandBase {

    private Timer timer;
    private double totalTime;
    private int rpm;

    public ShootAllBalls(double time) {
        timer = new Timer();
        totalTime = time;
        rpm = -1;
    }

    public ShootAllBalls(double time, int rpmFlyWheel) {
        timer = new Timer();
        totalTime = time;
        rpm = rpmFlyWheel;
    }

    @Override
    public void initialize() {
        timer.start();

        if(rpm != -1) {
            rpm = (int)turretShooter.estimateTargetSpeed(LimelightHelper.getDistanceMeters());
        }
    }

    @Override
    public void execute() {
        turretShooter.setFlywheelOutput(TalonFXControlMode.Velocity, rpm);

        if(timer.get() > 0.35) {
            intakeConveyor.setFrontConveyorMotorOutput(FRONT_CONVEYOR_POWER);
            intakeConveyor.setBackConveyorMotorOutput(BACK_CONVEYOR_POWER);
        }
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return timer.get() > totalTime;
    }
}
