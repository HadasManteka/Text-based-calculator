package calculator.expression.unary;

import calculator.expression.IExpression;
import calculator.lexer.Token;

public abstract class UnaryExpression implements IExpression {
    protected final String name;
    protected final Token operator;

    protected UnaryExpression(String name, Token operator) {
        this.name = name;
        this.operator = operator;
    }
}
