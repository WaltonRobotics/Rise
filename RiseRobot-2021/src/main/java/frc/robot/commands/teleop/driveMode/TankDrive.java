package frc.robot.commands.teleop.driveMode;

import static frc.robot.Robot.drivetrain;

public class TankDrive extends DriveMode {

    @Override
    public void feed() {
        double leftOutput = applyResponseFunction(applyDeadband(getLeftJoystickY()));
        double rightOutput = applyResponseFunction(applyDeadband(getRightJoystickY()));

        drivetrain.setDutyCycles(leftOutput, rightOutput);
    }

}
