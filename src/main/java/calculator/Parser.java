package calculator;

import calculator.exception.ParserException;
import calculator.expression.*;
import calculator.expression.assign.*;
import calculator.expression.binary.BinaryExpr;
import calculator.expression.unary.*;
import calculator.lexer.Token;
import calculator.lexer.TokenType;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token peek() {
        if (pos >= tokens.size()) return new Token(TokenType.EOF, "");
        return tokens.get(pos);
    }

    private Token next() {
        if (pos >= tokens.size()) return new Token(TokenType.EOF, "");
        return tokens.get(pos++);
    }

    private boolean match(TokenType type) {
        if (peek().type == type) {
            next();
            return true;
        }
        return false;
    }

    public IExpression parse() {
        return parseExpression();
    }

    private IExpression parseExpression() {
        return parseAssignment();
    }

    /*
     * Assignment operators
     */
    private IExpression parseAssignment() {
        IExpression left = parseAdditive();

        if (!(left instanceof VariableExpr)) {
            return left;
        }

        Token token = peek();
        if (isAssignmentOperator(token)) {
            next(); // consume the assignment operator
            IExpression right = parseExpression();
            String variableName = ((VariableExpr) left).getName();

            if (token.type == TokenType.ASSIGN) {
                return new AssignExpression(variableName, right);
            } else {
                return new OperatorAssignExpr(variableName, right, token);
            }
        }

        return left;
    }

    /*
     * Unary: add, sub
     */
    private IExpression parseAdditive() {
        IExpression left = parseTerm();
        Token token = peek();

        while (isAdditiveOperator(token)) {
            Token operator = next();
            IExpression right = parseTerm();
            left = new BinaryExpr(left, right, operator);
            token = peek();
        }

        return left;
    }

    /*
     * Binary: mul, div, modulo
     */
    private IExpression parseTerm() {
        IExpression left = parseUnary();
        Token token = peek();
        while (isTermOperator(token)) {
            Token operator = next();
            IExpression right = parseUnary();
            left = new BinaryExpr(left, right, operator);
            token = peek();
        }
        return left;
    }

    /*
     * Unary : prefix, postfix
     */
    private IExpression parseUnary() {
        Token token = peek();

        // Prefix
        if (isUnaryOperator(token)) {
            Token operator = next();
            Token nextToken = peek();
            if (nextToken.type == TokenType.IDENTIFIER) {
                String varName = next().value;
                return new PrefixExpr(varName, operator);
            } else {
                throw new ParserException("Expected identifier after prefix operator, got: " + nextToken.value);
            }
        }

        // Parse primary expression
        IExpression expr = parsePrimary();

        // Postfix
        Token after = peek();
        if (isUnaryOperator(after)) {
            Token operator = next();
            if (expr instanceof VariableExpr) {
                return new PostfixExpr(((VariableExpr) expr).getName(), operator);
            } else {
                throw new ParserException("Postfix operators only allowed on variables");
            }
        }

        return expr;
    }

    /*
     * Primary := NUMBER | IDENTIFIER | LPAREN Expression RPAREN
     */
    private IExpression parsePrimary() {
        Token token = next();

        switch (token.type) {
            case NUMBER:
                return new NumberExpr(Double.parseDouble(token.value));
            case IDENTIFIER:
                return new VariableExpr(token.value);
            case LPAREN:
                IExpression expr = parseExpression();
                if (!match(TokenType.RPAREN)) {
                    throw new ParserException("Expected ')'");
                }
                return expr;
            default:
                throw new ParserException("Unexpected token " + token.value);
        }
    }

    private boolean isAssignmentOperator(Token token) {
        return switch (token.type) {
            case ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, MUL_ASSIGN, DIV_ASSIGN, MODULO_ASSIGN -> true;
            default -> false;
        };
    }

    private boolean isUnaryOperator(Token token) {
        return switch (token.type) {
            case DECREMENT, INCREMENT -> true;
            default -> false;
        };
    }

    private boolean isTermOperator(Token token) {
        return switch (token.type) {
            case MUL, DIV, MODULO -> true;
            default -> false;
        };
    }

    private boolean isAdditiveOperator(Token token) {
        return switch (token.type) {
            case PLUS, MINUS -> true;
            default -> false;
        };
    }
}
