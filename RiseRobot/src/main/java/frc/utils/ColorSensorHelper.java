package frc.utils;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import static frc.robot.Robot.currentRobot;

public class ColorSensorHelper {

    public static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private final ColorMatch colorMatcher = new ColorMatch();

    private final int targetDistanceLimit = 400;

    private ControlPanelColor currentColor = ControlPanelColor.NONE;


    public enum ControlPanelColor {
        RED{
            @Override
            public int getValue() {
                return 0;
            }
        },GREEN{
            @Override
            public int getValue() {
                return 1;
            }
        }, BLUE{
            @Override
            public int getValue() {
                return 2;
            }
        },YELLOW{
            @Override
            public int getValue() {
                return 3;
            }
        }, NONE;
        public int getValue(){
            return -1;
        }
    }
    public ColorSensorHelper() {
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
    }

    public ControlPanelColor getColorMatch() {
        Color detectedColor = currentRobot.getColorSensor().getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        if (getProximity() > SmartDashboard.getNumber("IRSensorLimit", targetDistanceLimit)) {
            if (match.color.equals(kRedTarget)) {
                return ControlPanelColor.RED;
            } else if (match.color.equals(kYellowTarget)) {
                return ControlPanelColor.YELLOW;
            } else if (match.color.equals(kGreenTarget)) {
                return ControlPanelColor.GREEN;
            } else if (match.color.equals(kBlueTarget)) {
                return ControlPanelColor.BLUE;
            }
        }
        return ControlPanelColor.NONE;
    }

    public int getProximity(){
        int detectedValue = currentRobot.getColorSensor().getProximity();
        return detectedValue;
    }

}
