package calculator.expression;

import calculator.Context;

public class NumberExpr implements IExpression {
    private final double value;
    public NumberExpr(double value) { this.value = value; }
    public double evaluate(Context context) { return value; }

    public double getValue() {
        return value;
    }
}
