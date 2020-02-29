package frc.utils;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Paths;
import frc.robot.Paths.Two;
import frc.robot.commands.auton.*;

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
    ONE_A(3, "Shoot 3 from 1 Go back", new TimedAuton(
            new ResetPose(Paths.CrossBaseline.generateBackwards().getInitialPose()),
            new ShootAllBalls(6),
            new RamseteTrackingCommand(Paths.CrossBaseline.backwards)
    )),
    ONE_B(4, "Shoot 3 from 1 Go forwards", new TimedAuton(
            new ShootAllBalls(5),
//            new TurnToAngle(180),
            new ResetPose(Paths.CrossBaseline.generateForwards().getInitialPose()),
            new RamseteTrackingCommand(Paths.CrossBaseline.forwards)
    )),
    ONE_C(6, "Shoot 3 from 2 Go back", new TimedAuton(

    )),
    ONE_D(7, "Shoot 3 from 2 Go forwards", new TimedAuton(

    )),
    ONE_E(8, "Shoot 3 from 3 Go back", new TimedAuton(

    )),
    ONE_F(9, "Shoot 3 from 2 Go forwards", new TimedAuton(

    )),
    TWO_A(10, "Shoot 3 Pick up 3 turn shoot", new TimedAuton(
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ParallelCommandGroup(new ShootAllBalls(3), new IntakeToggleCommand(true, 0.5)),
            new ResetPose(new Pose2d(Two.trenchAndIntake.getInitialPose().getTranslation().getX(),
                    Two.trenchAndIntake.getInitialPose().getTranslation().getY(),
                    Rotation2d.fromDegrees(180))),
            new TurnToAngle(-150).withTimeout(1.375),
            new ParallelDeadlineGroup(
                new RamseteTrackingCommand(Two.trenchAndIntake), new EnableIntakeCommand()),
            new ParallelDeadlineGroup(
                new RamseteTrackingCommand(Two.backUpToShootTrench), new EnableIntakeCommand()),
            new TurnToAngle(25).withTimeout(1.75),
            new AlignToTarget().withTimeout(1.25),
            new ShootAllBalls(4)
    )),
    /**
     * shoot 3, pick up 3 in trench, move and shoot 3
     */
    TWO_B(11, "5", new TimedAuton(
//            new ShootAllBalls(5),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ParallelCommandGroup(new ShootAllBalls(4), new IntakeToggleCommand(true, 0.5)),
            new ResetPose(new Pose2d(Two.trenchAndIntake.getInitialPose().getTranslation().getX(),
                    Two.trenchAndIntake.getInitialPose().getTranslation().getY(),
                    Rotation2d.fromDegrees(180))),
            new TurnToAngle(-150).withTimeout(1.375),
            new ParallelDeadlineGroup(
                    new RamseteTrackingCommand(Two.trenchAndIntake), new EnableIntakeCommand()),
            new ParallelDeadlineGroup(
                new RamseteTrackingCommand(Paths.Two.goShoot), new EnableIntakeCommand()),
            new TurnToAngle(0).withTimeout(2.5),
            new AlignToTarget().withTimeout(1.5),
            new ShootAllBalls(4)
//            new RamseteTrackingCommand(Paths.Two.trenchBackup),
//            new RamseteTrackingCommand(Paths.Two.generatorPickupTwoBalls),
//            new RamseteTrackingCommand(Paths.Two.generatorBackupToShoot)
    )),
    /**
     * Pick up 2 enemy trench, move and shoot 5
     */
    FOUR(12, "5", new TimedAuton(
            new IntakeToggleCommand(true, 0),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ResetPose(Paths.Five.intakeTwo.getInitialPose()),
            new ParallelDeadlineGroup(
                new RamseteTrackingCommand(Paths.Five.intakeTwo), new EnableIntakeCommand()),
            new ParallelDeadlineGroup(
                new RamseteTrackingCommand(Paths.Five.generateFiveTightTurnFromTrench()),
                new EnableIntakeCommand()),
            new RamseteTrackingCommand(Paths.Five.getAwayFromTrench),
            new TurnToAngle(-15),
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
