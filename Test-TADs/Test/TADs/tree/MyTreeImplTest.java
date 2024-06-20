package TADs.tree;

import TADs.tree.MyTree;
import TADs.tree.MyTreeImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyTreeImplTest {

    MyTree<Integer> arbol = new MyTreeImpl<>(1);

    @Test
    void add() {
        arbol.add(2,1);
        arbol.add(4,2);
        assertEquals(1, arbol.getValue());   // verifica que el valor raíz del árbol sea 1.
        assertEquals(1, arbol.getChilds().length); // verifica que haya solo 1 hijo directo de 1
        assertEquals(2,arbol.getChilds()[0].getValue()); // verifica que el hijo direco de 1 sea 2
        assertEquals(1, arbol.getChilds()[0].getChilds().length); //verifica que haya solo 1 hijo directo de 2, usas getChilds()[0] para acceder al nodo 2 y dsp verifica la longitud de sus hijos directos.
        assertEquals(4, arbol.getChilds()[0].getChilds()[0].getValue()); // Verifica que el hijo directo de 2 sea 4, usamos getChilds()[0] para acceder al nodo 2, luego getChilds()[0] para acceder al hijo directo de 2, que es 4, y dsp verificamos su valor.
    }

    @Test
    void searchValue() {
        arbol.add(2,1);
        arbol.add(4,2);
        assertEquals(arbol, arbol.searchValue(1));
        assertEquals(arbol.getChilds()[0], arbol.searchValue(2));
        assertNull(arbol.searchValue(5));
    }

    @Test
    void getValue() {
        assertEquals(1,arbol.getValue());
    }

    @Test
    void getChilds() {
        arbol.add(2,1);
        arbol.add(4,2);
        assertEquals(1, arbol.getChilds().length); //verifica que hay dos nodos directos del nodo raiz
        assertEquals(2, arbol.getChilds()[0].getValue());  //verifica que el prierm hijo tenga valor 2
        assertEquals(4, arbol.getChilds()[0].getChilds()[0].getValue());  // verifia que el segundo hijo tenga valor 4
    }

    @Test
    void setValue() {
        arbol.setValue(5);
        assertEquals(5, arbol.getValue());
    }
}