package com.lorgen.calculator.component.operation;

import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.Number;
import com.lorgen.calculator.component.UnorderedListComponent;
import com.lorgen.calculator.exception.CalculationException;

public class Addition extends UnorderedListComponent {

    public Addition(ExpressionComponent... components) {
        super(components);
    }

    @Override
    public Number asNumber() throws CalculationException {
        Number number = new Number(0);
        for (ExpressionComponent component : this.getComponents()) {
            number.add((Number) component);
        }

        return number;
    }

    @Override
    protected char getOperator() {
        return '+';
    }
}
