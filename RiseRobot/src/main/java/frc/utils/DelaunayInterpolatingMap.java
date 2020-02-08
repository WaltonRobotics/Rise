package frc.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import edu.wpi.first.wpilibj.drive.Vector2d;
import io.github.jdiemke.triangulation.DelaunayTriangulator;
import io.github.jdiemke.triangulation.NotEnoughPointsException;
import io.github.jdiemke.triangulation.Triangle2D;
import io.github.jdiemke.triangulation.Vector2D;
import org.ejml.data.DMatrixRMaj;
import org.ejml.simple.ops.SimpleOperations_DDRM;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.signum;

/**
 * <p>
 * A DelaunayInterpolatingMap will perform interpolation across two independent variables, given as
 * pairs of input {@code Vector2d}s and output Doubles. In order to determine which three points to
 * use for interpolation, the map uses a modified version of
 * <a href=https://github.com/jdiemke/delaunay-triangulator>Johannes Diemke's Java Delaunay
 * triangulation implementation</a>. It has been modified to include {@code equals()} and {@code
 * hashCode()} methods for his objects.
 *
 * <p>The interpolation algorithm comes from equations 4.2.2a-4.2.2c in
 * <a href=http://www.cs.rpi.edu/~flaherje/pdf/fea4.pdf>this paper</a>.
 *
 * @author Russell Newton, Walton Robotics
 * @see Vector2d
 * @see frc.utils.treemap.InterpolatingTreeMap
 **/
public class DelaunayInterpolatingMap {


  public final Map<Vector2D, Double> points;
  private DelaunayTriangulator triangulator;


  /**
   * @throws NotEnoughPointsException if the size of points is less than 3.
   */
  public DelaunayInterpolatingMap(Map<Vector2d, Double> points) throws NotEnoughPointsException {
    this.points = points.entrySet().stream().collect(Collectors.toMap(n ->
        new Vector2D(n.getKey().x, n.getKey().y), n -> n.getValue()));

    List<Vector2D> vectors = points.keySet().stream().map(n -> new Vector2D(n.x, n.y)).collect(
        Collectors.toList());
    triangulator = new DelaunayTriangulator(vectors);
    triangulator.triangulate();
  }

  private Double put(Vector2D key, Double value) throws NotEnoughPointsException {
    if (!points.containsKey(key)) {
      triangulator.getPointSet().add(key);
      triangulator.triangulate();
    }
    return points.put(key, value);
  }

  /**
   * Puts a new point into the map and updates the triangulation.
   *
   * @return the old value at key or null, if there wasn't a mapping for key.
   * @throws NotEnoughPointsException if, after inserting the point, the map still doesn't have at
   * least three points.
   */
  public Double put(Vector2d key, Double value) throws NotEnoughPointsException {
    return put(new Vector2D(key.x, key.y), value);
  }

  private Double remove(Vector2D key) throws NotEnoughPointsException {
    triangulator.getPointSet().remove(key);
    triangulator.triangulate();
    return points.remove(key);
  }

  /**
   * Removes the point from the map and updates the triangulation.
   *
   * @return the value that had been at key or null, if there was not a value at key.
   * @throws NotEnoughPointsException if, after removing the point, the map has less than three
   * points.
   */
  public Double remove(Vector2d key) throws NotEnoughPointsException {
    return remove(new Vector2D(key.x, key.y));
  }

  /**
   * The interpolation comes from equations 4.2.2a-4.2.2c in
   * <a href=http://www.cs.rpi.edu/~flaherje/pdf/fea4.pdf>this paper</a>.
   *
   * @return the interpolated value at key or null, if key cannot fit in the map.
   */
  public Double get(Vector2d key) {
    Vector2D translatedVector = new Vector2D(key.x, key.y);
    Iterator<Triangle2D> triangleIterator = triangulator.getTriangles().iterator();

    // Find the triangle that it is in, return null if it isn't found in one.
    Triangle2D triangulation = triangleIterator.next();
    while (!contains(triangulation, translatedVector)) {
      if (!triangleIterator.hasNext()) {
        return null;
      }
      triangulation = triangleIterator.next();
    }

    Vector2D[] corners = new Vector2D[]{triangulation.a, triangulation.b, triangulation.c};

    double[] shapeFunctions = IntStream.range(0, 3).mapToDouble(j ->
        getD(corners, j, translatedVector) / getC(corners, j)).toArray();

    return IntStream.range(0, 3).mapToDouble(j -> shapeFunctions[j] * points.get(corners[j])).sum();

  }

