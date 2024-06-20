package TADs.binarysearchtree;

import TADs.linkedlist.MyList;

public interface BinarySearchTree <K extends Comparable<K>, T>
{
    T find(K key);
    void insert (K key, T data);
    void delete (K key);
    MyList<K> inOrderKey();
    MyList<T> inOrderValue();
    MyList<K> preOrder();
    MyList<K> postOrder();
//    void replace(K key, T newValue);
}