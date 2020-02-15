package frc.utils.map;

import com.fasterxml.jackson.core.type.TypeReference;
import frc.utils.JsonParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InterpolatingTreeMap<K extends InverseInterpolable<K> & Comparable<K>, V extends Interpolable<V>>
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

    @Override
    public V remove(K key) {
        return points.remove(key);
    }

    @Override
    public V get(K key) {
        V gotval = points.get(key);
        if (gotval == null) {
            /** Get surrounding keys for interpolation */
            K topBound = points.ceilingKey(key);
            K bottomBound = points.floorKey(key);

            /**
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

            /** Get surrounding values for interpolation */
            V topElem = points.get(topBound);
            V bottomElem = points.get(bottomBound);
            return bottomElem.interpolate(topElem, bottomBound.inverseInterpolate(topBound, key));
        } else {
            return gotval;
        }
    }


    @Override
    public void toJson(File json) throws IOException {
        List<SerializablePair> pairs = points.entrySet().stream().map(n ->
            new SerializablePair<>(n.getKey(), n.getValue())).collect(Collectors.toList());
        JsonParser.sendObjectToJson(json, pairs);
    }

    @Override
    public InterpolatingTreeMap<K, V> fromJson(File json)
        throws IOException, NumberFormatException {
        return _fromJson(json);
    }

    public static <Q extends InverseInterpolable<Q> & Comparable<Q>, R extends Interpolable<R>>
    InterpolatingTreeMap<Q, R> _fromJson(File json) throws IOException {
        List<SerializablePair<Q, R>> pairs = JsonParser.parseJsonToList(json,
            new TypeReference<>(){});
        InterpolatingTreeMap<Q, R> map = new InterpolatingTreeMap<>();
        pairs.forEach(n -> map.put(n.key, n.value));
        return map;
    }

    public static class SerializablePair<Q, R> {
        Q key;
        R value;

        public SerializablePair(Q key, R value) {
            this.key = key;
            this.value = value;
        }

        public SerializablePair() {
            this(null, null);
        }
    }

}
