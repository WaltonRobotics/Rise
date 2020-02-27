package frc.robot.commands.auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class TimedAuton extends SequentialCommandGroup {

    private double startTime;

    public TimedAuton(Command... commands) {
        addCommands(commands);
    }

    @Override
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
        super.initialize();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Time to complete auton " + (Timer.getFPGATimestamp() - startTime));
        super.end(interrupted);
    }

}