package calculator.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int pos = 0;
    private final List<Token> tokens = new ArrayList<>();

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        while (pos < input.length()) {
            char c = input.charAt(pos);

            if (Character.isWhitespace(c)) {
                pos++;
            } else if (Character.isDigit(c)) {
                tokens.add(readNumber());
            } else if (Character.isLetter(c)) {
                tokens.add(readIdentifier());
            } else if (c == '+') {
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
            } else if (c == '-') {
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
            } else if (c == '*') {
                tokens.add(new Token(TokenType.MUL, "*"));
                pos++;
            } else if (c == '/') {
                tokens.add(new Token(TokenType.DIV, "/"));
                pos++;
            } else if (c == '=') {
                tokens.add(new Token(TokenType.ASSIGN, "="));
                pos++;
            } else if (c == '(') {
                tokens.add(new Token(TokenType.LPAREN, "("));
                pos++;
            } else if (c == ')') {
                tokens.add(new Token(TokenType.RPAREN, ")"));
                pos++;
            } else {
                throw new RuntimeException("Unknown character: " + c);
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private char peek() {
        return (pos + 1 < input.length()) ? input.charAt(pos + 1) : '\0';
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
