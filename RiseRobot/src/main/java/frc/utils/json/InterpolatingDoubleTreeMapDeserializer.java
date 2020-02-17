package frc.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import frc.utils.map.InterpolatingDoubleTreeMap;
import java.io.IOException;

/**
 **/
public class InterpolatingDoubleTreeMapDeserializer extends
    InterpolatingTreeMapDeserializer<InterpolatingDoubleTreeMap> {

  @Override
  public InterpolatingDoubleTreeMap deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    InterpolatingDoubleTreeMap map = new InterpolatingDoubleTreeMap();

    while (p.hasCurrentToken()) {
      p.nextToken();
      if (p.currentToken() == JsonToken.END_ARRAY) {
        break;
      }
      String key = p.nextFieldName();
      String val = p.nextTextValue();
      p.nextToken();
      map.put(key, val);
    }

    return map;
  }
}
