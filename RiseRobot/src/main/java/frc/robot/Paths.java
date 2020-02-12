package frc.robot;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;

import java.util.Arrays;

import static frc.robot.Robot.currentRobot;
import static frc.robot.Robot.drivetrain;

public class Paths {

    public static class TestTrajectories {

        public static Trajectory testForward = generateTestForward();

        public static Trajectory generateTestForward() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(11), Units.feetToMeters(5));
            // config.addConstraint(new CentripetalAccelerationConstraint(Units.feetToMeters(4)));
            config.setKinematics(drivetrain.getDriveKinematics());

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(0), Units.feetToMeters(0), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(15), Units.feetToMeters(0), Rotation2d.fromDegrees(0.0))),
                    config
            );
        }
    }

    public static class CrossBaseline {

        public static Trajectory forwards = generateForwards();
        public static Trajectory backwards = generateBackwards();

        public static Trajectory generateForwards() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(4), Units.feetToMeters(3));
            config.addConstraint(new DifferentialDriveKinematicsConstraint(drivetrain.getDriveKinematics(), Units.feetToMeters(17)));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(10.808), Units.feetToMeters(13.0), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(14.0), Units.feetToMeters(13.0), Rotation2d.fromDegrees(0.0))),
                    config
            );
        }

        public static Trajectory generateBackwards() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(4), Units.feetToMeters(3));
            config.addConstraint(new DifferentialDriveKinematicsConstraint(drivetrain.getDriveKinematics(), Units.feetToMeters(17)));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());
            config.setReversed(true);

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(14.0), Units.feetToMeters(13.0), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(10.808), Units.feetToMeters(13.0), Rotation2d.fromDegrees(0.0))),
                    config
            );
        }

    }

    public static class Two {

        public static Trajectory trenchPickup = generateTwoTrenchPickup();
        public static Trajectory intakeThreeBalls = generateTwoIntakeThreeBalls();
        public static Trajectory trenchBackup = generateTwoTrenchBackup();
        public static Trajectory generatorPickupTwoBalls = generateTwoGeneratorPickupTwoBalls();
        public static Trajectory generatorBackupToShoot = generateTwoGeneratorBackupToShoot();

        public static Trajectory generateTwoTrenchPickup() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(10), Units.feetToMeters(8));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());
            config.setStartVelocity(0);

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(10.609), Units.feetToMeters(19.13), Rotation2d.fromDegrees(0)),
                            new Pose2d(Units.feetToMeters(18.726), Units.feetToMeters(24.892), Rotation2d.fromDegrees(0))),
                    config
            );
        }

        public static Trajectory generateTwoIntakeThreeBalls() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(7.5), Units.feetToMeters(6));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(18.726), Units.feetToMeters(24.892), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(25.678), Units.feetToMeters(24.892), Rotation2d.fromDegrees(0.0))),
                    config
            );
        }

        public static Trajectory generateTwoTrenchBackup() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(10), Units.feetToMeters(6));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());
            config.setReversed(true);

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(25.678), Units.feetToMeters(24.892), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(18.965), Units.feetToMeters(21.707), Rotation2d.fromDegrees(-75.0))),
                    config
            );
        }

        public static Trajectory generateTwoGeneratorPickupTwoBalls() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(10), Units.feetToMeters(6));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(18.965), Units.feetToMeters(21.707), Rotation2d.fromDegrees(-75.0)),
                            new Pose2d(Units.feetToMeters(19.579), Units.feetToMeters(19.46), Rotation2d.fromDegrees(-75.0))),
                    config
            );
        }

        public static Trajectory generateTwoGeneratorBackupToShoot() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(10), Units.feetToMeters(6));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());
            config.setReversed(true);

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(19.579), Units.feetToMeters(19.46), Rotation2d.fromDegrees(-75.0)),
                            new Pose2d(Units.feetToMeters(22.104), Units.feetToMeters(20.889), Rotation2d.fromDegrees(168.0))),
                    config
            );
        }
    }

    public static class Four {

        public static Trajectory intakeTwo = generateFourIntakeTwo();
        public static Trajectory backupFromTrench = generateFourBackupFromTrench();

        public static Trajectory generateFourIntakeTwo() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(10), Units.feetToMeters(6));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(10.821), Units.feetToMeters(2.25), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(20.004), Units.feetToMeters(2.488), Rotation2d.fromDegrees(0.0))),
                    config
            );
        }

        public static Trajectory generateFourBackupFromTrench() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(10), Units.feetToMeters(6));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());
            config.setReversed(true);

            /*
            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(20.004), Units.feetToMeters(2.488), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(16.271), Units.feetToMeters(12.284), Rotation2d.fromDegrees(-90.0))),
                    config
            );
             */
            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(20.004), Units.feetToMeters(2.488), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(17.047), Units.feetToMeters(11.433), Rotation2d.fromDegrees(151.0))),
                    config
            );
        }

    }

}
