package frc.robot;

import static frc.robot.OI.buttonMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.auton.LiveDashboard;
import frc.robot.commands.auton.ShiftUp;
import frc.robot.commands.teleop.Drive;
import frc.robot.robots.RobotIdentifier;
import frc.robot.robots.WaltRobot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Spinner;
import frc.utils.AutonSelector;
import frc.utils.WaltTimedRobot;

import java.util.List;

import static frc.robot.Constants.FieldConfiguration.DISTANCE_BETWEEN_BASELINES;
import static frc.robot.Constants.FieldConfiguration.REFLECTION_LINE_DISTANCE;
import static frc.robot.Constants.SmartDashboardKeys.AUTON_SELECT_ID;
import static frc.robot.Constants.SmartDashboardKeys.IS_BLUE;
import static frc.robot.OI.buttonMap;

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

    public static boolean isBlue = true;

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

        SmartDashboard.putNumber(AUTON_SELECT_ID, 0);
        SmartDashboard.putBoolean(IS_BLUE, false);
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
        AutonSelector.findById((int)SmartDashboard.getNumber(AUTON_SELECT_ID, 0)).getCommandGroup().schedule();
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
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();

        if (isBlue) {
            LiveDashboard.getInstance().setRobotX(Units.metersToFeet(REFLECTION_LINE_DISTANCE * 2 - drivetrain.getRobotPose().getTranslation().getX()));
            LiveDashboard.getInstance().setRobotY(Units.metersToFeet(drivetrain.getRobotPose().getTranslation().getY()));
            LiveDashboard.getInstance().setRobotHeading(drivetrain.getRobotPose().getRotation().plus(Rotation2d.fromDegrees(180)).getRadians());
        } else {
            LiveDashboard.getInstance().setRobotX(Units.metersToFeet(drivetrain.getRobotPose().getTranslation().getX()));
            LiveDashboard.getInstance().setRobotY(Units.metersToFeet(drivetrain.getRobotPose().getTranslation().getY()));
            LiveDashboard.getInstance().setRobotHeading(drivetrain.getRobotPose().getRotation().getRadians());
        }
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
