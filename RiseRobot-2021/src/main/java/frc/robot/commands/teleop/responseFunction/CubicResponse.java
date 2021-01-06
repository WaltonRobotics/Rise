package frc.robot.commands.teleop.responseFunction;

public class CubicResponse implements ResponseFunction {

    @Override
    public double getOutput(double input) {
        return Math.cbrt(input);
    }

}
