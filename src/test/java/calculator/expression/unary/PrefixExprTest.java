package calculator.expression.unary;

import calculator.Context;
import calculator.lexer.Token;
import calculator.lexer.TokenType;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PrefixExprTest {

    private Context context;

    @Before
    public void setup() {
        context = new Context();
        context.set("x", 10.0);
    }

    @Test
    public void testPrefixIncrement() {
        PrefixExpr expr = new PrefixExpr("x", new Token(TokenType.INCREMENT, "++"));
        double result = expr.evaluate(context);

        assertEquals(11.0, result, 0.0);
        assertEquals(11.0, context.get("x"), 0.0);
    }

    @Test
    public void testPrefixDecrement() {
        PrefixExpr expr = new PrefixExpr("x", new Token(TokenType.DECREMENT, "--"));
        double result = expr.evaluate(context);

        assertEquals(9.0, result, 0.0);
        assertEquals(9.0, context.get("x"), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownOperator() {
        Token invalid = new Token(TokenType.ASSIGN, "=");
        PrefixExpr expr = new PrefixExpr("x", invalid);
        expr.evaluate(context);
    }

    @Test(expected = RuntimeException.class)
    public void testVariableNotSetInContext() {
        PrefixExpr expr = new PrefixExpr("y", new Token(TokenType.INCREMENT, "++")); // "y" not initialized
        expr.evaluate(context);
    }
}
