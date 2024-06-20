package TADs.binarysearchtree;
import TADs.linkedlist.MyLinkedListImpl;
import TADs.linkedlist.MyList;
import TADs.linkedlist.Node;

public class BinarySearchTreeImpl <K extends Comparable<K>, T> implements BinarySearchTree<K,T> {
    private NodeBST<K, T> root;

    public BinarySearchTreeImpl() {
        this.root = null;
    }

    @Override
    public T find(K key) {
        return findFrom(key,root);
    }

    public T findFrom(K key, NodeBST<K,T> node){
        if(node!=null){
            int temp = key.compareTo(node.getKey());//si esta key es mayor que this.root.key da > 0
            if(temp==0){
                return node.getData();
            } else if (temp>0) { //si key mayor que root key va a la derecha
                return findFrom(key,node.getRightChild());
            }else{
                return findFrom(key,node.getLeftChild());
            }
        }
        return null;
    }

    public NodeBST<K,T> findNode(K key, NodeBST<K,T> node){
        if(node!=null){
            int temp = key.compareTo(node.getKey());//si esta key es mayor que this.root.key da > 0
            if(temp==0){
                return node;
            } else if (temp>0) { //si key mayor que root key va a la derecha
                return findNode(key,node.getRightChild());
            }else{
                return findNode(key,node.getLeftChild());
            }
        }
        return null;
    }

    @Override
    public void insert(K key, T data) {
        NodeBST<K,T> node = new NodeBST<>(key,data);
        if(root!=null){
            root.addNode(node);
        }else {
            root=node;
        }
    }

    @Override
    public void delete(K key) {
        if(root!=null){
            root=root.removeNode(key);
        }
    }

    public MyList<K> inOrderKey(){ //IRD
        MyList<K> list = new MyLinkedListImpl<>();
        if(root!=null){
            root.sortInOrder(list);
        }
        return list;
    }


    public MyList<T> inOrderValue(){ //IRD
        MyList<T> listT = new MyLinkedListImpl<>();
        if(root!=null){
            root.sortInOrderValue(listT);
        }
        return listT;
    }

    public MyList<K> preOrder(){ //RID
        MyList<K> list = new MyLinkedListImpl<>();
        if(root!=null){
            root.sortPreOrden(list);;
        }
        return list;
    }
    public MyList<K> postOrder(){ //IDR
        MyList<K> list = new MyLinkedListImpl<>();
        if(root!=null){
            root.sortPostOrden(list);;
        }
        return list;
    }

//    @Override
//    public void replace(K key, T newValue) {
//        NodeBST<K,T> node = findNode(key,root);
//        node.setData(newValue);
//    }

}
