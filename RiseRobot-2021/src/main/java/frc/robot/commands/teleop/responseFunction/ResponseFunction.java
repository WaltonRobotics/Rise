package frc.robot.commands.teleop.responseFunction;

@FunctionalInterface
public interface ResponseFunction {

    double getOutput(double input);

}
