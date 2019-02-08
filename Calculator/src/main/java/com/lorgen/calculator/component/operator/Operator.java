package com.lorgen.calculator.component.operator;

import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.ExpressionComponentArray;
import com.lorgen.calculator.evalute.Delimiter;
import com.lorgen.calculator.exception.CalculationException;

public interface Operator extends ExpressionComponent, Delimiter {

    boolean split();

    OperatorPriority getPriority();

    void calc(ExpressionComponentArray array, int position) throws CalculationException;
}
