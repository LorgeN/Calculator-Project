package com.lorgen.calculator.component.operation;

import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.Number;
import com.lorgen.calculator.exception.CalculationException;

public class Modulo implements ExpressionComponent {

    private ExpressionComponent dividend;
    private ExpressionComponent divisor;

    public Modulo(ExpressionComponent dividend, ExpressionComponent divisor) {
        this.dividend = dividend;
        this.divisor = divisor;
    }

    public ExpressionComponent getDividend() {
        return dividend;
    }

    public ExpressionComponent getDivisor() {
        return divisor;
    }

    @Override
    public boolean isNumber() throws CalculationException {
        return this.getDividend().isNumber() && this.getDivisor().isNumber();
    }

    @Override
    public Number asNumber() throws CalculationException {
        return new Number(Math.floorMod(this.getDividend().asNumber().asLong(), this.getDivisor().asNumber().asLong()));
    }

    @Override
    public String asString() {
        return this.getDividend().asString() + " mod " + this.getDivisor().asString();
    }
}
