import java.util.HashMap;
import java.util.Map;

public class DisjointSets {

    // Alternative implementation for testing purposes
    public static <V> V union(V x, V y, Map<V, V> parent) {
        V rx = find(x, parent); V ry = find(y, parent);
        parent.put(ry, rx);
        return rx;
    }

    static <V> V find(V u, Map<V,V> parent) {
        while (u != parent.get(u)) {
            u = parent.get(u);
        }
        return u;
    }

    public static <V> void connected_components(Graph<V> G, Map<V,V> representative) {
        HashMap<V, V> parent = new HashMap<>();
        for (V u : G.vertices()) {
            parent.put(u, u);
        }
        for (V u : G.vertices()) {
            for (V v : G.adjacent(u)) {
                DisjointSets.union(u, v, parent);
            }
        }
        for (V u : G.vertices()) {
            representative.put(u, find(u, parent));
        }
    }

    public static <V> boolean check_representative(Graph<V> G, Map<V,V> representative)
    {
        HashMap<V,V> expected = new HashMap<>();
        connected_components(G, expected);

        for (V u : G.vertices())
            for (V v : G.vertices()) {
                if (expected.get(u) == expected.get(v)) {
                    if (representative.get(u) != representative.get(v)) {
                        return false;
                    }
                } else if (representative.get(u) == representative.get(v)) {
                    return false;
                }
            }
        return true;
    }

}
