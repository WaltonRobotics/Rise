package frc.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import frc.utils.map.Interpolable;
import frc.utils.map.InterpolatingTreeMap;
import frc.utils.map.InverseInterpolable;
import java.io.IOException;
import java.util.Map.Entry;

/**
 * The {@code InterpolatingTreeMapSerializer} serializes an {@code InterpolatingTreeMap} to a Json
 * file with the following format:
 * <pre>
 *   [{"key":"val"},{"key":"val"},{"key":"val"}...]
 * </pre>
 * Any subclass of {@code InterpolatingTreeMap} should have a custom deserializer
 */
public class InterpolatingTreeMapSerializer<K extends InverseInterpolable<K> & Comparable<K>,
    V extends Interpolable<V>> extends StdSerializer<InterpolatingTreeMap<K, V>> {

  public InterpolatingTreeMapSerializer() {
    this(null);
  }

  public InterpolatingTreeMapSerializer(Class<InterpolatingTreeMap<K, V>> t) {
    super(t);
  }

  @Override
  public void serialize(InterpolatingTreeMap<K, V> value, JsonGenerator gen,
      SerializerProvider provider) throws IOException {

    gen.writeStartArray();
    for (Entry<K, V> entry : value.points.entrySet()) {
      gen.writeStartObject();
      gen.writeStringField(entry.getKey().toString(), entry.getValue().toString());
      gen.writeEndObject();
    }
    gen.writeEndArray();
  }
}
