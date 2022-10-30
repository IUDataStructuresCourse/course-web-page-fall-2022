public interface Graph<V> {
    int numVertices();
    void addEdge(V source, V target);
    Iterable<V> adjacent(V source);
    Iterable<V> vertices();
}
