package com.lorgen.calculator.component;

import com.lorgen.calculator.exception.CalculationException;

@AssumedMultiplication
public class Function implements NamedComponent {

    private String name;
    private String[] variables;
    private ExpressionComponent function;

    public Function(String name, String[] variables, ExpressionComponent function) {
        this.name = name;
        this.variables = variables;
        this.function = function;
    }

    public String[] getVariables() {
        return variables;
    }

    @Override
    public String getName() {
        return name;
    }

    public ExpressionComponent getFunction() {
        return function;
    }

    public Number run(Number... values) throws CalculationException {
        if (values.length != this.getVariables().length) {
            throw new CalculationException("Missing arguments!");
        }

        // Define variables
        for (int i = 0; i < values.length; i++) {
            String name = this.variables[i];
            Number value = values[i];
            ThreadLocalVariables.current().setValue(name, value);
        }

        // TODO

        return null;
    }

    // Delegate to underlying expression

    @Override
    public boolean isNumber() {
        return this.function.isNumber();
    }

    @Override
    public Number asNumber() {
        return this.function.asNumber();
    }

    @Override
    public boolean hasAssociatedNumber() {
        return this.function.hasAssociatedNumber();
    }

    @Override
    public Number getAssociatedNumber() {
        return this.function.getAssociatedNumber();
    }

    @Override
    public boolean hasCoefficient() {
        return this.function.hasCoefficient();
    }

    @Override
    public Number getCoefficient() {
        return this.function.getCoefficient();
    }

    @Override
    public String asString() {
        return this.name + "(" + String.join(", ", this.variables) + ") = " + this.function.asString();
    }
}
