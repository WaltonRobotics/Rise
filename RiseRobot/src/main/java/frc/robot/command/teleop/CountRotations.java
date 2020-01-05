package frc.robot.command.teleop;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.utils.EnhancedBoolean;

import static frc.robot.Robot.spinner;

public class CountRotations extends CommandBase {

    private static final int REQUIRED_NUM_ROTATIONS = 3;

    private Color initialColor;

    /**
     *     Since there are two of the same color on the wheel, we count the number of times the same color passed the
     *     sensor and then divide by two to get the number of rotations
     */

    private EnhancedBoolean sameColorStatus;

    private int numberOfSameReads;
    private int numberOfRotations;

    public CountRotations() {
        addRequirements(spinner);

        sameColorStatus = new EnhancedBoolean();
        numberOfSameReads = 0;
        numberOfRotations = 0;
    }

    @Override
    public void initialize() {
        initialColor = spinner.getColorSensorHelper().getColorMatch();
    }

    @Override
    public void execute() {
        sameColorStatus.set(spinner.getColorSensorHelper().getColorMatch() == initialColor);

        if (sameColorStatus.isFallingEdge()) {
            numberOfSameReads++;
            numberOfRotations = numberOfSameReads / 2;
        }

        // TODO: Spin the disk here (cw or ccw)
    }

    @Override
    public boolean isFinished() {
        return numberOfRotations >= REQUIRED_NUM_ROTATIONS;
    }

}
