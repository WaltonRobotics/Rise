package frc.utils;

public class MotorConstants {

    public double nominalVoltage;
    public double stallTorque;
    public double stallCurrent;
    public double freeCurrent;

    public double freeSpeed;
    public double R;
    public double Kv;
    public double Kt;

    public MotorConstants(double nominalVoltage, double stallTorque, double stallCurrent, double freeCurrent, double freeSpeed) {
        this.nominalVoltage = nominalVoltage;
        this.stallTorque = stallTorque;
        this.stallCurrent = stallCurrent;
        this.freeCurrent = freeCurrent;

        this.freeSpeed = freeSpeed / 60 * (2.0 * Math.PI);

        this.R = nominalVoltage / stallCurrent;
        this.Kv = freeSpeed / (nominalVoltage - R * freeCurrent);
        this.Kt = stallTorque / stallCurrent;
    }

}
