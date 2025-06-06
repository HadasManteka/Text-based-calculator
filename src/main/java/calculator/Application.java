package calculator;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Text-Based Calculator");
        System.out.println("Enter line expression. Type 'exit' to quit.");
        System.out.println("Example: i = 0; j = ++i; x = i++ + 5; y = 5 + 3 * 10; i += y");
        System.out.println("------------------------------------------------------------------");

        while (true) {
            System.out.print("> ");
            String inputLine = scanner.nextLine();

            if (inputLine.trim().equalsIgnoreCase("exit")) {
                break;
            }
            if (inputLine.trim().isEmpty()) {
                continue;
            }

            try {
                calculator.evaluateLine(inputLine);
//                System.out.println("  Expression evaluated successfully.");
//            } catch (Exception e) {
//                System.err.println("  Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("  An unexpected error occurred: " + e.getMessage());
            }

            calculator.printAllVariables();
        }

        System.out.println("\nFinal Variable Values:");
        calculator.printAllVariables();
        System.out.println("Exiting calculator. Goodbye!");
        scanner.close();
    }
}
