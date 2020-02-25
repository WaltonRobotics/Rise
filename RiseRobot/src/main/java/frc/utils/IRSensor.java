package frc.utils;

import static edu.wpi.first.wpilibj.Timer.getFPGATimestamp;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.Set;

/**
 * The {@code IRSensor} class manages an IRSensor that has a flickering problem.
 */
public class IRSensor extends DigitalInput {

  private EnhancedBoolean sensorGetter;
  private EnhancedBoolean sensorDelay;
  private double sensorDelayStartTime;
  private static final double DELAY_TIME = 0.1;

  public IRSensor(int channel) {
    super(channel);

    sensorGetter = new EnhancedBoolean();
    sensorDelay = new EnhancedBoolean();
    sensorDelayStartTime = -1;

    // TODO The timing of this may not actually work
    CommandScheduler.getInstance().schedule(new Command() {
      @Override
      public Set<Subsystem> getRequirements() {
        return null;
      }

      @Override
      public void execute() {
        update();
      }
    });
  }

  public void update() {
    if(sensorGetter.isRisingEdge()) {
      sensorGetter.set(true);
    }
    if(sensorGetter.isFallingEdge()) {
      sensorGetter.set(false);
    }

    sensorDelay.set(!get());
    if(sensorDelay.hasChanged()) {
      sensorDelayStartTime = getFPGATimestamp();
    }
    if(sensorDelayStartTime != -1 &&
        getFPGATimestamp() - sensorDelayStartTime > DELAY_TIME) {
      sensorGetter.set(sensorDelay.get());
      sensorDelayStartTime = -1;
    }
  }

  public EnhancedBoolean getSensorGetter() {
    return sensorGetter;
  }
}
