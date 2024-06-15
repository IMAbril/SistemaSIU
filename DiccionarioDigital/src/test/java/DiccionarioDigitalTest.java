import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DiccionarioDigitalTest {

    private DiccionarioDigital<String, String> diccionario;

    @Before
    public void setUp() {
        diccionario = new DiccionarioDigital<>();
    }

    @Test
    public void testDiccionarioVacio() {
        assertTrue(diccionario.diccionarioVacio());
        diccionario.definir("apple", "fruta");
        assertFalse(diccionario.diccionarioVacio());
    }

    @Test
    public void testDefinirYObtener() {
        diccionario.definir("apple", "fruta");
        diccionario.definir("orange", "naranja");

        assertEquals("fruta", diccionario.obtener("apple"));
        assertEquals("naranja", diccionario.obtener("orange"));
    }

    @Test
    public void testPertenece() {
        diccionario.definir("apple", "fruta");

        assertTrue(diccionario.pertenece("apple"));
        assertFalse(diccionario.pertenece("orange"));
    }

    @Test
    public void testBorrar() {
        diccionario.definir("apple", "fruta");
        diccionario.definir("orange", "naranja");

        assertTrue(diccionario.pertenece("apple"));
        diccionario.borrar("apple");
        assertFalse(diccionario.pertenece("apple"));
        assertNull(diccionario.obtener("apple"));

        assertTrue(diccionario.pertenece("orange"));
        diccionario.borrar("orange");
        assertFalse(diccionario.pertenece("orange"));
        assertNull(diccionario.obtener("orange"));
    }

    @Test
    public void testCantClaves() {
        assertEquals(0, diccionario.cantClaves());

        diccionario.definir("apple", "fruta");
        diccionario.definir("orange", "naranja");

        assertEquals(2, diccionario.cantClaves());

        diccionario.borrar("apple");
        assertEquals(1, diccionario.cantClaves());

        diccionario.borrar("orange");
        assertEquals(0, diccionario.cantClaves());
    }
}
