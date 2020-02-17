package frc.utils.json;

import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import frc.utils.map.InterpolatingDoubleTreeMap;
import frc.utils.map.InterpolatingTreeMap;

/**
 * This specifies the deserialization function for an {@code InterpolatingTreeMap}. Any subclass of
 * {@code InterpolatingTreeMap} should use a subclass of {@code InterpolatingTreeMapDeserializer} as
 * its deserializer.
 *
 * @see InterpolatingDoubleTreeMap
 * @see InterpolatingTreeMapSerializer
 */
public abstract class InterpolatingTreeMapDeserializer<T extends InterpolatingTreeMap> extends
    StdDeserializer<T> {

  public InterpolatingTreeMapDeserializer() {
    this(null);
  }

  public InterpolatingTreeMapDeserializer(Class<T> t) {
    super(t);
  }
}
