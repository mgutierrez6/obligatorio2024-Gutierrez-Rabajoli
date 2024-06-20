package TADs.binarysearchtree;

import TADs.linkedlist.MyList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeImplTest {
    BinarySearchTree<Integer,Integer> arbol = new BinarySearchTreeImpl<>();
    @Test
    void insertYFind() {
        arbol.insert(5,5);
        arbol.insert(8,8);
        arbol.insert(12,12);
        arbol.insert(22,22);
        arbol.insert(2,2);
        arbol.insert(3,3);
        arbol.insert(15,15);

        assertEquals(5, arbol.find(5));
        assertEquals(8, arbol.find(8));
        assertEquals(12, arbol.find(12));
        assertEquals(22, arbol.find(22));
        assertEquals(2, arbol.find(2));
        assertEquals(3, arbol.find(3));
        assertEquals(15, arbol.find(15));

        assertNull(arbol.find(50));
        assertNull(arbol.find(4));
    }

    @Test
    void delete() {
        arbol.insert(5,5);
        arbol.insert(8,8);
        arbol.insert(12,12);
        arbol.insert(22,22);
        arbol.insert(2,2);
        arbol.insert(3,3);
        arbol.insert(15,15);

        arbol.delete(5);
        arbol.delete(12);
        arbol.delete(3);
        arbol.delete(15);
        arbol.delete(8);

        assertNull(arbol.find(5));
        assertNull(arbol.find(12));
        assertNull(arbol.find(3));
        assertNull(arbol.find(15));
        assertNull(arbol.find(8));

        assertEquals(22, arbol.find(22));
        assertEquals(2, arbol.find(2));
    }

    @Test
    void inOrderKey() {
        arbol.insert(5,5);
        arbol.insert(8,8);
        arbol.insert(12,12);
        arbol.insert(22,22);
        arbol.insert(2,2);
        arbol.insert(3,3);
        arbol.insert(15,15);

        MyList<Integer> inOrderList = arbol.inOrderKey();

        assertEquals(2, inOrderList.get(0));
        assertEquals(3, inOrderList.get(1));
        assertEquals(5, inOrderList.get(2));
        assertEquals(8, inOrderList.get(3));
        assertEquals(12, inOrderList.get(4));
        assertEquals(15, inOrderList.get(5));
        assertEquals(22, inOrderList.get(6));
    }

    @Test
    void inOrderKeyValue() {
        arbol.insert(7,1);
        arbol.insert(6,2);
        arbol.insert(5,3);
        arbol.insert(4,4);
        arbol.insert(3,5);
        arbol.insert(2,6);
        arbol.insert(1,7);

        MyList<Integer> inOrderList = arbol.inOrderValue();

        assertEquals(7, inOrderList.get(0));
        assertEquals(6, inOrderList.get(1));
        assertEquals(5, inOrderList.get(2));
        assertEquals(4, inOrderList.get(3));
        assertEquals(3, inOrderList.get(4));
        assertEquals(2, inOrderList.get(5));
        assertEquals(1, inOrderList.get(6));
    }

    @Test
    void preOrder() {
        arbol.insert(5,5);
        arbol.insert(8,8);
        arbol.insert(12,12);
        arbol.insert(22,22);
        arbol.insert(2,2);
        arbol.insert(3,3);
        arbol.insert(15,15);

        MyList<Integer> preOrderList = arbol.preOrder();

        assertEquals(5, preOrderList.get(0));
        assertEquals(2, preOrderList.get(1));
        assertEquals(3, preOrderList.get(2));
        assertEquals(8, preOrderList.get(3));
        assertEquals(12, preOrderList.get(4));
        assertEquals(22, preOrderList.get(5));
        assertEquals(15, preOrderList.get(6));
    }

    @Test
    void postOrder() {
        arbol.insert(5,5);
        arbol.insert(8,8);
        arbol.insert(12,12);
        arbol.insert(22,22);
        arbol.insert(2,2);
        arbol.insert(3,3);
        arbol.insert(15,15);

        MyList<Integer> postOrderList = arbol.postOrder();

        assertEquals(3, postOrderList.get(0));
        assertEquals(2, postOrderList.get(1));
        assertEquals(15, postOrderList.get(2));
        assertEquals(22, postOrderList.get(3));
        assertEquals(12, postOrderList.get(4));
        assertEquals(8, postOrderList.get(5));
        assertEquals(5, postOrderList.get(6));
    }
}