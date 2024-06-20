package TADs.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {


    @Test
    void put() {
        HashTable<Integer,Integer> ht = new HashTableImpl<>(5);
        ht.put(1,1);

        assertTrue(ht.contains(1));
        assertFalse(ht.contains(9));

    }

    @Test
    void contains() {
        HashTable<Integer,Integer> ht = new HashTableImpl<>(5);
        ht.put(1,2);
        assertTrue(ht.contains(1));
    }

    @Test
    void search() {
        HashTable<Integer,Integer> ht = new HashTableImpl<>(5);
        ht.put(1, 2);
        //hashTableTest.put(3, 4);
        assertEquals(2, ht.search(1));
        //assertEquals(4, hashTableTest.search(3));
    }

    @Test
    void remove() {
        HashTable<Integer,Integer> ht = new HashTableImpl<>(5);
        ht.put(1,2);
        ht.put(3,4);
        ht.remove(1);
        assertFalse(ht.contains(1));
        ht.remove(3);
        assertFalse(ht.contains(3));
    }

}