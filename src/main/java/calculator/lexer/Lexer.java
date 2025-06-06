package calculator.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private static final char EOF_CHAR = '\0';

    private final String input;
    private int pos = 0;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        while (pos < input.length()) {
            char current = input.charAt(pos);

            if (Character.isWhitespace(current)) {
                pos++;
            } else if (Character.isDigit(current)) {
                tokens.add(readNumber());
            } else if (Character.isLetter(current)) {
                tokens.add(readIdentifier());
            } else {
                readOperatorToken(current);
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private void readOperatorToken(char current) {
        switch (current) {
            case '+' -> handlePlus();
            case '-' -> handleMinus();
            case '*' -> handleMul();
            case '/' -> handleDiv();
            case '%' -> handleModulo();
            case '=' -> {
                tokens.add(new Token(TokenType.ASSIGN, "="));
                pos++;
            }
            case '(' -> {
                tokens.add(new Token(TokenType.LPAREN, "("));
                pos++;
            }
            case ')' -> {
                tokens.add(new Token(TokenType.RPAREN, ")"));
                pos++;
            }
            default -> throw new RuntimeException("Unknown character: " + current);
        }
    }

    private void handlePlus() {
        if (peek() == '+') {
            tokens.add(new Token(TokenType.INCREMENT, "++"));
            pos += 2;
        } else if (peek() == '=') {
            tokens.add(new Token(TokenType.PLUS_ASSIGN, "+="));
            pos += 2;
        } else {
            tokens.add(new Token(TokenType.PLUS, "+"));
            pos++;
        }
    }


    private void handleMul() {
        if (peek() == '=') {
            tokens.add(new Token(TokenType.MUL_ASSIGN, "*="));
            pos += 2;
        } else {
            tokens.add(new Token(TokenType.MUL, "*"));
            pos++;
        }
    }

    private void handleDiv() {
        if (peek() == '=') {
            tokens.add(new Token(TokenType.DIV_ASSIGN, "/="));
            pos += 2;
        } else {
            tokens.add(new Token(TokenType.DIV, "/"));
            pos++;
        }
    }

    private void handleModulo() {
        if (peek() == '=') {
            tokens.add(new Token(TokenType.MUL_ASSIGN, "%="));
            pos += 2;
        } else {
            tokens.add(new Token(TokenType.MODULO, "%"));
            pos++;
        }
    }

    private void handleMinus() {
        if (peek() == '-') {
            tokens.add(new Token(TokenType.DECREMENT, "--"));
            pos += 2;
        } else if (peek() == '=') {
            tokens.add(new Token(TokenType.MINUS_ASSIGN, "-="));
            pos += 2;
        } else {
            tokens.add(new Token(TokenType.MINUS, "-"));
            pos++;
        }
    }

    private char peek() {
        return (pos + 1 < input.length()) ? input.charAt(pos + 1) : EOF_CHAR;
    }

    private Token readNumber() {
        int start = pos;
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            pos++;
        }
        return new Token(TokenType.NUMBER, input.substring(start, pos));
    }

    private Token readIdentifier() {
        int start = pos;
        while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
            pos++;
        }
        return new Token(TokenType.IDENTIFIER, input.substring(start, pos));
    }
}