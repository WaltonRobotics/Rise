package frc.robot;

import static frc.robot.OI.buttonMap;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.teleop.Drive;
import frc.robot.robots.RobotIdentifier;
import frc.robot.robots.WaltRobot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spinner;

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

  public static NetworkTableInstance networkTableInstance;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    currentRobot = RobotIdentifier
        .findByInputs(new DigitalInput(9).get(), new DigitalInput(10).get()).getCurrentRobot();

    drivetrain = new Drivetrain();
    shooter = new Shooter();
    spinner = new Spinner();

    networkTableInstance = NetworkTableInstance.getDefault();

    CommandScheduler.getInstance().setDefaultCommand(drivetrain, new Drive());

    buttonMap.sendToNetworkTable();
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
    buttonMap.writeToFile();
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

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
}
