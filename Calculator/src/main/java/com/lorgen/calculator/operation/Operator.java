package com.lorgen.calculator.operation;

import com.lorgen.calculator.exception.CalculationException;
import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.ExpressionComponentArray;
import com.lorgen.calculator.evalute.Delimiter;

public interface Operator extends ExpressionComponent, Delimiter {

    void calc(ExpressionComponentArray array, int position) throws CalculationException;
}
