package Activities;



import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {

    public static List<String> list;

    @BeforeAll
    public static void setUp(){

        // Initialize a new ArrayList
        list = new ArrayList<String>();

      // Add values to the list
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }

    @Test
    @Order(0)
    public void insertTest(){

        assertEquals(2,list.size());

        list.add("zeta");

        assertEquals(3, list.size(), "Wrong size");

        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("Charlie", list.get(1), "Wrong element");
        assertEquals("zeta", list.get(2), "Wrong element");
    }

    @Test
    @Order(2)
    public void replaceTest(){

        list.set(1,"Charlie");
        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("Charlie", list.get(1), "Wrong element");
        //assertEquals("zeta", list.get(2), "Wrong element");
    }
}
