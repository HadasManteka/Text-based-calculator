package calculator.expression;

import calculator.Context;

// AST Nodes
public interface IExpression {
    double evaluate(Context context);
}