package calculator.expression.unary;

import calculator.Context;
import calculator.lexer.Token;
import calculator.lexer.TokenType;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class PostfixExprTest {

    private Context context;

    @Before
    public void setup() {
        context = new Context();
        context.set("x", 10.0);
    }

    @Test
    public void testPostfixIncrement() {
        PostfixExpr expr = new PostfixExpr("x", new Token(TokenType.INCREMENT, "++"));
        double result = expr.evaluate(context);

        assertEquals(10.0, result, 0.0);
        assertEquals(11.0, context.get("x"), 0.0);
    }

    @Test
    public void testPostfixDecrement() {
        PostfixExpr expr = new PostfixExpr("x", new Token(TokenType.DECREMENT, "--"));
        double result = expr.evaluate(context);

        assertEquals(10.0, result, 0.0);
        assertEquals(9.0, context.get("x"), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownOperator() {
        Token invalidToken = new Token(TokenType.ASSIGN, "=");
        PostfixExpr expr = new PostfixExpr("x", invalidToken);

        expr.evaluate(context);
    }

    @Test(expected = RuntimeException.class)
    public void testVariableNotSetInContext() {
        PostfixExpr expr = new PostfixExpr("y", new Token(TokenType.INCREMENT, "++"));
        expr.evaluate(context);
    }
}
