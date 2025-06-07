package calculator.expression.assign;

import calculator.Context;
import calculator.expression.IExpression;
import calculator.lexer.Token;
import calculator.lexer.TokenType;
import calculator.exception.RuntimeEvaluatorException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class OperatorAssignExprTest {

    private Context context;

    @Before
    public void setup() {
        context = new Context();
        context.set("x", 10.0);
    }

    private IExpression constantExpr(double value) {
        return ctx -> value;
    }

    @Test
    public void testPlusAssign() {
        OperatorAssignExpr expr = new OperatorAssignExpr("x", constantExpr(5),
                                    new Token(TokenType.PLUS_ASSIGN, "+="));
        double result = expr.evaluate(context);
        assertEquals(15.0, result, 0.0);
        assertEquals(15.0, context.get("x"), 0.0);
    }


    @Test
    public void testMinusAssign() {
        OperatorAssignExpr expr = new OperatorAssignExpr("x", constantExpr(3),
                                    new Token(TokenType.MINUS_ASSIGN, "-="));
        double result = expr.evaluate(context);
        assertEquals(7.0, result, 0.0);
        assertEquals(7.0, context.get("x"), 0.0);
    }

    @Test
    public void testMulAssign() {
        OperatorAssignExpr expr = new OperatorAssignExpr("x", constantExpr(2),
                                    new Token(TokenType.MUL_ASSIGN, "*="));
        double result = expr.evaluate(context);
        assertEquals(20.0, result, 0.0);
        assertEquals(20.0, context.get("x"), 0.0);
    }

    @Test
    public void testDivAssign() {
        OperatorAssignExpr expr = new OperatorAssignExpr("x", constantExpr(2),
                                        new Token(TokenType.DIV_ASSIGN, "/="));
        double result = expr.evaluate(context);
        assertEquals(5.0, result, 0.0);
        assertEquals(5.0, context.get("x"), 0.0);
    }

    @Test
    public void testModuloAssign() {
        OperatorAssignExpr expr = new OperatorAssignExpr("x", constantExpr(3),
                                    new Token(TokenType.MODULO_ASSIGN, "%="));
        double result = expr.evaluate(context);
        assertEquals(1.0, result, 0.0);
        assertEquals(1.0, context.get("x"), 0.0);
    }

    @Test(expected = RuntimeEvaluatorException.class)
    public void testDivisionByZero() {
        OperatorAssignExpr expr = new OperatorAssignExpr("x", constantExpr(0),
                                    new Token(TokenType.DIV_ASSIGN, "/="));
        expr.evaluate(context);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownOperator() {
        Token invalidToken = new Token(TokenType.ASSIGN, "=="); // not supported in factory
        OperatorAssignExpr expr = new OperatorAssignExpr("x", constantExpr(5), invalidToken);
        expr.evaluate(context);
    }
}
