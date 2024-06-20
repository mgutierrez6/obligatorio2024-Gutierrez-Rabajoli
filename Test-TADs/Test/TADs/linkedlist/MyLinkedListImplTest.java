package TADs.linkedlist;

import TADs.linkedlist.MyLinkedListImpl;
import TADs.linkedlist.MyList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListImplTest {
    MyList<Integer> lista = new MyLinkedListImpl<>();

    @Test
    void addYGet() {
        lista.add(2);
        assertEquals(2,lista.get(0));
        assertNull(lista.get(10));
    }

    @Test
    void contains() {
        lista.add(3);
        lista.add(5);
        lista.add(8);
        assertTrue(lista.contains(5));
        assertFalse(lista.contains(10));
    }

    @Test
    void removeYSize() {
        lista.add(5);
        lista.add(8);
        assertEquals(2,lista.size());
        lista.remove(8);
        assertEquals(1,lista.size());
    }

}