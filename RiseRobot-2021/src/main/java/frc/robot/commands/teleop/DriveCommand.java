package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.driveModeChooser;
import static frc.robot.Robot.drivetrain;

public class DriveCommand extends CommandBase {

    public DriveCommand() {
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        // (Joysticks inverted because limelight is facing backwards)

//        if(turnToTargetButton.get()) {
//            double steerCmd = LimelightHelper.getTX() * 0.05;
//            drivetrain.setArcadeSpeeds(0 , steerCmd);
//            System.out.println(steerCmd);
//        }
////
//        else {
//        }

        driveModeChooser.getSelected().feed();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
