package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.spinner;

public class ToColor extends CommandBase{
    private int stepsPerSegment;
    private double stepsNeeded;
    private char targetColor;
    private int FMSColorValue;
    public ToColor(int stepsPerSegment){
        addRequirements(spinner);
        this.stepsPerSegment = stepsPerSegment;
    }
    @Override
    public void initialize() {
        targetColor = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
        switch (targetColor){
            case 'R':
                FMSColorValue = 0;
                break;
            case 'G':
                FMSColorValue = 1;
                break;
            case 'B':
                FMSColorValue = 2;
                break;
            case 'Y':
                FMSColorValue = 3;
                break;
        }
        stepsNeeded = (2.5 + (FMSColorValue - spinner.getColorSensorHelper().getColorMatch().getValue())) * stepsPerSegment;
    }

    @Override
    public void execute() {
    }

    @Override
    public void end(boolean interrupted) {
        spinner.setSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return spinner.getEncoderValue() >= stepsNeeded;
    }
}
