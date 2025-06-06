package calculator;
// Calculator.java

import calculator.expression.IExpression;
import calculator.lexer.Lexer;
import calculator.lexer.Token;

import java.util.*;

/**
 * The main calculator application class.
 * It orchestrates the Lexer, Parser, and Evaluator to process input lines.
 */
public class Calculator {
    private final Context context;

    public Calculator() {
        this.context = new Context();
    }

    /**
     * Processes a single line of input (an assignment expression).
     */
    public double evaluateLine(String line) {
        Lexer lexer = new Lexer(line);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        IExpression ast = parser.parse();
        return ast.evaluate(this.context);
    }

    public void printAllVariables() {
        Map<String, Double> symbolTable = context.getAll();
        if (symbolTable.isEmpty()) {
            System.out.println("No variables defined.");
            return;
        }

        System.out.println("\n--- Variable Values ---");
        symbolTable.forEach((name, value) ->
                System.out.println(name + " = " + value)
        );
        System.out.println("---------------------\n");
    }

    public double getVariable(String varName) {
        return context.get(varName);
    }
}

