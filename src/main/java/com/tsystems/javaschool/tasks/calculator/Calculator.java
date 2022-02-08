package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.Stack;

public class Calculator {

    private Stack<Double> numbers = new Stack<>();
    private Stack<String> operators = new Stack<>();

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        if (statement == null || statement.isEmpty()) {
            return null;
        }
        MathStatementIterator mathStatementIterator = new MathStatementIterator(statement.replaceAll("\\s", ""));
        while (mathStatementIterator.hasNext()) {
            String numberOrOperand = mathStatementIterator.next();
            try {
                numbers.push(Double.parseDouble(numberOrOperand));
                if (!mathStatementIterator.hasNext()) {
                    while (!operators.isEmpty()) {
                        calculate();
                    }
                }
            } catch (NumberFormatException exception) {
                try {
                    if (!numberOrOperand.equals("(") && !numberOrOperand.equals(")")
                            && !numberOrOperand.equals("*") && !numberOrOperand.equals("/")
                            && !numberOrOperand.equals("+") && !numberOrOperand.equals("-")) {
                        return null;
                    } else if (numberOrOperand.equals("(")) {
                        operators.push(numberOrOperand);
                    } else if (!numberOrOperand.equals(")") && operators.empty()) {
                        operators.push(numberOrOperand);
                    } else if (!numberOrOperand.equals(")") && !operators.peek().equals("(")
                            && getPriority(numberOrOperand) <= getPriority(operators.peek())) {
                        calculate();
                        operators.push(numberOrOperand);
                    } else if (!numberOrOperand.equals(")") && !operators.peek().equals("(")
                            && getPriority(numberOrOperand) > getPriority(operators.peek())) {
                        operators.push(numberOrOperand);
                    } else if (!numberOrOperand.equals(")") && operators.peek().equals("(")) {
                        operators.push(numberOrOperand);
                    } else if (numberOrOperand.equals(")") && (operators.peek().equals("*")
                            || operators.peek().equals("/"))) {
                        calculate();
                        while (!operators.peek().equals("(")) {
                            calculate();
                        }
                        operators.pop();
                    } else if (numberOrOperand.equals(")") && (operators.peek().equals("+")
                            || operators.peek().equals("-"))) {
                        while (!operators.peek().equals("(")) {
                            calculate();
                        }
                        operators.pop();
                    }
                } catch (Exception ex) {
                    return null;
                }
            }
        }
        if (operators.search("(") != -1) {
            return null;
        }
        while (!operators.isEmpty()) {
            try {
                calculate();
            } catch (InputMismatchException ex) {
                return null;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.####");
        return decimalFormat.format(numbers.pop()).replace(",", ".");
    }

    private int getPriority(String operator) {
        int result = 0;
        switch (operator) {
            case ("+"):
                result = 1;
                break;
            case ("-"):
                result = 1;
                break;
            case ("*"):
                result = 2;
                break;
            case ("/"):
                result = 2;
        }
        return result;
    }

    private void calculate() throws EmptyStackException {
        String operator = operators.pop();
        Double n2 = numbers.pop();
        Double n1 = numbers.pop();
        Double result = null;

        switch (operator) {
            case "+":
                result = n1 + n2;
                break;
            case "-":
                result = n1 - n2;
                break;
            case "*":
                result = n1 * n2;
                break;
            case "/":
                if (n2 == 0.0) {
                    throw new InputMismatchException();
                }
                result = n1 / n2;
        }
        numbers.push(result);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.evaluate("(-100)+(-2)*(2+3*(4+5)-2*2)"));
    }

}
