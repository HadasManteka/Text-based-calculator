package calculator.expression;

import calculator.exception.RuntimeEvaluatorException;
import calculator.lexer.TokenType;

import java.io.DataOutput;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

public class OperatorFactory {

    public static BiFunction<Double, Double, Double> getBinaryOperator(TokenType type) {
        BiFunction<Double, Double, Double> op = BINARY_OPERATORS.get(type);
        if (op == null) {
            throw new IllegalArgumentException("Unknown binary operator: " + type);
        }
        return op;
    }

    public static Function<Double, Double> getUnaryOperator(TokenType type) {
        Function<Double, Double> op = UNARY_OPERATORS.get(type);
        if (op == null) {
            throw new IllegalArgumentException("Unknown unary operator: " + type);
        }
        return op;
    }

    public static BiFunction<Double, Double, Double> getAssignOperator(TokenType type) {
        BiFunction<Double, Double, Double> op = ASSIGN_OPERATORS.get(type);
        if (op == null) {
            throw new IllegalArgumentException("Unknown assign operator: " + type);
        }
        return op;
    }

    private static final Map<TokenType, BiFunction<Double, Double, Double>> BINARY_OPERATORS = Map.of(
            TokenType.PLUS,  Double::sum,
            TokenType.MINUS, (a, b) -> a - b,
            TokenType.MUL,   (a, b) -> {
                if (b == 0.0) {
                throw new RuntimeEvaluatorException("Division by zero.");
            } return a * b;},
            TokenType.DIV,   (a, b) -> a / b

//            TokenType.MODULO, (a, b) -> a % b

    );

    private static final Map<TokenType, Function<Double, Double>> UNARY_OPERATORS = Map.of(
            TokenType.INCREMENT,  a -> a++,
            TokenType.DECREMENT, a -> a--
    );

    private static final Map<TokenType, BiFunction<Double, Double, Double>> ASSIGN_OPERATORS = Map.of(
            TokenType.PLUS_ASSIGN, Double::sum,
            TokenType.MINUS_ASSIGN, (a, b) -> a - b,
            TokenType.ASSIGN, Double::sum
    );
}
