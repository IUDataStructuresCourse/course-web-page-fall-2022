public interface Map<K,V> {
    V get(K key) throws Exception;
    void put(K key, V value);
    void remove(K key);
    boolean containsKey(K key);
}
