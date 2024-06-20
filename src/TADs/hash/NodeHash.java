package TADs.hash;

public class NodeHash<K,V> {
    K key;
    V value;
    int codigoHash;

    public NodeHash(K key, V value) {
        this.key = key;
        this.value = value;
        this.codigoHash = key.hashCode();
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getCodigoHash() {
        return codigoHash;
    }
}
