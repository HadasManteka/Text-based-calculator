package calculator.expression;

import calculator.Context;

public class VariableExpr implements IExpression {
    private final String name;
    public VariableExpr(String name) { this.name = name; }
    public double evaluate(Context context) { return context.get(name); }
    public String getName() { return name; }
}
