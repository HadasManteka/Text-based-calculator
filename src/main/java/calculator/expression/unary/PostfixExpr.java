package calculator.expression.unary;

import calculator.Context;
import calculator.expression.IExpression;
import calculator.expression.binary.BinaryOperatorFactory;
import calculator.lexer.Token;

public class PostfixExpr extends UnaryExpression {

    public PostfixExpr(String name, Token operator) {
        super(name, operator);
    }
    public double evaluate(Context context) {
        double originVal = context.get(this.name);
        double newValue = UnaryOperatorFactory.getUnaryOperator(operator.type).apply(originVal);

        context.set(this.name, newValue);
        return originVal;
    }
}
