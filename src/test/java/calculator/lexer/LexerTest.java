package calculator.lexer;


import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class LexerTest {

    @Test
    public void testSingleNumber() {
        Lexer lexer = new Lexer("42");
        List<Token> tokens = lexer.tokenize();

        assertEquals(2, tokens.size());
        assertEquals(TokenType.NUMBER, tokens.get(0).type);
        assertEquals("42", tokens.get(0).value);
        assertEquals(TokenType.EOF, tokens.get(1).type);
    }

    @Test
    public void testIdentifier() {
        Lexer lexer = new Lexer("x1");
        List<Token> tokens = lexer.tokenize();

        assertEquals(2, tokens.size());
        assertEquals(TokenType.IDENTIFIER, tokens.get(0).type);
        assertEquals("x1", tokens.get(0).value);
        assertEquals(TokenType.EOF, tokens.get(1).type);
    }

    @Test
    public void testArithmeticOperators() {
        Lexer lexer = new Lexer("+ - * /");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.PLUS, tokens.get(0).type);
        assertEquals(TokenType.MINUS, tokens.get(1).type);
        assertEquals(TokenType.MUL, tokens.get(2).type);
        assertEquals(TokenType.DIV, tokens.get(3).type);
        assertEquals(TokenType.EOF, tokens.get(4).type);
    }

    @Test
    public void testAssignmentOperators() {
        Lexer lexer = new Lexer("= += -=");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.ASSIGN, tokens.get(0).type);
        assertEquals(TokenType.PLUS_ASSIGN, tokens.get(1).type);
        assertEquals(TokenType.MINUS_ASSIGN, tokens.get(2).type);
        assertEquals(TokenType.EOF, tokens.get(3).type);
    }

    @Test
    public void testIncrementDecrement() {
        Lexer lexer = new Lexer("++ --");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.INCREMENT, tokens.get(0).type);
        assertEquals(TokenType.DECREMENT, tokens.get(1).type);
        assertEquals(TokenType.EOF, tokens.get(2).type);
    }

    @Test
    public void testParentheses() {
        Lexer lexer = new Lexer("( )");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.LPAREN, tokens.get(0).type);
        assertEquals(TokenType.RPAREN, tokens.get(1).type);
        assertEquals(TokenType.EOF, tokens.get(2).type);
    }

    @Test
    public void testComplexExpression() {
        Lexer lexer = new Lexer("x += (y-- - 5) * 3");
        List<Token> tokens = lexer.tokenize();

        assertEquals(TokenType.IDENTIFIER, tokens.get(0).type);
        assertEquals(TokenType.PLUS_ASSIGN, tokens.get(1).type);
        assertEquals(TokenType.LPAREN, tokens.get(2).type);
        assertEquals(TokenType.IDENTIFIER, tokens.get(3).type);
        assertEquals(TokenType.DECREMENT, tokens.get(4).type);
        assertEquals(TokenType.MINUS, tokens.get(5).type);
        assertEquals(TokenType.NUMBER, tokens.get(6).type);
        assertEquals(TokenType.RPAREN, tokens.get(7).type);
        assertEquals(TokenType.MUL, tokens.get(8).type);
        assertEquals(TokenType.NUMBER, tokens.get(9).type);
        assertEquals(TokenType.EOF, tokens.get(10).type);
    }

    @Test(expected = RuntimeException.class)
    public void testUnknownCharacterThrows() {
        Lexer lexer = new Lexer("@");
        lexer.tokenize(); // יזרוק חריגה
    }
}
