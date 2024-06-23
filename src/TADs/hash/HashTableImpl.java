package TADs.hash;


public class HashTableImpl <K,V> implements HashTable<K,V>{
    private NodeHash<K,V> [] table;
    private int size;
    private int capacity;

    public HashTableImpl(int capacity) {
        this.table = new NodeHash[capacity];
        this.size = 0;
        this.capacity = capacity;
    }


    public int getHash(K key){
        return Math.abs(key.hashCode())%capacity;
    }

    // para la implementacion del spotify, queremos que la tabla este llena, no que se use un load factor, asi que eso lo comento

    @Override
    public void put(K key, V value) {
//        put con load factor:
//        if(key!=null && (size*100/capacity)<75) { // calculamos el load factor para que siempre sea menor a 0,75 (75%)
//            NodeHash<K,V> node = new NodeHash<>(key, value);
//            int posi = node.getCodigoHash()%capacity;
//            if (this.table[posi] == null) {
//                this.table[posi] = node;
//                this.size++;
//            } else {
//                posi--;
//                this.putIn(node,posi);
//            }
//        }else if ((size*100/capacity)>=75){ // si la capacidad es igual al size, significa que no hay mas espacio, hay que agrandar el array
//            changeCapacity(capacity*2); // agranda la tabla
//            put(key,value); //vuelve para agregar
//        }

        if(key!=null && capacity>size) {
            NodeHash<K,V> node = new NodeHash<>(key, value);
            int posi = getHash(key); //en valor absoluto pq no hay posiciones neg.
            if (this.table[posi] == null) {
                this.table[posi] = node;
                this.size++;
            } else {
                posi++;
                this.putIn(node,posi);
            }
        }else{ // si la capacidad es igual al size, significa que no hay mas espacio, hay que agrandar el array
            changeCapacity(capacity*2); // agranda la tabla
            put(key,value); //vuelve para agregar
        }
    }


    public void putIn(NodeHash<K,V> node, int posi){
        if(posi<capacity && this.table[posi]!=null) {
            posi++;
            putIn(node,posi);
        }else if (posi==capacity){ //significa que ya recorrio toda la lista
            posi=0;
            putIn(node,posi);
        }else{
            this.table[posi] = node;
            this.size++;
        }
    }

    @Override
    public boolean contains(K key) {
        return getNodePosi(getHash(key),0,key)!=-1; //true si existe
    }

    @Override
    public V search(K key) {
        if(getNodePosi(getHash(key),0,key)!=-1){ //significa que lo encontró
            return this.table[getNodePosi(getHash(key),0,key)].getValue();
        }
        return null;
    }

    @Override
    public void changeValue(K key, V newValue){
        int posi = getNodePosi(getHash(key),0,key);
        this.table[posi].setValue(newValue);
    }

    public int getNodePosi(int posi, int iter, K key){
        NodeHash<K,V> node = this.table[posi];
        if(node==null){
            return -1;
        } else if (node.getKey().equals(key)) {
            return posi;
        }else if(iter==capacity) { //en el peor de los casos, si ya esta lleno
            return -1;
        }else if (posi==(capacity-1)) {
            posi=0;
            iter++;
            return getNodePosi(posi,iter,key);
        }else{
            posi++;
            iter++;
            return getNodePosi(posi,iter,key);
        }
    }

    @Override
    public void remove(K key) {
        if(contains(key)) { // existe el nodo a borrar
            this.table[getNodePosi(getHash(key),0,key)]=null; //borro el elemento de esa posicion
            this.size--;
        }
    }

    @Override
    public int size() {
        return this.size;
    }


    public void changeCapacity(int newCapacity) {
        this.capacity = newCapacity;
        final NodeHash<K,V> [] oldTable = this.table.clone();
        this.table = new NodeHash[newCapacity];
        if(this.table.length!=0) {
            for (NodeHash<K, V> node : oldTable) {
                if(node!=null) {
                    this.put(node.getKey(), node.getValue());
                }
            }
        }
    }

    @Override
    public NodeHash<K, V> get(int posi) {
        return table[posi];
    }
}
