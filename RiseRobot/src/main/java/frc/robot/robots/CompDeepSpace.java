package frc.robot.robots;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class CompDeepSpace {

  private SpeedControllerGroup leftTransmission;
  private SpeedControllerGroup rightTransmission;

  public CompDeepSpace() {

    leftTransmission = new SpeedControllerGroup(new WPI_TalonFX(), new WPI_TalonFX());
    rightTransmission = new SpeedControllerGroup(new WPI_TalonFX(), new WPI_TalonFX());

  }
}
