package org.waltonrobotics.plugins.buttonmap.data;

import static org.waltonrobotics.plugins.buttonmap.data.ButtonMap.subTableName;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Russell Newton, Walton Robotics
 **/
public class ButtonMapType extends ComplexDataType<ButtonMap> {

  public static final ButtonMapType Instance = new ButtonMapType();
  private static final String NAME = "ButtonMap";

  public ButtonMapType() {
    super(NAME, ButtonMap.class);
  }

  @Override
  public Function<Map<String, Object>, ButtonMap> fromMap() {
    return map -> {
      String mappingName = subTableName;
      List<Entry<String, Object>> unpairedMappings =
          map.entrySet().stream().filter(n -> n.getKey().startsWith(mappingName)).map(n ->
              new Map.Entry<String, Object>() {
                @Override
                public String getKey() {
                  return n.getKey().substring(mappingName.length() + 1);
                }

                @Override
                public Object getValue() {
                  return n.getValue();
                }

                @Override
                public Object setValue(Object value) {
                  return null;
                }
              }).collect(Collectors.toList());

      HashMap<String, Map<String, Object>> pairedMappings = new HashMap<>();
      unpairedMappings.forEach(n -> {
        int lastSlash = n.getKey().lastIndexOf("/");
        String buttonName = n.getKey().substring(0, lastSlash);
        if (n.getKey().substring(lastSlash + 1).equals("Joystick") ||
            n.getKey().substring(lastSlash + 1).equals("Index")) {
          if (!pairedMappings.containsKey(buttonName)) {
            HashMap<String, Object> tempMap = new HashMap<>();
            tempMap.put(n.getKey().substring(lastSlash + 1), n.getValue());
            pairedMappings.put(buttonName, tempMap);
          } else {
            pairedMappings.get(buttonName).put(n.getKey().substring(lastSlash + 1), n.getValue());
          }
        }
      });

      HashMap<String, ButtonMapping> finalMappings = new HashMap<>();

      pairedMappings.entrySet().stream().filter(n -> n.getValue().size() == 2).forEach(n -> {
        Map<String, Object> values = n.getValue();
        int joystick = ((Double) values.get("Joystick")).intValue();
        int index = ((Double) values.get("Index")).intValue();
        finalMappings.put(n.getKey(), new ButtonMapping(joystick, index));
      });
      return new ButtonMap(finalMappings);
    };
  }

  @Override
  public ButtonMap getDefaultValue() {
    return new ButtonMap(new HashMap<>());
  }
}
