package com.lorgen.calculator.component.operation;

import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.Number;
import com.lorgen.calculator.component.UnorderedListComponent;
import com.lorgen.calculator.exception.CalculationException;

public class Subtraction extends UnorderedListComponent {

    public Subtraction(ExpressionComponent... components) {
        super(components);
    }

    @Override
    public Number asNumber() throws CalculationException {
        Number number = this.getComponents().get(0).asNumber();
        for (int i = 1; i < this.getComponents().size(); i++) {
            number.subtract(this.getComponents().get(i).asNumber());
        }

        return number;
    }

    @Override
    protected char getOperator() {
        return '-';
    }
}
