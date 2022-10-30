import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class UndirectedAdjacencyList implements Graph<Integer> {
    ArrayList<LinkedList<Integer>> adjacent;

    /**
     * Constructs a graph with the specified number of vertices
     * but no edges.
     */
    public UndirectedAdjacencyList(int num_vertices) {
        adjacent = new ArrayList<>(num_vertices);
        for (int i = 0; i != num_vertices; ++i)
            adjacent.add(new LinkedList<Integer>());
    }

    /*
    @return     The number of vertices in the graph.
     */
    public int numVertices() {
        return adjacent.size();
    }

    /*
    Inserts an edge into the graph, connecting vertex u to vertex v.
    The vertices are numbered 0 though num_vertices-1.

    @param u    The number for vertex u.
    @param v    The number for vertex v.
     */
    public void addEdge(Integer u, Integer v) {
        adjacent.get(u).addFirst(v);
        adjacent.get(v).addFirst(u);
    }

    public boolean hasEdge(Integer u, Integer v) {
        return adjacent.get(u).contains(v);
    }
    /*
    The adjacent() method provides a way to access all the vertices that
    are adjacent to vertex u. In other words, if the edges
    (u,v_1),...,(u,v_n) are in the graph, then the object
    returned by this method iterates through v_1, ..., v_n.

    The common use case for this method is to support the
    Java 'for' loop:

        for (Integer v : G.adjacent(u)) {
            ...
        }
     */
    public Iterable<Integer> adjacent(Integer u) {
        return adjacent.get(u);
    }

    /*
    The vertices method provides a way to access all the vertices
    in the graph, as represented by integers. So this method
    returns an Iterable object that enumerates the integers 0 through
    num_vertices - 1.
     */
    public Iterable<Integer> vertices() {
        return new IntRangeIterator(0, adjacent.size());
    }

}

class IntRangeIterator implements Iterable<Integer>, Iterator<Integer> {
    private int nextValue;
    private final int max;

    public IntRangeIterator(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        this.nextValue = min;
        this.max = max;
    }

    @Override
    public boolean hasNext() {
        return nextValue < max;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(nextValue++);
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
}
