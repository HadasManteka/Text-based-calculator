package calculator.expression.unary;

import calculator.lexer.TokenType;

import java.util.Map;
import java.util.function.Function;

public class UnaryOperatorFactory {

    public static Function<Double, Double> getUnaryOperator(TokenType type) {
        Function<Double, Double> op = UNARY_OPERATORS.get(type);
        if (op == null) {
            throw new IllegalArgumentException("Unknown unary operator: " + type);
        }
        return op;
    }

    private static final Map<TokenType, Function<Double, Double>> UNARY_OPERATORS = Map.of(
            TokenType.INCREMENT, a -> a+1,
            TokenType.DECREMENT, a -> a-1
    );
}
