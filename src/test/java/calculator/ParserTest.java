package calculator;

import calculator.exception.ParserException;
import calculator.expression.BinaryExpr;
import calculator.expression.IExpression;
import calculator.expression.NumberExpr;
import calculator.expression.assign.AssignExpression;
import calculator.expression.unary.PrefixExpr;
import calculator.lexer.Token;
import calculator.lexer.TokenType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTest {

    private IExpression parseTokens(List<Token> tokens) {
        Parser parser = new Parser(tokens);
        return parser.parse();
    }

    @Test
    public void testParseSimpleAddExpressionStructure() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.NUMBER, "1"),
                new Token(TokenType.PLUS, "+"),
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.EOF, "")
        );

        IExpression expr = parseTokens(tokens);
        assertTrue(expr instanceof BinaryExpr);

        BinaryExpr binExpr = (BinaryExpr) expr;
        assertTrue(binExpr.getLeft() instanceof NumberExpr);
        assertTrue(binExpr.getRight() instanceof NumberExpr);
        assertEquals("+", binExpr.getOperator().value);
    }

    @Test
    public void testParseAssignmentStructure() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.IDENTIFIER, "a"),
                new Token(TokenType.ASSIGN, "="),
                new Token(TokenType.NUMBER, "5"),
                new Token(TokenType.EOF, "")
        );

        IExpression expr = parseTokens(tokens);
        assertTrue(expr instanceof AssignExpression);

        AssignExpression assign = (AssignExpression) expr;
        assertEquals("a", assign.getName());
        assertTrue(assign.getExpression() instanceof NumberExpr);
    }

    @Test
    public void testParseParenthesesStructure() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.NUMBER, "3"),
                new Token(TokenType.PLUS, "+"),
                new Token(TokenType.NUMBER, "4"),
                new Token(TokenType.RPAREN, ")"),
                new Token(TokenType.MUL, "*"),
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.EOF, "")
        );

        IExpression expr = parseTokens(tokens);
        assertTrue(expr instanceof BinaryExpr); // * at top

        BinaryExpr mul = (BinaryExpr) expr;
        assertEquals("*", mul.getOperator().value);

        assertTrue(mul.getLeft() instanceof BinaryExpr); // (3 + 4)
        BinaryExpr inner = (BinaryExpr) mul.getLeft();
        assertEquals("+", inner.getOperator().value);

        assertTrue(mul.getRight() instanceof NumberExpr);
        NumberExpr numberExp = (NumberExpr) mul.getRight();
        assertEquals(2.0, numberExp.getValue(), 0.0);
    }

    @Test
    public void testPrefixIncrementStructure() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.INCREMENT, "++"),
                new Token(TokenType.IDENTIFIER, "x"),
                new Token(TokenType.EOF, "")
        );

        IExpression expr = parseTokens(tokens);
        assertTrue(expr instanceof PrefixExpr);

        PrefixExpr prefix = (PrefixExpr) expr;
        assertEquals("x", prefix.getName());
        assertEquals(TokenType.INCREMENT, prefix.getOperator().type);
    }

    @Test(expected = ParserException.class)
    public void testUnclosedParenthesisThrows() {
        List<Token> tokens = Arrays.asList(
                new Token(TokenType.LPAREN, "("),
                new Token(TokenType.NUMBER, "1"),
                new Token(TokenType.PLUS, "+"),
                new Token(TokenType.NUMBER, "2"),
                new Token(TokenType.EOF, "")
        );
        parseTokens(tokens);
    }

}
