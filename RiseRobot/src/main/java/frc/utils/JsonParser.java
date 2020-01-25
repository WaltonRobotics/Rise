package frc.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * JsonParser is a utility class with generic methods for converting Json files into objects, lists,
 * and maps.
 *
 * @author Russell Newton, Walton Robotics
 **/
public final class JsonParser {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static <R> R parseJsonToObject(File jsonFile, Class<R> contentClass) throws IOException {
    return mapper.readValue(jsonFile, contentClass);
  }

  /**
   * @param listReference must be passed in so that the casting can work properly.
   */
  public static <R> List<R> parseJsonToList(File jsonFile, TypeReference<List<R>> listReference)
      throws IOException {
    return mapper.readValue(jsonFile, listReference);
  }

  /**
   * @param mapReference must be passed in so that the casting can work properly.
   */
  public static <K, V> Map<K, V> parseJsonToMap(File jsonFile,
      TypeReference<Map<K, V>> mapReference) throws IOException {
    return mapper.readValue(jsonFile, mapReference);
  }

  public static void sendObjectToJson(FileWriter jsonFileWriter, Object data) throws IOException {
    mapper.writeValue(jsonFileWriter, data);
  }

  public static void sendObjectToJson(File jsonFile, Object data) throws IOException {
    mapper.writeValue(jsonFile, data);
  }

}