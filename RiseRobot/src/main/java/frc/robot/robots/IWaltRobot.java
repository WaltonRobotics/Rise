package frc.robot.robots;

public interface IWaltRobot {

    // Ramsete constants
    double getTrackWidth();
    double getKBeta();
    double getKZeta();

    // Velocity controller constants
    double getLeftKP();
    double getLeftKI();
    double getLeftKD();
    double getLeftKFF();

    double getRightKP();
    double getRightKI();
    double getRightKD();
    double getRightKFF();

    // Motion control constants
    double getKV();
    double getKA();
    double getKS();
    double getKDT();

    double getRpmToMeters();

    void setSpeeds(double leftSpeed, double rightSpeed);
    void setVoltages(double leftVoltage, double rightVoltage);
}
