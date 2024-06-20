package TADs.stack;

import TADs.linkedlist.MyLinkedListImpl;
import TADs.stack.EmptyStackException;
import TADs.stack.MyStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
    MyStack<Integer> lista = new MyLinkedListImpl<>();

    @Test
    void push() {
        lista.push(2);
        assertEquals(2,lista.peek());
    }

    @Test
    void popYSize() throws EmptyStackException {
        lista.push(2);
        lista.push(1);
        assertEquals(2, lista.size());
        lista.pop();
        assertEquals(1, lista.size());
        lista.pop();
        assertNull(lista.peek()); // verificamos si el peek funciona cuando esta vacia
    }

    @Test
    void peek() { // como stack es LIFO peek siempre muestra el ultimo en agregarse
        lista.push(2);
        assertEquals(2,lista.peek());
        lista.push(4);
        assertEquals(4,lista.peek());
    }

    @Test
    void emptyStackException() {
        assertThrows(EmptyStackException.class, () -> { // expresión lambda que representa la acción que debe lanzar la excepción
            lista.pop();
        });
    }

}