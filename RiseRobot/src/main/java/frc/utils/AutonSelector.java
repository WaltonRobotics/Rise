package frc.utils;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Paths;
import frc.robot.Paths.Two;
import frc.robot.commands.auton.*;

import java.nio.file.Path;

import static frc.robot.Robot.drivetrain;

public enum AutonSelector {
    DO_NOTHING(0, "Do Nothing", new SequentialCommandGroup()),
    ZERO_A(1, "Cross Baseline Forwards", new SequentialCommandGroup(
            new ResetPose(Paths.CrossBaseline.forwards.getInitialPose()),
            new RamseteTrackingCommand(Paths.CrossBaseline.forwards)
    )),
    ZERO_B(2, "Cross Baseline Backwards", new SequentialCommandGroup(
            new ResetPose(Paths.CrossBaseline.backwards.getInitialPose()),
            new RamseteTrackingCommand(Paths.CrossBaseline.backwards)
    )),
    ONE_A(3, "Shoot 3 Go back", new TimedAuton(
            new ResetPose(Paths.CrossBaseline.generateBackwards().getInitialPose()),
            new ShootAllBalls(6),
            new RamseteTrackingCommand(Paths.CrossBaseline.backwards)
    )),
    ONE_B(4, "Shoot 3 Turn and Go back", new TimedAuton(
            new ShootAllBalls(5),
            new TurnToAngle(180),
            new ResetPose(Paths.CrossBaseline.generateForwards().getInitialPose()),
            new RamseteTrackingCommand(Paths.CrossBaseline.forwards)
    )),
    TWO_A(5, "Shoot 3 Pick up 3 turn shoot", new TimedAuton(
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ParallelCommandGroup(new ShootAllBalls(3), new IntakeToggleCommand(true, 0.5)),
            new ResetPose(new Pose2d(Paths.Two.trenchPickup.getInitialPose().getTranslation().getX(),
                    Paths.Two.trenchPickup.getInitialPose().getTranslation().getY(),
                    Rotation2d.fromDegrees(180))),
            new TurnToAngle(-130).withTimeout(1.375),
            new RamseteTrackingCommand(Paths.Two.trenchPickup),
            new ParallelDeadlineGroup(
                new RamseteTrackingCommand(Paths.Two.intakeThreeBalls), new EnableIntakeCommand()),
            new ParallelDeadlineGroup(
                new RamseteTrackingCommand(Two.backUpToShootTrench), new EnableIntakeCommand()),
            new TurnToAngle(25).withTimeout(1.75),
            new AlignToTarget().withTimeout(1),
            new ShootAllBalls(3)
    )),
    /**
     * shoot 3, pick up 3 in trench, move and shoot 3
     */
    TWO_B(6, "5", new TimedAuton(
//            new ShootAllBalls(5),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ParallelCommandGroup(new ShootAllBalls(4), new IntakeToggleCommand(true, 0.5)),
            new ResetPose(new Pose2d(Paths.Two.trenchPickup.getInitialPose().getTranslation().getX(),
                    Paths.Two.trenchPickup.getInitialPose().getTranslation().getY(),
                    Rotation2d.fromDegrees(180))),
            new TurnToAngle(-130).withTimeout(1.5),
            new RamseteTrackingCommand(Paths.Two.trenchPickup).andThen(() -> drivetrain.setVoltages(0, 0)),
            new ParallelDeadlineGroup(new RamseteTrackingCommand(Paths.Two.intakeThreeBalls), new EnableIntakeCommand()),
            new ParallelDeadlineGroup(new RamseteTrackingCommand(Paths.Two.goShoot), new EnableIntakeCommand()),
            new TurnToAngle(0).withTimeout(2.5),
            new AlignToTarget().withTimeout(1.5),
            new ShootAllBalls(4)
//            new RamseteTrackingCommand(Paths.Two.trenchBackup),
//            new RamseteTrackingCommand(Paths.Two.generatorPickupTwoBalls),
//            new RamseteTrackingCommand(Paths.Two.generatorBackupToShoot)
    )),
    /**
     * Pick up 2 enemy trench, shoot 5
     */
    FOUR(7, "4", new TimedAuton(
            new IntakeToggleCommand(true, 3),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ResetPose(Paths.Four.intakeTwo.getInitialPose()),
            new RamseteTrackingCommand(Paths.Four.intakeTwo),
            new RamseteTrackingCommand(Paths.Four.backupFromTrench)
    )),
    /**
     * Pick up 2 enemy trench, shoot 5, pick up 3 from our trench, pick up 2 from generator, shoot all
     */
    FIVE(8, "5", new TimedAuton(
            new IntakeToggleCommand(true, 3),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ResetPose(Paths.Five.intakeTwo.getInitialPose()),
            new RamseteTrackingCommand(Paths.Five.intakeTwo),
            new RamseteTrackingCommand(Paths.Five.backupFromTrench),
            new ShootAllBalls(3),
            new WaitCommand(2),
            new RamseteTrackingCommand(Paths.Five.threeBallTrench),
            new RamseteTrackingCommand(Paths.Five.trenchToGenerator)
    )),
    TESTS(254, "Tests", new TimedAuton(
            new InstantCommand(() -> drivetrain.resetHardware()),
            new TurnToAngle(90)
//            new ResetPose(Paths.Two.intakeThreeBalls.getInitialPose()),
//            new RamseteTrackingCommand(Paths.Two.intakeThreeBalls, 0)
    )),
    SCREWING_AROUND(420, "Lol", new TimedAuton(
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ResetPose(Paths.ScrewingAround.infiniteLoop.getInitialPose()),
            new RamseteTrackingCommand(Paths.ScrewingAround.infiniteLoop, 0)
    ));

    int id;
    String name;
    SequentialCommandGroup commandGroup;

    AutonSelector(int id, String name, SequentialCommandGroup commandGroup) {
        this.id = id;
        this.name = name;
        this.commandGroup = commandGroup;
    }

    public static AutonSelector findById(int id) {
        for (AutonSelector i : values()) {
            if (i.id == id) {
                return i;
            }
        }
        return DO_NOTHING;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SequentialCommandGroup getCommandGroup() {
        return commandGroup;
    }

    @Override
    public String toString() {
        return name;
    }
}
