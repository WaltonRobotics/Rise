/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.utils.JsonParser.parseJsonToMap;
import static frc.utils.JsonParser.sendObjectToJson;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  /**
   * The Joystick class contains port mappings for the HID controllers.
   */
  public static class Joystick {

    public static final int LEFT_JOYSTICK_PORT = 0;
    public static final int RIGHT_JOYSTICK_PORT = 1;
    public static final int GAMEPAD_PORT = 2;
  }

  /**
   * The ButtonMap class contains button mappings represented by String keys in a Map. Use {@code
   * getButtonIndex()} and {@code setButtonIndex()} in order to get the button indices. IntSuppliers
   * are used so that the Map can be dynamic.
   */
  public static class ButtonMap {

    /*
    Add button keys here. When you add new ones, you should also run writeButtonMapToFile().
    This will update the Json file to match the default values
     */
    public static final String SHIFT_UP_BUTTON_KEY = "Shift Up";
    public static final String SHIFT_DOWN_BUTTON_KEY = "Shift Down";

    /*
    Add the default values here, following the example.
    This simulates an initializer list for the map.
     */
    private static final Map<String, Integer> defaultMap = Stream.of(new Object[][]{
//      {KEY, DEFAULT VALUE},
        {SHIFT_UP_BUTTON_KEY, 3},
        {SHIFT_DOWN_BUTTON_KEY, 2},
    }).collect(Collectors.toMap(n -> (String) n[0], n -> (Integer) n[1]));

    /*
    Ignore these. Don't change them.
     */
    private static final String buttonMapFileLocation = "/home/lvuser/button_map.json";
    private static final Map<String, Integer> buttonMap = getButtonMap();
    private static boolean hasChanged = false;

    /**
     * Attempt to load the Map from {@code buttonMapFileLocation}. If there is a problem, the
     * default Map is loaded.
     */
    private static Map<String, Integer> getButtonMap() {
      try {
        // Read Json File
        Map<String, Integer> map = parseJsonToMap(new File(buttonMapFileLocation),
            new TypeReference<>() {
            });
        return map;
      } catch (com.fasterxml.jackson.databind.exc.InvalidFormatException e) {
        System.out.println("File " + buttonMapFileLocation
            + " contains values that aren't deserializable into Integers");
      } catch (IOException e) {
        System.out.println("Cannot load file " + buttonMapFileLocation);
      }

      return defaultMap;
    }

    /**
     * Using an IntSupplier lets us point to the buttonMap value, which can change.
     *
     * @return an IntSupplier that points to the buttonMap value, 0 if buttonMap does not contain
     * key.
     */
    public static IntSupplier getButtonIndex(String key) {
      if (buttonMap.containsKey(key)) {
        return () -> buttonMap.get(key);
      }
      System.out.println("The button map does not contain a map with the key " + key + ".");
      return () -> 0;
    }

    /**
     * Update the buttonMap value at {@code key}.
     */
    public static void updateButtonIndex(String key, int index) {
      buttonMap.put(key, index);
      hasChanged = true;
    }

    /**
     * If the button map has changed since it was last pulled from {@code buttonMapFileLocation},
     * write the new changes to the file.
     */
    public static void writeButtonMapToFile() {
      if (hasChanged) {

        File jsonFile = new File(buttonMapFileLocation);
        File duplicateFile = new File(buttonMapFileLocation + ".backup");
        try {

          // Attempt to backup file
          Files.copy(jsonFile.toPath(), duplicateFile.toPath(), REPLACE_EXISTING);
          jsonFile.delete();
          jsonFile = new File(buttonMapFileLocation);

          // Overwrite with new data
          try (FileWriter fw = new FileWriter(jsonFile)) {
            sendObjectToJson(fw, buttonMap);
          } catch (IOException e) {
            System.out.println("Unable to write to file " + buttonMapFileLocation);
          }

        } catch (IOException e) {
          System.out.println("Unable to backup file " + buttonMapFileLocation);
        }

      } else {
        System.out.println("Nothing in the button map has changed.");
      }
    }

    /**
     * Reset buttonMap to defaultMap.
     */
    public static void resetButtonMap() {
      buttonMap.clear();
      buttonMap.putAll(defaultMap);
    }

  }

}
