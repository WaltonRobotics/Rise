package frc.utils;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import edu.wpi.first.wpilibj.util.Color;

import static frc.robot.Robot.currentRobot;

public class ColorSensorHelper {

    public static final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    private final ColorMatch colorMatcher = new ColorMatch();

    public ColorSensorHelper() {
        colorMatcher.addColorMatch(kBlueTarget);
        colorMatcher.addColorMatch(kGreenTarget);
        colorMatcher.addColorMatch(kRedTarget);
        colorMatcher.addColorMatch(kYellowTarget);
    }

    public Color getColorMatch() {
        Color detectedColor = currentRobot.getColorSensor().getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);
        return match.color;
    }

}
