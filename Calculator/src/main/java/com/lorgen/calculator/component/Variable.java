package com.lorgen.calculator.component;

import com.lorgen.calculator.exception.CalculationException;

import java.util.Objects;

@AssumedMultiplication
public class Variable implements ExpressionComponent {

    private String name;
    private int degree;
    private Number coefficient;

    public Variable(String name) {
        this.name = name;
        this.degree = 1;
        this.coefficient = new Number(1.0);

        ThreadLocalVariables.current().add(name);
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
        return ThreadLocalVariables.current().hasValue(this.getName());
    }

    @Override
    public Number asNumber() {
        return new Number(this.getCoefficient().asDouble() * ThreadLocalVariables.current().getValue(this.getName()).asDouble());
    }

    @Override
    public Number getCoefficient() {
        return this.coefficient;
    }

    public int getDegree() {
        return degree;
    }

    public void addDegree(int amount) {
        this.degree += amount;
    }

    public void removeDegree(int amount) {
        this.degree -= amount;
    }

    public boolean canAdd(Variable variable) {
        return Objects.equals(variable.getName(), this.getName()) && this.getDegree() == variable.getDegree();
    }

    public void add(Variable variable) throws CalculationException {
        if (!Objects.equals(variable.getName(), this.getName())) {
            throw new CalculationException("Can't add differently named variable to this!");
        }

        if (this.getDegree() != variable.getDegree()) {
            throw new CalculationException("Can't add variable of different degree to this one!");
        }

        this.getCoefficient().add(variable.getCoefficient());
        variable.getCoefficient().setValue(0); // This way we make sure to avoid any potential issues
    }
}
