package calculator.expression.assign;

import calculator.exception.RuntimeEvaluatorException;
import calculator.lexer.TokenType;

import java.util.Map;
import java.util.function.BiFunction;

public class AssignOperatorFactory {

    public static BiFunction<Double, Double, Double> getAssignOperator(TokenType type) {
        BiFunction<Double, Double, Double> op = ASSIGN_OPERATORS.get(type);
        if (op == null) {
            throw new IllegalArgumentException("Unknown assign operator: " + type);
        }
        return op;
    }

    private static final Map<TokenType, BiFunction<Double, Double, Double>> ASSIGN_OPERATORS = Map.of(
        TokenType.PLUS_ASSIGN, Double::sum,
        TokenType.MINUS_ASSIGN, (a, b) -> a - b,
        TokenType.DIV_ASSIGN, (a, b) -> {
            if (b == 0.0) {
            throw new RuntimeEvaluatorException("Division by zero.");
        } return a / b;},
        TokenType.MUL_ASSIGN, (a, b) -> a * b,
        TokenType.MODULO_ASSIGN, (a, b) -> a % b
    );
}
