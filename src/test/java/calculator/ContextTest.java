package calculator;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import calculator.Context;

import java.util.Map;

public class ContextTest {

    private Context context;

    @Before
    public void setUp() {
        context = new Context();
    }

    @Test(expected=RuntimeException.class)
    public void testGetUnknownValue() {
        context.get("unknown key");
    }

    @Test
    public void testSetAndGet() {
        context.set("x", 42.5);
        assertEquals(42.5, context.get("x"), 0.0001);
    }

    @Test
    public void testOverwriteValue() {
        context.set("var", 10);
        assertEquals(10, context.get("var"), 0.0001);
        context.set("var", 20);
        assertEquals(20, context.get("var"), 0.0001);
    }

    @Test
    public void testGetAllReturnsMap() {
        context.set("a", 1.1);
        context.set("b", 2.2);
        Map<String, Double> allVars = context.getAll();
        assertTrue(allVars.containsKey("a"));
        assertTrue(allVars.containsKey("b"));
        assertEquals(1.1, allVars.get("a"), 0.0001);
        assertEquals(2.2, allVars.get("b"), 0.0001);
    }
}