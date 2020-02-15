package frc.utils;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Paths;
import frc.robot.commands.auton.*;

import static frc.robot.Robot.drivetrain;

public enum AutonSelector {
    DO_NOTHING(0, "Do Nothing", new SequentialCommandGroup()),
    CROSS_BASELINE_FORWARDS(1, "Cross Baseline Forwards", new SequentialCommandGroup(
            new ShiftUp(),
            new ResetPose(Paths.CrossBaseline.forwards.getInitialPose()),
            new RamseteTrackingCommand(Paths.CrossBaseline.forwards)
    )),
    CROSS_BASELINE_BACKWARDS(2, "Cross Baseline Backwards", new SequentialCommandGroup(
            new ShiftUp(),
            new ResetPose(Paths.CrossBaseline.backwards.getInitialPose()),
            new RamseteTrackingCommand(Paths.CrossBaseline.backwards)
    )),
    TWO_A(3, "2A", new TimeAuto(
            new ShiftUp(),
            new InstantCommand(() -> System.out.println("Shooting 3 balls!")),
            new WaitCommand(2),
            new TurnAtAngle(180).withTimeout(2.5),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ResetPose(Paths.Two.trenchPickup.getInitialPose()),
            new RamseteTrackingCommand(Paths.Two.trenchPickup, 0),
            new DropIntake(),
            new RamseteTrackingCommand(Paths.Two.intakeThreeBalls, 0),
            new RamseteTrackingCommand(Paths.Two.trenchBackup, 0),
            new RamseteTrackingCommand(Paths.Two.generatorPickupTwoBalls, 0),
            new RamseteTrackingCommand(Paths.Two.generatorBackupToShoot, 0)
    )),
    FOUR(4, "4", new TimeAuto(
            new ShiftUp(),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ResetPose(Paths.Four.intakeTwo.getInitialPose()),
            new RamseteTrackingCommand(Paths.Four.intakeTwo),
            new RamseteTrackingCommand(Paths.Four.backupFromTrench)
    )),
    FIVE(5, "5", new TimeAuto(
            new ShiftUp(),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ResetPose(Paths.Five.intakeTwo.getInitialPose()),
            new RamseteTrackingCommand(Paths.Five.intakeTwo),
            new RamseteTrackingCommand(Paths.Five.backupFromTrench),
            new InstantCommand(() -> System.out.println("Shooting 3 balls!")),
            new WaitCommand(2),
            new RamseteTrackingCommand(Paths.Five.threeBallTrench),
            new RamseteTrackingCommand(Paths.Five.trenchToGenerator)
    )),
    TESTS(254, "Tests", new TimeAuto(
            new ShiftUp(),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new TurnAtAngle(180)
    )),
    SCREWING_AROUND(420, "Lol", new TimeAuto(
            new ShiftUp(),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ResetPose(Paths.ScrewingAround.infiniteLoop.getInitialPose()),
            new RamseteTrackingCommand(Paths.ScrewingAround.infiniteLoop)
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
