package calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    public void testEvaluateSimpleExpression() {
        double result = calculator.evaluateLine("1 + 2 * 3");
        assertEquals(7.0, result, 0.0);
    }

    @Test
    public void testAssignmentCreatesVariable() {
        double result = calculator.evaluateLine("x = 10");
        assertEquals(10.0, result, 0.0);
    }

    @Test
    public void testUsingAssignedVariable() {
        calculator.evaluateLine("x = 4");
        double result = calculator.evaluateLine("x * 2");
        assertEquals(8.0, result, 0.0);
    }

    @Test
    public void testPlusAssignOperator() {
        calculator.evaluateLine("a = 5");
        double result = calculator.evaluateLine("a += 3");
        assertEquals(8.0, result, 0.0);
    }

    @Test
    public void testPrefixIncrement() {
        calculator.evaluateLine("x = 5");
        double result = calculator.evaluateLine("++x");
        assertEquals(6.0, result, 0.0);
    }

    @Test
    public void testPostfixDecrement() {
        calculator.evaluateLine("y = 8");
        double result = calculator.evaluateLine("y--");
        assertEquals(8.0, result, 0.0); // returns old value
        assertEquals(7.0, calculator.getVariable("y"), 0.0);
    }

    @Test(expected = RuntimeException.class)
    public void testUsingUndefinedVariableThrows() {
        calculator.evaluateLine("notDefinedVar + 1");
    }

    @Test(expected = RuntimeException.class)
    public void testSyntaxErrorThrows() {
        calculator.evaluateLine("5 +");
    }

    @Test
    public void testChainedExpressions() {
        calculator.evaluateLine("a = 2");
        calculator.evaluateLine("b = 3");
        calculator.evaluateLine("c = a + b * 2");
        assertEquals(8.0, calculator.evaluateLine("c"), 0.0);
        assertEquals(2.0, calculator.getVariable("a"), 0.0);
        assertEquals(3.0, calculator.getVariable("b"), 0.0);
        assertEquals(8.0, calculator.getVariable("c"), 0.0);
    }
}