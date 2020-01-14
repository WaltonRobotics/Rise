package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.OI.*;
import static frc.robot.Robot.drivetrain;

public class Drive extends CommandBase {

    private double deadBand = 0.1;

    public Drive() {
        addRequirements(drivetrain);
    }

    public double getLeftJoystickY() {
        return leftJoystick.getY() > deadBand ? -leftJoystick.getY() : 0;
    }

    public double getRightJoystickY() {
        return rightJoystick.getY() > deadBand ? -rightJoystick.getY() : 0;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {

        drivetrain.setSpeeds(getLeftJoystickY(), getRightJoystickY());

        if (shiftUpButton.get() && !drivetrain.isHighGear()) {
            drivetrain.shiftUp();
        } else if (shiftDownButton.get() && drivetrain.isHighGear()) {
            drivetrain.shiftDown();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
