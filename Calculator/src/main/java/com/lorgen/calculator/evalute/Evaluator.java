package com.lorgen.calculator.evalute;

import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.exception.EvalutationException;

public interface Evaluator {
    ExpressionComponent evaluate(String string) throws EvalutationException;
}
