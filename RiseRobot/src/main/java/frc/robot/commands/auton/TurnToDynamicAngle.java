package frc.robot.commands.auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import java.util.function.DoubleSupplier;

public class TurnToDynamicAngle extends TurnToAngle {

  private DoubleSupplier angleSupplier;

  public TurnToDynamicAngle(DoubleSupplier angleSupplier) {
    super(0);
    this.angleSupplier = angleSupplier;
  }

  @Override
  public void initialize() {
    targetAngle = angleSupplier.getAsDouble();
    super.initialize();
  }
}
