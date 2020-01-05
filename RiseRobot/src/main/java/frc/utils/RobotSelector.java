package frc.utils;


import edu.wpi.first.wpilibj.DigitalInput;

public class RobotSelector {

  private boolean firstInput;
  private boolean secondInput;

  public RobotSelector() {
    firstInput = new DigitalInput(9).get();
    secondInput = new DigitalInput(10).get();
  }

  public boolean isFirstInput() {
    return firstInput;
  }

  public boolean isSecondInput() {
    return secondInput;
  }
}
