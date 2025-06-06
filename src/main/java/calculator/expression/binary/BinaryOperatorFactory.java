package calculator.expression.binary;

import calculator.exception.RuntimeEvaluatorException;
import calculator.lexer.TokenType;

import java.util.Map;
import java.util.function.BiFunction;

public class BinaryOperatorFactory {

    public static BiFunction<Double, Double, Double> getBinaryOperator(TokenType type) {
        BiFunction<Double, Double, Double> op = BINARY_OPERATORS.get(type);
        if (op == null) {
            throw new IllegalArgumentException("Unknown binary operator: " + type);
        }
        return op;
    }

    private static final Map<TokenType, BiFunction<Double, Double, Double>> BINARY_OPERATORS = Map.of(
        TokenType.PLUS, Double::sum,
        TokenType.MINUS, (a, b) -> a - b,
        TokenType.MUL, (a, b) -> a * b,
        TokenType.DIV, (a, b) -> {
            if (b == 0.0) {
                throw new RuntimeEvaluatorException("Division by zero.");
            } return a / b;},
        TokenType.MODULO, (a, b) -> a % b
    );
}
