package calculator.expression.assign;

import calculator.Context;
import calculator.expression.IExpression;
import calculator.lexer.Token;

public class AssignExpression implements IExpression {
    protected final String name;
    protected final IExpression expr;
    public AssignExpression(String name, IExpression expr) {
        this.name = name;
        this.expr = expr;
    }
    @Override
    public double evaluate(Context context) {
        double val = expr.evaluate(context);
        context.set(name, val);
        return val;
    }

    public String getName() {
        return name;
    }

    public IExpression getExpression() {
        return expr;
    }
}
