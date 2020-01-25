package frc.robot;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;

import java.util.Arrays;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class Paths {

    public static class ShootAndTrenchPickup {

        public static Pose2d shootingLine = new Pose2d(Units.feetToMeters(9.268), Units.feetToMeters(19.566), Rotation2d.fromDegrees(0.0));
        public static Pose2d trenchPickUp = new Pose2d(Units.feetToMeters(19.133), Units.feetToMeters(24.602), Rotation2d.fromDegrees(0.0));

        public static Trajectory generateToTrenchPickup() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(13), Units.feetToMeters(7));
            config.addConstraint(new DifferentialDriveKinematicsConstraint(drivetrain.getDriveKinematics(), Units.feetToMeters(13)));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(shootingLine, trenchPickUp),
                    config
            );
        }

        public static Trajectory generateBackupToShoot() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(13), Units.feetToMeters(7));
            config.addConstraint(new DifferentialDriveKinematicsConstraint(drivetrain.getDriveKinematics(), Units.feetToMeters(13)));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());
            config.setReversed(true);

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(trenchPickUp, shootingLine),
                    config
            );
        }

    }

}
