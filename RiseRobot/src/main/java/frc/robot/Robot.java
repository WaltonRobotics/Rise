package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.auton.TurnAtAngle;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.commands.teleop.Drive;
import frc.robot.robots.RobotIdentifier;
import frc.robot.robots.WaltRobot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spinner;

import java.util.Arrays;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static Drivetrain drivetrain;
  public static Shooter shooter;
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

    currentRobot = RobotIdentifier.findByInputs(new DigitalInput(9).get(), new DigitalInput(10).get()).getCurrentRobot();

    drivetrain = new Drivetrain();
    shooter = new Shooter();
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
      new TurnAtAngle(90).schedule();
    drivetrain.shiftUp();
    drivetrain.reset();
    getAutonomousCommand().schedule();
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
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
    SmartDashboard.putNumber("Left encoder", Units.metersToFeet(drivetrain.leftMetersTravelled()));
    SmartDashboard.putNumber("Right encoder", Units.metersToFeet(drivetrain.rightMetersTravelled()));
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
            Units.feetToMeters(2.0), Units.feetToMeters(2.0));
    config.setKinematics(drivetrain.getDriveKinematics());

    Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            Arrays.asList(new Pose2d(), new Pose2d(Units.feetToMeters(5), 0, new Rotation2d())),
            config
    );

    RamseteCommand command = new RamseteCommand(
            trajectory,
            drivetrain::getRobotPose,
            drivetrain.getRamseteController(),
            drivetrain.feedforward,
            drivetrain.getDriveKinematics(),
            drivetrain::getSpeeds,
            drivetrain.leftPIDController,
            drivetrain.rightPIDController,
            drivetrain::setVoltages,
            drivetrain
    );

    return command.andThen(() -> drivetrain.setVoltages(0, 0));
  }

}
