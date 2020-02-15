package frc.robot;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.auton.routines.FurElise;
import frc.robot.commands.teleop.Drive;
import frc.robot.robots.RobotIdentifier;
import frc.robot.robots.WaltRobot;
import frc.robot.subsystems.*;
import frc.utils.AutonSelector;
import frc.utils.LiveDashboardHelper;
import frc.utils.WaltTimedRobot;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

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
    public static TurretShooter shooter;
    public static Climber climber;
    public static Intake intake;

    public static WaltRobot currentRobot;

    public static boolean isBlue = true;

    private FurElise _music = new FurElise();
    private TalonFX _talonFX = new TalonFX(8);

    private Orchestra orchestra = new Orchestra(Collections.singletonList(_talonFX));

    private int musicSelection = 0;

    private SendableChooser<String> songChoice = new SendableChooser<>();

    int _songSelection = 0;

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
        shooter = new TurretShooter();
        climber = new Climber();
        intake = new Intake();


        CommandScheduler.getInstance().setDefaultCommand(drivetrain, new Drive());

        SmartDashboard.putNumber(AUTON_SELECT_ID, 0);
        SmartDashboard.putBoolean(IS_BLUE, false);
//        SmartDashboard.putNumber("Music", 0);

        String[] songs = Arrays.stream(Filesystem.getDeployDirectory().listFiles()).map(File::getName).
                filter(n -> n.endsWith(".chrp")).toArray(String[]::new);
        for(String song : songs) {
            songChoice.addOption(song.substring(0, song.indexOf(".")), song);
        }
        SmartDashboard.putData(songChoice);
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
    }

    @Override
    public void disabledPeriodic() {

        musicSelection = (int) SmartDashboard.getNumber("Music", 0);
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
        int dt = 20; // 20ms per loop

        /* what note to play during this 20ms slice? */
        double freq = _music.GetMusicFrequency(dt);

        /* update the FX. If the freq is 0, no-note is played */
        _talonFX.set(TalonFXControlMode.MusicTone, freq);
    }

    @Override
    public void teleopInit() {
//        orchestra.loadMusic(songs[musicSelection]);
        orchestra.loadMusic(songChoice.getSelected());
        drivetrain.shiftUp();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();

        LiveDashboardHelper.putRobotData(drivetrain.getRobotPose());
        orchestra.play();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

}
