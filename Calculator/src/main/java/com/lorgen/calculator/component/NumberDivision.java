package com.lorgen.calculator.component;

import com.lorgen.calculator.exception.CalculationException;

/**
 * To maintain accuracy we may in some cases want to use this class
 * over the default {@link Number} class and performing the division
 * when we need it.
 */
public class NumberDivision extends Number {

    public static Number getExactDivision(Number dividend, Number divisor) throws CalculationException {
        double divided = dividend.asDouble() / divisor.asDouble();
        if (Math.round(divided) == divided) {
            return new Number(divided);
        }

        return new NumberDivision(dividend, divisor);
    }

    private Number dividend;
    private Number divisor;

    public NumberDivision(Number dividend, Number divisor) {
        super(0); // This value doesn't matter since we override all relevant methods
        this.dividend = dividend;
        this.divisor = divisor;
    }

    @Override
    public void multiply(Number number) throws CalculationException {
        if (number instanceof NumberDivision) {
            NumberDivision division = (NumberDivision) number;

            return;
        }
    }

    @Override
    public void multiply(double d) throws CalculationException {
        this.dividend.multiply(d);
    }

    @Override
    public void divide(Number number) throws CalculationException {
        if (number instanceof NumberDivision) {
            NumberDivision division = (NumberDivision) number;
            this.divisor.multiply(division.dividend);
            this.dividend.multiply(division.divisor);
            return;
        }

        this.divide(number.asDouble());
    }

    @Override
    public void divide(double d) throws CalculationException {
        this.divisor.multiply(d);
    }

    @Override
    public void add(Number number) throws CalculationException {
        this.dividend.add(number.asDouble() * this.divisor.asDouble());
    }

    @Override
    public void add(double d) throws CalculationException {
        this.dividend.add(d * this.divisor.asDouble());
    }

    @Override
    public void subtract(Number number) throws CalculationException {
        this.dividend.subtract(number.asDouble() * this.divisor.asDouble());
    }

    @Override
    public void subtract(double d) throws CalculationException {
        this.dividend.subtract(d * this.divisor.asDouble());
    }

    @Override
    public double asDouble() throws CalculationException {
        if (this.divisor.isZero()) {
            throw new CalculationException("Division by zero!");
        }

        return this.dividend.asDouble() / this.divisor.asDouble();
    }

    @Override
    public void setValue(double value) throws CalculationException {
        this.dividend.setValue(value);
        this.divisor.setValue(1);
    }

    public Number getDividend() {
        return dividend;
    }

    public Number getDivisor() {
        return divisor;
    }

    @Override
    protected Number clone() {
        return new NumberDivision(this.dividend, this.divisor);
    }
}
