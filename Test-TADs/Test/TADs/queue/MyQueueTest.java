package TADs.queue;

import TADs.linkedlist.MyLinkedListImpl;
import TADs.queue.EmptyQueueException;
import TADs.queue.MyQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyQueueTest {
    MyQueue<Integer> lista = new MyLinkedListImpl<>();
    @Test
    void enqueue() { // Prueba el contains y el size tambien
        lista.enqueue(2);
        assertTrue(lista.contains(2));
        assertEquals(1,lista.size());
    }

    @Test
    void dequeueYSize() throws EmptyQueueException {
        lista.enqueue(2);
        lista.enqueue(4);
        lista.enqueue(1);
        assertEquals(3,lista.size());
        lista.dequeue();
        assertEquals(2,lista.size());
        assertFalse(lista.contains(2)); // vemos que por ser FIFO, tiene que remover el valor 2, entonces tiene que dar false
    }

    @Test
    void emptyQueueException() {
        assertThrows(EmptyQueueException.class, () -> {
            lista.dequeue();
        });
    }

    @Test
    void contains() {
        lista.enqueue(2);
        assertTrue(lista.contains(2));
        assertFalse(lista.contains(10));
    }
}