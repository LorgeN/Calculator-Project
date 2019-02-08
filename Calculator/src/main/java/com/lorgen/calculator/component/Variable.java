package com.lorgen.calculator.component;

import com.lorgen.calculator.exception.CalculationException;
import com.lorgen.calculator.session.Session;

import java.util.Objects;

@AssumedMultiplication
public class Variable implements ExpressionComponent {

    private static final boolean DO_NEGATIVE_EXPONENT = true;

    private String name;
    private int exponent;
    private Number coefficient;

    public Variable(String name) {
        this.name = name;
        this.exponent = 1;
        this.coefficient = new Number(1.0);
    }

    public String getName() {
        return name;
    }

    @Override
    public String asString() {
        return this.getName();
    }

    @Override
    public boolean isNumber() {
        Number number = Session.current().getComponent(this.getName(), Number.class);
        return number != null || this.exponent == 0 || this.coefficient.isZero();
    }

    @Override
    public Number asNumber() throws CalculationException {
        if (this.coefficient.isZero()) {
            return new Number(0.0);
        }

        if (this.exponent == 0) {
            return this.coefficient.clone();
        }

        // Clone to avoid modifying definition
        return Session.current().getComponent(this.getName(), Number.class).clone();
    }

    @Override
    public Number getCoefficient() {
        return this.coefficient;
    }

    public int getExponent() {
        return exponent;
    }

    public void addDegree(int amount) {
        this.exponent += amount;
    }

    public void removeDegree(int amount) {
        this.exponent -= amount;
    }

    public boolean isSame(Variable variable) {
        return Objects.equals(variable.getName(), this.getName());
    }

    public boolean canAddSubtract(Variable variable) {
        return this.isSame(variable) && this.getExponent() == variable.getExponent();
    }

    public boolean canMultiplyDivide(Variable variable) {
        return this.isSame(variable);
    }

    public boolean add(Variable variable) throws CalculationException {
        if (!Objects.equals(variable.getName(), this.getName())) {
            throw new CalculationException("Can't add differently named variable to this!");
        }

        if (this.getExponent() != variable.getExponent()) {
            throw new CalculationException("Can't add variable of different degree to this one!");
        }

        this.getCoefficient().add(variable.getCoefficient());
        return true;
    }

    public boolean subtract(Variable variable) throws CalculationException {
        if (!Objects.equals(variable.getName(), this.getName())) {
            throw new CalculationException("Can't subtract differently named variable to this!");
        }

        if (this.getExponent() != variable.getExponent()) {
            throw new CalculationException("Can't subtract variable of different degree to this one!");
        }

        this.getCoefficient().subtract(variable.getCoefficient());
        return true;
    }

    public boolean multiply(Variable variable) throws CalculationException {
        if (!Objects.equals(variable.getName(), this.getName())) {
            throw new CalculationException("Can't subtract differently named variable to this!");
        }

        Number coefficient = variable.getCoefficient();
        this.getCoefficient().multiply(coefficient);

        this.exponent += variable.exponent;
        return true;
    }

    public boolean divide(Variable variable) throws CalculationException {
        if (!Objects.equals(variable.getName(), this.getName())) {
            throw new CalculationException("Can't subtract differently named variable to this!");
        }

        Number otherCoefficient = variable.getCoefficient();
        Number localCoefficient = this.getCoefficient();
        this.coefficient = NumberDivision.getExactDivision(localCoefficient, otherCoefficient);

        if (DO_NEGATIVE_EXPONENT || this.exponent >= variable.exponent) {
            this.exponent -= variable.exponent;
            return true;
        }

        variable.exponent -= this.exponent;
        this.exponent = 0;
        return false;
    }
}
