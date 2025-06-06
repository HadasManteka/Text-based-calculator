package calculator.expression.unary;

import calculator.Context;
import calculator.expression.IExpression;
import calculator.expression.OperatorFactory;
import calculator.lexer.Token;

public class

PrefixExpr implements IExpression {
    private final String name;
    private final Token operator;

    public PrefixExpr(String name, Token operator) { this.name = name;
        this.operator = operator;
    }
    public double evaluate(Context context) {
        double originVal = context.get(name);
        double newValue = OperatorFactory.getUnaryOperator(operator.type).apply(originVal);

        context.set(name, newValue);
        return newValue;
    }

    public String getName() {
        return name;
    }

    public Token getOperator() {
        return operator;
    }
}
