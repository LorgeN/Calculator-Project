package com.lorgen.calculator.component;

import com.lorgen.calculator.component.operator.Operator;
import com.lorgen.calculator.exception.CalculationException;

import java.util.Optional;

public interface ExpressionComponent {

    String asString();

    default Optional<ExpressionComponent> compute() throws CalculationException {
        return Optional.of(this);
    }

    default boolean assumeMultiplication() throws CalculationException {
        return this.getClass().getAnnotation(AssumedMultiplication.class) != null;
    }

    default boolean isOperator() throws CalculationException {
        return this instanceof Operator;
    }

    default Operator asOperator() throws CalculationException {
        return (Operator) this;
    }

    default boolean isNumber() throws CalculationException {
        return this instanceof Number;
    }

    default Number asNumber() throws CalculationException {
        return (Number) this;
    }

    default boolean hasAssociatedNumber() throws CalculationException {
        return this.isNumber() || this.hasCoefficient();
    }

    default Number getAssociatedNumber() throws CalculationException {
        if (this.isNumber()) {
            return this.asNumber();
        }

        return this.getCoefficient();
    }

    default boolean hasCoefficient() throws CalculationException {
        return !this.isOperator() && !this.isNumber();
    }

    default Number getCoefficient() throws CalculationException {
        if (!this.hasCoefficient()) {
            throw new UnsupportedOperationException("Component doesn't have a coefficient");
        }

        throw new UnsupportedOperationException("Component has coefficient but doesn't override method!");
    }
}
