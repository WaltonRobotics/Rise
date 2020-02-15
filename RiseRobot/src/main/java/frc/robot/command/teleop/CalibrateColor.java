package frc.robot.command.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.command.teleop.routines.PositionControl;

import static frc.robot.Robot.spinner;

public class CalibrateColor extends CommandBase{
    private int stepsPerSegment;
    private String currentColor;
    private int initialPulses;
    private int transitionCount;

    public CalibrateColor(){
        addRequirements(spinner);
    }
    @Override
    public void initialize() {
        if(spinner.getColor().equals("NONE")){
            //bepbep
        }
        else{
            currentColor = spinner.getColor();
            spinner.setSpeed(1);
        }
    }

    @Override
    public void execute() {
        if (!currentColor.equals(spinner.getColor())){
            initialPulses = spinner.getEncoderValue();
            currentColor = spinner.getColor();
            transitionCount++;
        }
    }

    @Override
    public void end(boolean interrupted) {
        stepsPerSegment = spinner.getEncoderValue() - initialPulses;
    }

    public int getStepsPerSegment() {
        return stepsPerSegment;
    }

    @Override
    public boolean isFinished() {
        return transitionCount >= 2;
    }
}
