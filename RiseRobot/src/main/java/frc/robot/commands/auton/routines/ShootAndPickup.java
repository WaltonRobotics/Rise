package frc.robot.commands.auton.routines;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Paths;
import frc.robot.commands.auton.RamseteTrackingCommand;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class ShootAndPickup extends SequentialCommandGroup {

    public ShootAndPickup() {
        addCommands(
                new InstantCommand(() -> System.out.println("Shooting three balls!")),
                new WaitCommand(2.0),
                new InstantCommand(() -> drivetrain.reset(Paths.ShootAndTrenchPickup.shootingLine)),
                new RamseteTrackingCommand(
                        Paths.ShootAndTrenchPickup.generateToTrenchPickup(),
                        drivetrain::getRobotPose,
                        drivetrain.getRamseteController(),
                        currentRobot.getDrivetrainFeedforward(),
                        drivetrain.getDriveKinematics(),
                        drivetrain::getSpeeds,
                        currentRobot.getLeftPIDController(),
                        currentRobot.getRightPIDController(),
                        drivetrain::setVoltages,
                        drivetrain),
                new RamseteTrackingCommand(
                        Paths.ShootAndTrenchPickup.generateBackupToShoot(),
                        drivetrain::getRobotPose,
                        drivetrain.getRamseteController(),
                        currentRobot.getDrivetrainFeedforward(),
                        drivetrain.getDriveKinematics(),
                        drivetrain::getSpeeds,
                        currentRobot.getLeftPIDController(),
                        currentRobot.getRightPIDController(),
                        drivetrain::setVoltages,
                        drivetrain)
        );
    }

}
