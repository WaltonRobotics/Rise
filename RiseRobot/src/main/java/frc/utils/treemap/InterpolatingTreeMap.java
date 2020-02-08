package frc.utils.treemap;

import com.fasterxml.jackson.core.type.TypeReference;
import frc.utils.JsonParser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InterpolatingTreeMap<K extends InverseInterpolable<K> & Comparable<K>, V extends Interpolable<V>>
        extends TreeMap<K, V> {
    private static final long serialVersionUID = 8347275262778054124L;

    int max_;

    public InterpolatingTreeMap(int maximumSize) {
        max_ = maximumSize;
    }

    public InterpolatingTreeMap() {
        this(0);
    }

    /**
     * Inserts a key value pair, and trims the tree if a max size is specified
     *
     * @param key   Key for inserted data
     * @param value Value for inserted data
     * @return the value
     */
    @Override
    public V put(K key, V value) {
        if (max_ > 0 && max_ <= size()) {
            // "Prune" the tree if it is oversize
            K first = firstKey();
            remove(first);
        }

        super.put(key, value);

        return value;
    }

    /**
     * @param key Lookup for a value (does not have to exist)
     * @return V or null; V if it is Interpolable or exists, null if it is at a
     * bound and cannot average
     */
    public V getInterpolated(K key) {
        V gotval = get(key);
        if (gotval == null) {
            /** Get surrounding keys for interpolation */
            K topBound = ceilingKey(key);
            K bottomBound = floorKey(key);

            /**
             * If attempting interpolation at ends of tree, return the nearest
             * data point
             */
            if (topBound == null && bottomBound == null) {
                return null;
            } else if (topBound == null) {
                return get(bottomBound);
            } else if (bottomBound == null) {
                return get(topBound);
            }

            /** Get surrounding values for interpolation */
            V topElem = get(topBound);
            V bottomElem = get(bottomBound);
            return bottomElem.interpolate(topElem, bottomBound.inverseInterpolate(topBound, key));
        } else {
            return gotval;
        }
    }

    /**
     * Send this {@code InterpolatingTreeMap} to a Json file that can then be loaded with {@code fromJson()}.
     * @see InterpolatingTreeMap#fromJson(File)
     */
    public void toJson(File json) throws IOException {
        List<SerializablePair> pairs = entrySet().stream().map(n ->
            new SerializablePair<>(n.getKey(), n.getValue())).collect(Collectors.toList());
        JsonParser.sendObjectToJson(json, pairs);
    }

    /**
     * Load an {@code InterpolatingTreeMap} from a Json File that has been created from {@code toJson()}.
     * @see InterpolatingTreeMap#toJson(File)
     */
    public static <Q extends InverseInterpolable<Q> & Comparable<Q>, R extends Interpolable<R>>
    InterpolatingTreeMap<Q, R> fromJson(File json) throws IOException {
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
