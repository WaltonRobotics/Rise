package frc.robot.commands.teleop.responseFunction;

public class LinearResponse implements ResponseFunction {

    @Override
    public double getOutput(double input) {
        return input;
    }

}
