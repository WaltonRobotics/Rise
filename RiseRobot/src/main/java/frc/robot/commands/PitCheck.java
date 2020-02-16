package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WaltSubsystem;
import java.util.ArrayList;

public class PitCheck extends CommandBase {

  public PitCheck(WaltSubsystem... subsystems) {
    addRequirements(subsystems);
  }

  @Override
  public void initialize() {
    System.out.println("Performing a pit check...");
    for(WaltSubsystem subsystem :
        getRequirements().stream().map(n -> (WaltSubsystem) n).toArray(WaltSubsystem[]::new)) {
      ArrayList<String> failures = subsystem.getPitCheckFunction().get();
      System.out.println(String.format("%s Failures%n%s", subsystem.getClass().getName(),
          failures.toString()));
    }
  }

  @Override
  public boolean isFinished() {
    return true;
  }

}
