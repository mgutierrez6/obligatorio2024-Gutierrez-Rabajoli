package TADs.hash;

public interface HashTable<K, V> {
    void put(K key, V value);
    boolean contains(K key);
    V search(K key);
    void remove(K clave);
    int size();
    NodeHash<K, V> get(int posi);
    void changeValue(K key, V newValue);
}