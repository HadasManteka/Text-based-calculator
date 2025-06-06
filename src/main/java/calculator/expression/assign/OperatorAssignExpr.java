package calculator.expression.assign;

import calculator.Context;
import calculator.expression.IExpression;
import calculator.lexer.Token;

public class OperatorAssignExpr extends AssignExpression{
    private final Token operator;

    public OperatorAssignExpr(String name, IExpression expr, Token operator) {
        super(name,expr);
        this.operator = operator;
    }

    @Override
    public double evaluate(Context context) {
        double val = AssignOperatorFactory.getAssignOperator(operator.type)
                        .apply(context.get(name), expr.evaluate(context));
        context.set(name, val);
        return val;
    }
}
