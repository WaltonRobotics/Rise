package frc.utils.map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import frc.utils.json.InterpolatingTreeMapSerializer;
import frc.utils.json.JsonHandler;
import frc.utils.json.JsonableInterpolatingMap;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

/**
 * An {@code InterpolatingTreeMap} will perform interpolation across one independent variable, given
 * known pairs of keys and values.<br>
 *
 * It makes use of the {@code InterpolatingTreeMapSerializer} to serialize the map to a Json file.
 * Any subclass should have a custom deserializer.
 *
 * @see InterpolatingTreeMapSerializer
 */
@JsonSerialize(using = InterpolatingTreeMapSerializer.class)
public abstract class InterpolatingTreeMap<K extends InverseInterpolable<K> & Comparable<K>,
    V extends Interpolable<V>>
    implements JsonableInterpolatingMap<InterpolatingTreeMap<K, V>, K, V> {

  private static final long serialVersionUID = 8347275262778054124L;

  public final TreeMap<K, V> points;

  int max_;

  public InterpolatingTreeMap(int maximumSize) {
    points = new TreeMap<>();
    max_ = maximumSize;
  }

  public InterpolatingTreeMap() {
    this(0);
  }

  @Override
  public V put(K key, V value) {
    if (max_ > 0 && max_ <= points.size()) {
      // "Prune" the tree if it is oversize
      K first = points.firstKey();
      remove(first);
    }

    return points.put(key, value);
  }

  /**
   * This is used by the {@code InterpolatingTreeMapDeserializer} in order to properly
   * deserialize an {@code InterpolatingTreeMap}.
   */
  public abstract V put(String keyFromJson, String valFromJson);

  @Override
  public V remove(K key) {
    return points.remove(key);
  }

  @Override
  public V get(K key) {
    V gotval = points.get(key);
    if (gotval == null) {
      /* Get surrounding keys for interpolation */
      K topBound = points.ceilingKey(key);
      K bottomBound = points.floorKey(key);

      /*
       * If attempting interpolation at ends of tree, return the nearest
       * data point
       */
      if (topBound == null && bottomBound == null) {
        return null;
      } else if (topBound == null) {
        return points.get(bottomBound);
      } else if (bottomBound == null) {
        return points.get(topBound);
      }

      /* Get surrounding values for interpolation */
      V topElem = points.get(topBound);
      V bottomElem = points.get(bottomBound);
      return bottomElem.interpolate(topElem, bottomBound.inverseInterpolate(topBound, key));
    } else {
      return gotval;
    }
  }

  @Override
  public void toJson(File json) throws IOException {
    JsonHandler.sendObjectToJson(json, this);
  }

  @Override
  public String toString() {
    return "InterpolatingTreeMap:\n" + points.toString();
  }
}
