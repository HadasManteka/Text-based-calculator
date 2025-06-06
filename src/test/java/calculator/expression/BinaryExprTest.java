package calculator.expression;

import static org.junit.Assert.*;
import org.junit.Test;
import calculator.Context;
import calculator.expression.binary.BinaryExpr;
import calculator.lexer.Token;
import calculator.lexer.TokenType;
import calculator.exception.RuntimeEvaluatorException;

public class BinaryExprTest {

    @Test
    public void testAddition() {
        BinaryExpr expr = new BinaryExpr(
                new NumberExpr(3),
                new NumberExpr(4),
                new Token(TokenType.PLUS, "+")
        );
        double result = expr.evaluate(new Context());
        assertEquals(7.0, result, 0.0001);
    }

    @Test
    public void testDivision() {
        BinaryExpr expr = new BinaryExpr(
                new NumberExpr(10),
                new NumberExpr(2),
                new Token(TokenType.DIV, "/")
        );
        double result = expr.evaluate(new Context());
        assertEquals(5.0, result, 0.0001);
    }

    @Test(expected = RuntimeEvaluatorException.class)
    public void testDivisionByZero() {
        BinaryExpr expr = new BinaryExpr(
                new NumberExpr(10),
                new NumberExpr(0),
                new Token(TokenType.DIV, "/")
        );
        expr.evaluate(new Context());
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidOperator() {
        Token invalidOp = new Token(TokenType.EOF, "");
        BinaryExpr expr = new BinaryExpr(
                new NumberExpr(1),
                new NumberExpr(1),
                invalidOp
        );
        expr.evaluate(new Context());
    }
}
