package frc.utils;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.util.Units;
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
            new RamseteTrackingCommand(Paths.CrossBaseline.generateForwards())
    )),
    CROSS_BASELINE_BACKWARDS(2, "Cross Baseline Backwards", new SequentialCommandGroup(
            new ShiftUp(),
            new RamseteTrackingCommand(Paths.CrossBaseline.generateBackwards())
    )),
    TWO_A(3, "2A", new TimeAuton(
            new ShiftUp(),
            new InstantCommand(() -> System.out.println("Shooting 3 balls!")),
            DifferentialDriveOdometry
            new WaitCommand(2),
            // new TurnAtAngle(180).withTimeout(2.5),
            new InstantCommand(() -> drivetrain.resetHardware()),
            new ResetPose(Paths.Two.generateTwoTrenchPickup().getInitialPose()),
            new RamseteTrackingCommand(Paths.Two.generateTwoTrenchPickup()),
            new DropIntake(),
            new RamseteTrackingCommand(Paths.Two.generateTwoIntakeThreeBalls())
    )),
    TWO_A_PRIME(4, "2A'", new SequentialCommandGroup(
            new ShiftUp(),
            new RamseteTrackingCommand(Paths.TestTrajectories.generateTestSCurve())
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
