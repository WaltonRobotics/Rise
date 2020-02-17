package frc.utils.map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import frc.utils.json.InterpolatingDoubleTreeMapDeserializer;
import frc.utils.json.JsonParser;
import java.io.File;
import java.io.IOException;

@JsonDeserialize(using = InterpolatingDoubleTreeMapDeserializer.class)
public class InterpolatingDoubleTreeMap extends
    InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> {

  public static InterpolatingDoubleTreeMap _fromJson(File json) throws IOException {
    return JsonParser.parseJsonToObject(json, InterpolatingDoubleTreeMap.class);
  }

  @Override
  public InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> fromJson(File json)
      throws IOException, NumberFormatException {
    return _fromJson(json);
  }

  @Override
  public InterpolatingDouble put(String keyFromJson, String valFromJson) {
    return put(new InterpolatingDouble(Double.parseDouble(keyFromJson)),
        new InterpolatingDouble(Double.parseDouble(valFromJson)));
  }
}
