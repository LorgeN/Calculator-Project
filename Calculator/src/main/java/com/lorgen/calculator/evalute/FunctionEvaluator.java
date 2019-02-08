package com.lorgen.calculator.evalute;

import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.Function;
import com.lorgen.calculator.exception.EvalutationException;

public class FunctionEvaluator implements Evaluator {

    public boolean isValidFunction(String string) {
        if (!string.contains("=")) {
            return false;
        }

        String[] parts = string.split("=");
        String naming = parts[0];
        return parts.length == 2 && BracketType.PARANTHESES.isBalanced(naming);
    }

    @Override
    public Function evaluate(String string) throws EvalutationException {
        if (!string.contains("=")) {
            throw new EvalutationException("Not a function (Doesn't contain =)");
        }

        String[] parts = string.split("=");
        if (parts.length != 2) {
            throw new EvalutationException("Weird function (Not 2 components in split array)");
        }

        String naming = parts[0];
        String functionRaw = parts[1];

        if (!BracketType.PARANTHESES.isBalanced(naming)) {
            throw new EvalutationException("Not a function (Unbalanced naming parentheses)");
        }

        int openingIndex = naming.indexOf('(');
        String name = naming.substring(0, openingIndex);
        String[] variables = name.substring(openingIndex + 1, name.indexOf(')')).split(", *");

        ExpressionComponent functionExpression = Evaluators.SIMPLE.evaluate(functionRaw);

        return new Function(name, variables, functionExpression);
    }
}
