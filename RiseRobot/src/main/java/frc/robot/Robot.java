package frc.robot;

import static frc.robot.Constants.SmartDashboardKeys.AUTON_SELECT_ID;
import static frc.robot.Constants.SmartDashboardKeys.IS_BLUE;
import static frc.robot.OI.buttonMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.PitCheck;
import frc.robot.commands.teleop.ClimbCommand;
import frc.robot.commands.teleop.DriveCommand;
import frc.robot.commands.teleop.IntakeConveyorCommand;
import frc.robot.commands.teleop.TurretShooterCommand;
import frc.robot.robots.RobotIdentifier;
import frc.robot.robots.WaltRobot;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.IntakeConveyor;
import frc.robot.subsystems.Spinner;
import frc.robot.subsystems.TurretShooter;
import frc.utils.AutonSelector;
import frc.utils.LiveDashboardHelper;
import frc.utils.WaltTimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends WaltTimedRobot {

  public static Drivetrain drivetrain;
  public static Spinner spinner;
  public static TurretShooter turretShooter;
  public static Climber climber;
  public static IntakeConveyor intakeConveyor;

  public static WaltRobot currentRobot;

  public static boolean isBlue = true;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    currentRobot = RobotIdentifier.findByInputs(new DigitalInput(9).get(),
        new DigitalInput(10).get()).getCurrentRobot();

    buttonMap.sendToNetworkTable();

    drivetrain = new Drivetrain();
    spinner = new Spinner();
    turretShooter = new TurretShooter();
    climber = new Climber();
    intakeConveyor = new IntakeConveyor();

    SmartDashboard.putNumber(AUTON_SELECT_ID, 0);
    SmartDashboard.putBoolean(IS_BLUE, false);
    CommandScheduler.getInstance().setDefaultCommand(drivetrain, new DriveCommand());
    CommandScheduler.getInstance().setDefaultCommand(turretShooter, new TurretShooterCommand());
    CommandScheduler.getInstance().setDefaultCommand(intakeConveyor, new IntakeConveyorCommand());
    CommandScheduler.getInstance().setDefaultCommand(climber, new ClimbCommand());
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
    CommandScheduler.getInstance().run();
    if (DriverStation.getInstance().getAlliance() != DriverStation.Alliance.Invalid) {
      isBlue = SmartDashboard.getBoolean(IS_BLUE, false);
    } else {
      isBlue = (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue);
    }
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    SmartDashboard.delete("Run Pit Check");
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected
   */
  @Override
  public void autonomousInit() {
    drivetrain.resetHardware();
    AutonSelector.findById((int) SmartDashboard.getNumber(AUTON_SELECT_ID, 0)).getCommandGroup()
        .schedule();
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
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();

    LiveDashboardHelper.putRobotData(drivetrain.getRobotPose());
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();

    SmartDashboard.putData("Run Pit Check",
        new PitCheck(drivetrain, spinner, turretShooter, climber, intakeConveyor));
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    CommandScheduler.getInstance().run();
  }

}
