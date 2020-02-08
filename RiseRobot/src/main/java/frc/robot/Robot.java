package frc.robot;

import static frc.robot.OI.buttonMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auton.RamseteTrackingCommand;
import frc.robot.commands.auton.ShiftUp;
import frc.robot.commands.teleop.Drive;
import frc.robot.robots.RobotIdentifier;
import frc.robot.robots.WaltRobot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Spinner;
import frc.utils.WaltTimedRobot;

import java.util.Arrays;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends WaltTimedRobot {

    public static Drivetrain drivetrain;
    public static Spinner spinner;

    public static WaltRobot currentRobot;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.

        currentRobot = RobotIdentifier.findByInputs(new DigitalInput(9).get(),
                new DigitalInput(10).get()).getCurrentRobot();

        buttonMap.sendToNetworkTable();

        drivetrain = new Drivetrain();
        spinner = new Spinner();

        CommandScheduler.getInstance().setDefaultCommand(drivetrain, new Drive());
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run();
    }

    /**
     * This function is called once each time the robot enters Disabled mode.
     */
    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    /**
     * This autonomous runs the autonomous command selected
     */
    @Override
    public void autonomousInit() {
        drivetrain.shiftUp();
        drivetrain.reset();
        new SequentialCommandGroup(new ShiftUp(), getAutonomousCommand()).schedule();
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        drivetrain.shiftUp();
        drivetrain.reset();
        drivetrain.zeroNeoEncoders();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    public Command getAutonomousCommand() {
        TrajectoryConfig config = new TrajectoryConfig(
                Units.feetToMeters(6.0), Units.feetToMeters(4.0));
        config.addConstraint(new DifferentialDriveKinematicsConstraint(drivetrain.getDriveKinematics(), Units.feetToMeters(6)));
        config.addConstraint(new DifferentialDriveVoltageConstraint(currentRobot.getDrivetrainFeedforward(), drivetrain.getDriveKinematics(), 10.0));
        config.setKinematics(drivetrain.getDriveKinematics());

        Pose2d initialPose = new Pose2d(Units.feetToMeters(9.385), Units.feetToMeters(2.414), Rotation2d.fromDegrees(0.0));

        drivetrain.reset(initialPose);

        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                Arrays.asList(initialPose,
                        new Pose2d(Units.feetToMeters(16.671), Units.feetToMeters(2.414), Rotation2d.fromDegrees(0.0))),
                config
        );

        RamseteTrackingCommand command = new RamseteTrackingCommand(
                trajectory,
                drivetrain::getRobotPose,
                drivetrain.getRamseteController(),
                currentRobot.getDrivetrainFeedforward(),
                drivetrain.getDriveKinematics(),
                drivetrain::getSpeeds,
                currentRobot.getLeftPIDController(),
                currentRobot.getRightPIDController(),
                drivetrain::setVoltages,
                drivetrain
        );

        return command.andThen(() -> drivetrain.setVoltages(0, 0));
    }

}
