package com.lorgen.calculator.component.operation;

import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.Number;
import com.lorgen.calculator.exception.CalculationException;

public class Exponent implements ExpressionComponent {

    private ExpressionComponent base;
    private ExpressionComponent power;

    public Exponent(ExpressionComponent base, ExpressionComponent power) {
        this.base = base;
        this.power = power;
    }

    public ExpressionComponent getBase() {
        return base;
    }

    public ExpressionComponent getPower() {
        return power;
    }

    @Override
    public boolean isNumber() {
        return this.getBase().isNumber() && this.getPower().isNumber();
    }

    @Override
    public Number asNumber() throws CalculationException {
        return new Number(Math.pow(this.getBase().asNumber().asDouble(), this.getPower().asNumber().asDouble()));
    }

    @Override
    public String asString() {
        return this.getBase().asString() + "^" + this.getPower().asString();
    }
}
