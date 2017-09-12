package com.lorgen.calculator.component;

import com.lorgen.calculator.component.operation.Multiplication;
import com.lorgen.calculator.exception.CalculationException;
import com.lorgen.calculator.util.UtilNumber;

public class Number implements ExpressionComponent {

    private double value;

    public Number(double value) {
        this.value = value;
    }

    public boolean isZero() {
        return this.value == 0;
    }

    public void setValue(double value) throws CalculationException {
        this.value = value;
    }

    public void multiply(Number number) throws CalculationException {
        this.multiply(number.value);
    }

    public void multiply(double d) throws CalculationException {
        this.value *= d;
    }

    public void divide(Number number) throws CalculationException {
        this.divide(number.value);
    }

    public void divide(double d) throws CalculationException {
        this.value /= d;
    }

    public void add(Number number) throws CalculationException {
        this.add(number.value);
    }

    public void add(double d) throws CalculationException {
        this.value += d;
    }

    public void subtract(Number number) throws CalculationException {
        this.subtract(number.value);
    }

    public void subtract(double d) throws CalculationException {
        this.value -= d;
    }

    public boolean isInteger() throws CalculationException {
        return UtilNumber.isInteger(this.asDouble());
    }

    public boolean isLong() throws CalculationException {
        return UtilNumber.isLong(this.asDouble());
    }

    public Number[] findFactors() throws CalculationException {
        if (!this.isLong()) {
            throw new CalculationException("This number isn't whole!");
        }

        Long[] factors = UtilNumber.factorize(this.asLong());
        Number[] wrapped = new Number[factors.length];
        for (int i = 0; i < factors.length; i++) {
            wrapped[i] = new Number(factors[i]);
        }

        return wrapped;
    }

    public ExpressionComponent findFactorComponent() throws CalculationException {
        return new Multiplication(this.findFactors());
    }

    public boolean isPrime() throws CalculationException {
        if (!this.isLong()) {
            throw new CalculationException("This number isn't whole!");
        }

        return UtilNumber.isPrime(this.asLong());
    }

    public long asLong() throws CalculationException {
        return (long) this.asDouble();
    }

    public int asInteger() throws CalculationException {
        return (int) this.asDouble();
    }

    public double asDouble() throws CalculationException {
        return value;
    }

    @Override
    public String asString() {
        try {
            return String.valueOf(this.asDouble());
        } catch (CalculationException e) {
            return "ERROR";
        }
    }
}
