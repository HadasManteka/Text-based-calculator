package calculator.expression;

import calculator.Context;
import calculator.exception.RuntimeEvaluatorException;
import calculator.lexer.Token;


public class BinaryExpr implements IExpression {
    private final IExpression left, right;
    private final Token operator;
    public BinaryExpr(IExpression left, IExpression right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
    public double evaluate(Context context) {
        double leftVal = left.evaluate(context);
        double rightVal = right.evaluate(context);

        return OperatorFactory.getBinaryOperator(operator.type).apply(leftVal, rightVal);
    }

    public IExpression getLeft() {
        return this.left;
    }

    public IExpression getRight() {
        return this.right;
    }

    public Token getOperator() {
        return this.operator;
    }
}