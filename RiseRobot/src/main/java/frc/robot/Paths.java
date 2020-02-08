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

    public static class TestTrajectories {

        public static Trajectory generateTestForward() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(7), Units.feetToMeters(3));
            config.addConstraint(new DifferentialDriveKinematicsConstraint(drivetrain.getDriveKinematics(), Units.feetToMeters(17)));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(9.32), Units.feetToMeters(13.677), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(17.091), Units.feetToMeters(13.541), Rotation2d.fromDegrees(0.0))),
                    config
            );
        }

        public static Trajectory generateTestSCurve() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(7), Units.feetToMeters(3));
            config.addConstraint(new DifferentialDriveKinematicsConstraint(drivetrain.getDriveKinematics(), Units.feetToMeters(17)));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(4.756), Units.feetToMeters(19.094), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(10.554), Units.feetToMeters(13.295), Rotation2d.fromDegrees(0.0))),
                    config
            );
        }
    }

    public static class CrossBaseline {

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

        public static Trajectory generateTwoTrenchPickup() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(5), Units.feetToMeters(3));
            config.addConstraint(new DifferentialDriveKinematicsConstraint(drivetrain.getDriveKinematics(), Units.feetToMeters(17)));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(10.609), Units.feetToMeters(19.13), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(18.726), Units.feetToMeters(24.688), Rotation2d.fromDegrees(0.0))),
                    config
            );
        }

        public static Trajectory generateTwoIntakeThreeBalls() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(5), Units.feetToMeters(3));
            config.addConstraint(new DifferentialDriveKinematicsConstraint(drivetrain.getDriveKinematics(), Units.feetToMeters(17)));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(18.726), Units.feetToMeters(24.688), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(25.678), Units.feetToMeters(24.688), Rotation2d.fromDegrees(0.0))),
                    config
            );
        }

        public static Trajectory generateTwoPrime() {
            TrajectoryConfig config = new TrajectoryConfig(
                    Units.feetToMeters(13), Units.feetToMeters(7));
            config.addConstraint(new DifferentialDriveKinematicsConstraint(drivetrain.getDriveKinematics(), Units.feetToMeters(17)));
            config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
            config.setKinematics(drivetrain.getDriveKinematics());
            config.setReversed(true);

            return TrajectoryGenerator.generateTrajectory(
                    Arrays.asList(new Pose2d(Units.feetToMeters(18.877), Units.feetToMeters(24.688), Rotation2d.fromDegrees(0.0)),
                            new Pose2d(Units.feetToMeters(10.609), Units.feetToMeters(19.13), Rotation2d.fromDegrees(0.0))),
                    config
            );
        }

    }

}
