package frc.robot.robots;

public enum RobotIdentifier {
    COMP_DEEPSPACE(false, false, new CompDeepSpace()),
    PRACTICE_RISE(false, true, new PracticeRise()),
    COMP_RISE(true, true, new CompRise());

    boolean input1;
    boolean input2;

    WaltRobot currentRobot;

    RobotIdentifier(boolean input1, boolean input2, WaltRobot robot) {
        this.input1 = input1;
        this.input2 = input2;
        this.currentRobot = robot;
    }

    public static RobotIdentifier findByInputs(boolean input1, boolean input2) {
        for (RobotIdentifier i : values()) {
            if (i.input1 == input1 && i.input2 == input2) {
                return i;
            }
        }
        return COMP_RISE;
    }

    public WaltRobot getCurrentRobot() {
        return currentRobot;
    }
}