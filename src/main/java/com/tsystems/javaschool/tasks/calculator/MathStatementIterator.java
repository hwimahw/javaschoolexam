package com.tsystems.javaschool.tasks.calculator;

import java.util.Iterator;

public class MathStatementIterator implements Iterator<String> {

    String mathStatement;
    int index;

    MathStatementIterator(String mathStatement) {
        this.mathStatement = mathStatement;
        index = 0;
    }

    @Override
    public String next() {
        StringBuilder numberOrOperand = new StringBuilder();
        char[] mathStatementArray = mathStatement.toCharArray();
        int flag = 0;
        for (int i = index; i < mathStatementArray.length; i++) {
            char element = mathStatement.charAt(index);
            if (Character.isDigit(element)) {
                numberOrOperand.append(element);
                index++;
            } else if (element == '.') {
                numberOrOperand.append(element);
                index++;
            } else if (index > 0 && element == '-' && mathStatement.charAt(index - 1) == '(') {
                flag = 1;
                numberOrOperand.append(element);
                index++;
            } else if (index < mathStatementArray.length - 1 && element == '(' && mathStatement.charAt(index + 1) == '-') {
                flag = 1;
                index++;
            } else if (flag == 1 && element == ')') {
                flag = 0;
                index++;
            } else if (numberOrOperand.length() != 0) {
                return numberOrOperand.toString();
            } else {
                index++;
                return numberOrOperand.append(element).toString();
            }
        }
        return numberOrOperand.toString();
    }

    @Override
    public boolean hasNext() {
        return mathStatement != null && !mathStatement.isEmpty()
                && index >= 0 && index < mathStatement.length();
    }
}
