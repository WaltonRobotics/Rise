package org.waltonrobotics.plugin;

import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.theme.Theme;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.waltonrobotics.plugin.data.ButtonMapType;
import org.waltonrobotics.plugin.data.ButtonMappingType;
import org.waltonrobotics.plugin.widget.ButtonMapWidget;

/**
 * If you want to install this plugin, either use the "install" Gradle task on the source, or get
 * the .jar file from the build/libs/ folder.
 *
 * @author Russell Newton
 **/
@Description(
    group = "org.waltonrobotics",
    name = "WRTDeepSpace",
    version = "1.0.0",
    summary = "This plugin provides for widgets and themes used by WaltonRobotics in FRC DeepSpace."
)
public class WRTDeepSpacePlugin extends Plugin {

//  private final Theme wrtTheme =
//      new Theme( "WRT Theme", "org/waltonrobotics/plugin/wrt-theme.css");

//  @Override
//  public List<Theme> getThemes() {
//    return List.of(wrtTheme);
//  }

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
