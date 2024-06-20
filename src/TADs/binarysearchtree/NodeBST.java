package TADs.binarysearchtree;

import TADs.linkedlist.MyList;

public class NodeBST <K extends Comparable<K>, T> {
    private K key;
    private T data;
    private NodeBST <K, T> leftChild;
    private NodeBST <K, T> rightChild;

    public NodeBST(K key, T data) {
        this.key = key;
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }
    public NodeBST<K, T> getLeftChild() {
        return leftChild;
    }

    public NodeBST<K, T> getRightChild() {
        return rightChild;
    }

    public void addNode(NodeBST<K,T> node) {
        if (node != null) {
            int temp = node.getKey().compareTo(this.key); // compara la clave del nodo con la clave actual

            if (temp > 0) { // si la clave del nodo es mayor, va a la derecha
                if (rightChild == null) {
                    rightChild = node;
                } else {
                    rightChild.addNode(node);
                }
            } else if (temp < 0) { // si la clave del nodo es menor, va a la izquierda
                if (leftChild == null) {
                    leftChild = node;
                } else {
                    leftChild.addNode(node);
                }
            }
            // Si las claves son iguales, no se hace nada o se maneja de otra manera si lo deseas
        }
    }

    public NodeBST<K,T> getMin(){ //busque la
        // minima key del hijo derecho
        NodeBST<K,T> esteNodo=this;
        if(leftChild!=null){ //del lado izquierdo siempre esta la menor key
            esteNodo=leftChild.getMin();
        }
        return esteNodo;
    }

    public NodeBST<K,T> removeNode(K key) {
        NodeBST<K,T> esteNodo=this;
        int temp = key.compareTo(this.key);
        if (temp > 0 && rightChild!=null) {
            rightChild = rightChild.removeNode(key);

        } else if (temp < 0 && leftChild != null) {
            leftChild = leftChild.removeNode(key);

        } else if (leftChild != null && rightChild != null) {
            NodeBST<K, T> nodoMin = rightChild.getMin();
            this.key = nodoMin.getKey();
            this.data = nodoMin.getData();
            rightChild = rightChild.removeNode(nodoMin.getKey());

        } else if (leftChild != null) {
                esteNodo = leftChild;

        } else{
                esteNodo = rightChild;
        }
        return esteNodo;
    }

    public void sortInOrder(MyList<K> list){
        if (leftChild!=null){
            leftChild.sortInOrder(list);
        }
        list.add(this.getKey());
        if(rightChild!=null){
            rightChild.sortInOrder(list);
        }
    }

    public void sortInOrderValue(MyList<T> list){
        if (leftChild!=null){
            leftChild.sortInOrderValue(list);
        }
        list.add(this.getData());
        if(rightChild!=null){
            rightChild.sortInOrderValue(list);
        }
    }

    public void sortPreOrden(MyList<K> list){
        list.add(this.getKey());
        if (leftChild!=null){
            leftChild.sortPreOrden(list);
        }
        if(rightChild!=null){
            rightChild.sortPreOrden(list);
        }
    }

    public void sortPostOrden(MyList<K> list){
        if (leftChild!=null){
            leftChild.sortPostOrden(list);
        }
        if (rightChild!=null){
            rightChild.sortPostOrden(list);
        }
        list.add(this.getKey());
    }

    public void setData(T newData) {
        this.data = newData;
    }
}
