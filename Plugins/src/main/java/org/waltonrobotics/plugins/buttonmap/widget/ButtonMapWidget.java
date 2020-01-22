package org.waltonrobotics.plugins.buttonmap.widget;

import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.waltonrobotics.plugins.buttonmap.data.ButtonMap;
import org.waltonrobotics.plugins.buttonmap.data.ButtonMapType;
import org.waltonrobotics.plugins.buttonmap.data.ButtonMapping;

/**
 * @author Russell Newton, Walton Robotics
 **/
@Description(name = "Button Map", dataTypes = ButtonMapType.class)
@ParametrizedController("ButtonMapWidget.fxml")
public class ButtonMapWidget extends SimpleAnnotatedWidget<ButtonMap> {

  private static final double hBoxSpacing = 4;
  @FXML
  private VBox root;

  private ObservableMap<String, ButtonMapping> activeRows = FXCollections.observableHashMap();
  private Map<String, Integer> joysticks = Stream.of(new Object[][]{
      {"Left Joystick", 0},
      {"Right Joystick", 1},
      {"Gamepad", 2}
  }).collect(Collectors.toMap(n -> (String) n[0], n -> (Integer) n[1]));
  private Map<String, Integer> namedButtons = new TreeMap<>(Stream.of(new Object[][]{
      {"POV_NW", -9},
      {"POV_W", -8},
      {"POV_SW", -7},
      {"POV_S", -6},
      {"POV_SE", -5},
      {"POV_E", -4},
      {"POV_NE", -3},
      {"POV_N", -2},
      {"UNBOUND", -1},
      {"STANDARD", 0}
  }).collect(Collectors.toMap(n -> (String) n[0], n -> (Integer) n[1])));

  @FXML
  private void initialize() {
    dataOrDefault.addListener((__, oldValue, newValue) ->
        new TreeMap<>(newValue.getMappings()).forEach((k, v) -> {
          if (!activeRows.containsKey(k)) {
            activeRows.put(k, v);
            addHBox(k, v);
          }
        })
    );
  }

  private void addHBox(String mappingName, ButtonMapping mapping) {
    HBox row = new HBox(hBoxSpacing);
    row.getChildren().add(new Label(mappingName));
    row.getChildren().add(getMappingAsComboBoxes(mappingName, mapping));
    root.getChildren().add(row);
  }

  private HBox getMappingAsComboBoxes(String mappingName, ButtonMapping mapping) {
    HBox subRow = new HBox(hBoxSpacing);

    ComboBox<String> joysticksCB = new ComboBox<>(
        FXCollections.observableList(new ArrayList<>(joysticks.keySet())));
    joysticksCB.getSelectionModel().select(
        joysticks.entrySet().stream().filter(n -> n.getValue().equals(mapping.getJoystick())).map(
            Entry::getKey).toArray(String[]::new)[0]);

    ComboBox<String> namedButtonsCB = new ComboBox<>(
        FXCollections.observableList(new ArrayList<>(namedButtons.keySet())));
    namedButtonsCB.getSelectionModel().select(namedButtons.entrySet().stream()
        .filter(n -> n.getValue() == Math.min(0, mapping.getIndex())).map(
            Entry::getKey).toArray(String[]::new)[0]);

    TextField numberField = new TextField();
    numberField.setText(mapping.getIndex() + "");
    numberField.editableProperty().setValue(mapping.getIndex() >= 0);
    numberField.visibleProperty().setValue(mapping.getIndex() >= 0);

    joysticksCB.getSelectionModel().selectedItemProperty().addListener((__, oldData, newData) -> {
      int joystick = joysticks.get(newData);
      setData(getData().removeMapping(mappingName)
          .addMapping(mappingName, new ButtonMapping(joystick, mapping.getIndex())));
    });

    namedButtonsCB.getSelectionModel().selectedItemProperty()
        .addListener((__, oldData, newData) -> {
          if (!newData.equals("STANDARD")) {
            numberField.editableProperty().setValue(false);
            numberField.visibleProperty().setValue(false);
            setData(getData().removeMapping(mappingName).addMapping(mappingName,
                new ButtonMapping(mapping.getJoystick(), namedButtons.get(newData))));
          } else {
            numberField.editableProperty().setValue(true);
            numberField.visibleProperty().setValue(true);
            try {
              int index = Integer.parseInt(newData);
              setData(getData().removeMapping(mappingName).addMapping(mappingName,
                  new ButtonMapping(mapping.getJoystick(), index)));
            } catch (NumberFormatException e) {
              numberField.setText("-1");
            }
          }
        });

    numberField.textProperty().addListener((__, oldData, newData) -> {
      if (namedButtonsCB.getSelectionModel().getSelectedItem().equals("STANDARD") &&
          !(numberField.getText().equals("") || numberField.getText().equals("-"))) {
        try {
          int index = Integer.parseInt(newData);
          setData(getData().removeMapping(mappingName).addMapping(mappingName,
              new ButtonMapping(mapping.getJoystick(), index)));
        } catch (NumberFormatException e) {
          numberField.setText("-1");
        }
      }
    });

    subRow.getChildren().addAll(joysticksCB, namedButtonsCB, numberField);
    return subRow;
  }

  @Override
  public Pane getView() {
    return root;
  }
}
