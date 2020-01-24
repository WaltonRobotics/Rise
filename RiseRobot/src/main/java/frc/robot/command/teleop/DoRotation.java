package frc.robot.command.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.spinner;

public class DoRotation extends CommandBase {
    private int transitionCount;
    private String expectedColor;
    private final int TRANSITION_LIMIT = 30;

    public DoRotation(){
        addRequirements(spinner);
    }

    @Override
    public void initialize() {
        if(spinner.getColor().equals("NONE")){
            // light on robot
        } else {
            spinner.setSpeed(1);
            transitionCount = 0;
            expectedColor = spinner.getExpectedColor();
        }
    }

    @Override
    public void execute() {
        if(spinner.getColor().equals("NONE")){
            end(true);
        }
        if(spinner.getColor().equals(expectedColor)){
            transitionCount++;
            expectedColor = spinner.getExpectedColor();
        }
    }

    @Override
    public void end(boolean interrupted) {
        spinner.setSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return transitionCount >= TRANSITION_LIMIT;
    }
}
