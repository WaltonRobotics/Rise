package frc.utils.map;


import java.io.File;
import java.io.IOException;

public interface JsonableInterpolatingMap<T, K, V> {

  /**
   * Send this {@code JsonableInterpolatingMap} to a Json file that can then be loaded with {@code
   * fromJson()}.
   */
  public void toJson(File json) throws IOException;

  /**
   * Load an {@code JsonableInterpolatingMap} from a Json File that has been created from {@code
   * toJson()}.
   */
  public T fromJson(File json) throws IOException, NumberFormatException;

  /**
   * @return the interpolated value at key or null, if key cannot fit in the map or if the map is
   * not triangulated.
   */
  public V get(K key);

  /**
   * Puts a new point into the map.
   *
   * @return the old value at key or null, if there wasn't a mapping for key.
   */
  public V put(K key, V value);

  /**
   * Removes the point from the map.
   *
   * @return the value that had been at key or null, if there was not a value at key.
   */
  public V remove(K key);
}
