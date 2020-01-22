package frc.utils;

import static edu.wpi.first.networktables.EntryListenerFlags.kNew;
import static edu.wpi.first.networktables.EntryListenerFlags.kUpdate;
import static frc.utils.JsonParser.parseJsonToMap;
import static frc.utils.JsonParser.sendObjectToJson;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import frc.utils.EnhancedJoystickButton.EnhancedButtonIndex;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The DynamicButtonMap class helps to abstract the use of EnhancedJoystickButtons with
 * NetworkTables. <p>In order to properly set up a DynamicButtonMap, having static buttons may cause
 * NullPointerExceptions if they're in the same class as static Joysticks.
 *
 * @author Russell Newton, Walton Robotics
 **/
public final class DynamicButtonMap {

  private static final HashMap<String, int[]> defaultMap = new HashMap<>();
  private static final String buttonMapFileLocation = "/home/lvuser/button_map.json";

  private final Map<String, int[]> mappings;
  private final ArrayList<GenericHID> joysticks;
  private boolean hasChanged;

  public DynamicButtonMap(ArrayList<GenericHID> joysticks, Map<String, int[]> defaults) {
    this.joysticks = joysticks;
    defaultMap.putAll(defaults);
    hasChanged = false;
    mappings = loadFromFile();
  }

  public DynamicButtonMap(GenericHID[] joysticks, Map<String, int[]> defaults) {
    this(new ArrayList<>(List.of(joysticks)), defaults);
  }

  /**
   * Attempt to load the Map from {@code buttonMapFileLocation}. If there is a problem, the default
   * Map is loaded.
   */
  private static Map<String, int[]> loadFromFile() {
    try {
      // Read Json File
      return parseJsonToMap(new File(buttonMapFileLocation),
          new TypeReference<Map<String, int[]>>() {
          });
    } catch (InvalidFormatException e) {
      System.out.println("File " + buttonMapFileLocation
          + " contains values that aren't deserializable into Integers");
    } catch (IOException e) {
      System.out.println("Cannot load file " + buttonMapFileLocation);
    }

    return defaultMap;
  }

  /**
   * Using an EnhancedButtonIndex lets us point to the mappings value, which can change.
   *
   * @return an EnhancedButtonIndex that points to the mappings value, empty EnhancedButtonIndex if
   * mappings does not contain key.
   */
  public EnhancedButtonIndex getButtonIndex(String key) {
    if (mappings.containsKey(key)) {
      // Although it is redundant, writing mappings.get(key) twice keeps the dynamic properties
      return new EnhancedButtonIndex(
          () -> joysticks.get(mappings.get(key)[0]), () -> mappings.get(key)[1]);
    }
    System.out.println("The button map does not contain a map with the key " + key + ".");
    return new EnhancedButtonIndex(null, () -> 0);
  }

  /**
   * Update the mappings index at {@code key}.
   */
  public void updateButtonIndex(String key, int index) {
    mappings.get(key)[1] = index;
    hasChanged = true;
  }

  /**
   * Update the mappings joystick at {@code key}.
   */
  public void updateButtonJoystick(String key, int port) {
    mappings.get(key)[0] = port;
    hasChanged = true;
  }

  /**
   * Update (or add) the mappings value at {@code key} with an EnhancedButtonIndex.
   */
  public void updateButtonValue(String key, EnhancedButtonIndex value) {
    mappings.put(key, new int[]{value.getJoystick().getPort(), value.getIndex()});
  }

  /**
   * If the button map has changed since it was last pulled from {@code buttonMapFileLocation},
   * write the new changes to the file.
   */
  public void writeToFile() {
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
          sendObjectToJson(fw, mappings);
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
   * Reset mappings to defaultMap.
   */
  public void resetButtonMap() {
    mappings.clear();
    mappings.putAll(defaultMap);
  }

  /**
   * Puts DynamicButtonMap data onto a NetworkTable so that it can be used by the DynamicButtonMap
   * widget.
   *
   * @author Russell Newton, Walton Robotics
   */
  public void sendToNetworkTable() {
    NetworkTable buttonMapTable = NetworkTableInstance.getDefault().getTable("Button Map");
    buttonMapTable.getEntry(".type").setString("ButtonMap");

    NetworkTable mapTable = buttonMapTable.getSubTable("Mappings");
    for (Entry<String, int[]> mapping : mappings.entrySet()) {
      NetworkTable mappingTable = mapTable.getSubTable(mapping.getKey());
      mappingTable.getEntry(".type").setString("ButtonMapping");
      NetworkTableEntry joystickEntry = mappingTable.getEntry("Joystick");
      NetworkTableEntry indexEntry = mappingTable.getEntry("Index");
      joystickEntry.setNumber(mapping.getValue()[0]);
      indexEntry.setNumber(mapping.getValue()[1]);

      // Add listeners to update the button map when values are changed
      joystickEntry.addListener(notification ->
              updateButtonJoystick(mapping.getKey(), (int) notification.value.getDouble()),
          kNew | kUpdate);
      indexEntry.addListener(notification ->
              updateButtonIndex(mapping.getKey(), (int) notification.value.getDouble()),
          kNew | kUpdate);
    }
  }

}
