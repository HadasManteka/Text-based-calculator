package calculator;

import calculator.exception.ParserException;
import calculator.expression.*;
import calculator.expression.assign.*;
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
        IExpression left = parseAddSub();

        if (left instanceof VariableExpr) {
            Token token = peek();

            if (token.type == TokenType.ASSIGN ||
                    token.type == TokenType.PLUS_ASSIGN ||
                    token.type == TokenType.MINUS_ASSIGN) {

                next();
                IExpression right = parseExpression();

                if (token.type == TokenType.ASSIGN) {
                    return new AssignExpression(((VariableExpr) left).getName(), right);
                } else {
                    return new OperatorAssignExpr(((VariableExpr) left).getName(), right, token);
                }
            }
        }

        return left;
    }


    /*
     * Unary: add, sub
     */
    private IExpression parseAddSub() {
        IExpression left = parseMulDiv();
        Token token = peek();

        while (token.type == TokenType.PLUS || token.type == TokenType.MINUS) {
            Token operator = next();
            IExpression right = parseMulDiv();
            left = new BinaryExpr(left, right, operator);
            token = peek();
        }

        return left;
    }

    /*
     * Binary: mul, div
     */
    private IExpression parseMulDiv() {
        IExpression left = parseUnary();
        Token token = peek();
        while (token.type == TokenType.MUL || token.type == TokenType.DIV) {
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
        if (token.type == TokenType.INCREMENT || token.type == TokenType.DECREMENT) {
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
        if (after.type == TokenType.INCREMENT || after.type == TokenType.DECREMENT) {
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
}
