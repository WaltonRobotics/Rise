package frc.robot.utils;

import edu.wpi.first.wpilibj.DigitalInput;

import static edu.wpi.first.wpilibj.Timer.getFPGATimestamp;

/**
 * The {@code IRSensor} class manages an IRSensor that has a flickering problem.
 */
public class IRSensor extends DigitalInput {

    private static final double DELAY_TIME = 0.1;
    private boolean sensorGetter;
    private EnhancedBoolean sensorDelay;
    private double sensorDelayStartTime;

    public IRSensor(int channel) {
        super(channel);

//    sensorGetter = new EnhancedBoolean();
        sensorDelay = new EnhancedBoolean();
        sensorDelayStartTime = -1;

//    // TODO The timing of this may not actually work
//    CommandScheduler.getInstance().schedule(new Command() {
//      @Override
//      public Set<Subsystem> getRequirements() {
//        return null;
//      }
//
//      @Override
//      public void execute() {
//        update();
//      }
//    });
    }

    public void update() {
        sensorDelay.set(super.get());
        if (sensorDelay.hasChanged()) {
            sensorDelayStartTime = getFPGATimestamp();
        }
        if (sensorDelayStartTime != -1 &&
                getFPGATimestamp() - sensorDelayStartTime > DELAY_TIME) {
            sensorGetter = sensorDelay.get();
            sensorDelayStartTime = -1;
        }
    }

    @Override
    public boolean get() {
        return sensorGetter;
    }


}