  /**
   * This is equation 4.2.2c in <a href=http://www.cs.rpi.edu/~flaherje/pdf/fea4.pdf>this
   * paper</a>.
   */
  private double getC(Vector2D[] corners, int j) {
    SimpleOperations_DDRM simpleOps = new SimpleOperations_DDRM();

    DMatrixRMaj mat = new DMatrixRMaj(new double[][]{
        {1, corners[j].x, corners[j].y},
        {1, corners[(j + 1) % 3].x, corners[(j + 1) % 3].y},
        {1, corners[(j + 2) % 3].x, corners[(j + 2) % 3].y}
    });

    return simpleOps.determinant(mat);
  }

  /**
   * This is equation 4.2.2b in <a href=http://www.cs.rpi.edu/~flaherje/pdf/fea4.pdf>this
   * paper</a>.
   */
  private double getD(Vector2D[] corners, int j, Vector2D toInterpolate) {
    SimpleOperations_DDRM simpleOps = new SimpleOperations_DDRM();

    DMatrixRMaj mat = new DMatrixRMaj(new double[][]{
        {1, toInterpolate.x, toInterpolate.y},
        {1, corners[(j + 1) % 3].x, corners[(j + 1) % 3].y},
        {1, corners[(j + 2) % 3].x, corners[(j + 2) % 3].y}
    });

    return simpleOps.determinant(mat);
  }

  /**
   * I found this process <a href=https://planetcalc.com/8108/>here</a>.
   *
   * @return if triangle contains point. This solution works when the Triangle2D contains() method
   * doesn't.
   */
  private boolean contains(Triangle2D triangle, Vector2D point) {
    double a = triangle.b.sub(triangle.a).cross(point.sub(triangle.a));
    double b = triangle.c.sub(triangle.b).cross(point.sub(triangle.b));
    double c = triangle.a.sub(triangle.c).cross(point.sub(triangle.c));
    double[] crosses = new double[]{a, b, c};
    return IntStream.range(0, 3).mapToObj(n -> checkSet(n, crosses)).filter(n -> n).count() > 0;
  }

  /**
   * @return if all of the cross products have the same sign, if two have the same sign and one is
   * zero, or if two are zero.
   */
  private Boolean checkSet(int i, double[] crosses) {
    boolean allSame = signum(crosses[i]) == signum(crosses[(i + 1) % 3]) &&
        signum(crosses[i]) == signum(crosses[(i + 2) % 3]);
    boolean thisZero = signum(crosses[i]) == 0 &&
        signum(crosses[(i + 1) % 3]) == signum(crosses[(i + 2) % 3]);
    boolean twoZero = signum(crosses[(i + 1) % 3]) == 0 && signum(crosses[(i + 2) % 3]) == 0;
    return allSame || thisZero || twoZero;
  }

  /**
   * Sends this map to a Json file. The map will be serialized with the following format:
   * <pre>
   * {"[x, y]":z, "[x, y]":z, ...}
   * </pre>
   * If you plan on adding to it manually, preserve the format.
   */
  public void toJson(File json) throws IOException {
    Map<List<Double>, Double> serializableMap = points.entrySet().stream().map(n ->
        new Entry<List<Double>, Double>() {
          @Override
          public List<Double> getKey() {
            return Arrays.asList(n.getKey().x, n.getKey().y);
          }

          @Override
          public Double getValue() {
            return n.getValue();
          }

          @Override
          public Double setValue(Double value) {
            return null;
          }
        }).collect(Collectors.toMap(n -> (List<Double>) n.getKey(), n -> (Double) n.getValue()));
    JsonParser.sendObjectToJson(json, serializableMap);
  }

  /**
   * Load a {@code DelaunayInterpolatingMap} from a Json file.
   *
   * @throws IOException if there is a problem loading the file.
   * @throws NotEnoughPointsException if there are not enough points within the file to create a
   * map.
   * @throws NumberFormatException if the file is not formatted properly.
   */
  public static DelaunayInterpolatingMap fromJson(File json)
      throws IOException, NotEnoughPointsException, NumberFormatException {
    Map<String, Double> deserializedMap = JsonParser.parseJsonToMap(json,
        new TypeReference<>() {
        });
    return new DelaunayInterpolatingMap(
        deserializedMap.entrySet().stream().map(n -> new Entry<Vector2d, Double>() {
          @Override
          public Vector2d getKey() {
            // The key is a String that looks like "[x, y]"
            String key = n.getKey();
            double x = Double.parseDouble(key.substring(1, key.indexOf(",")));
            double y = Double.parseDouble(key.substring(key.indexOf(",") + 2, key.length() - 1));
            return new Vector2d(x, y);
          }

          @Override
          public Double getValue() {
            return n.getValue();
          }

          @Override
          public Double setValue(Double value) {
            return null;
          }
        }).collect(Collectors.toMap(n -> n.getKey(), n -> n.getValue())));
  }

  @Override
  public String toString() {
    return "DelaunayInterpolatingMap:\n" + points.toString();
  }

}
