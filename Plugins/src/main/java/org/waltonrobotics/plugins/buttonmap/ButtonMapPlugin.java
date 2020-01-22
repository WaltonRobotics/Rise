package org.waltonrobotics.plugins.buttonmap;

import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.waltonrobotics.plugins.buttonmap.data.ButtonMapType;
import org.waltonrobotics.plugins.buttonmap.data.ButtonMappingType;
import org.waltonrobotics.plugins.buttonmap.widget.ButtonMapWidget;

/**
 * @author Russell Newton
 **/
@Description(
    group = "org.waltonrobotics.plugins",
    name = "ButtonMap",
    version = "1.0.0",
    summary = "A plugin used to display and adjust a dynamic button map"
)
public class ButtonMapPlugin extends Plugin {

  @Override
  public List<DataType> getDataTypes() {
    return List.of(
        ButtonMapType.Instance,
        ButtonMappingType.Instance
    );
  }

  @Override
  public Map<DataType, ComponentType> getDefaultComponents() {
    return Map.of(
        ButtonMapType.Instance, WidgetType.forAnnotatedWidget(ButtonMapWidget.class)
    );
  }

  @Override
  public List<ComponentType> getComponents() {
    return Stream.of(WidgetType.forAnnotatedWidget(ButtonMapWidget.class)).
        collect(Collectors.toList());
  }
}
