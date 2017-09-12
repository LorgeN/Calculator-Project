package com.lorgen.calculator.component;

import com.lorgen.calculator.exception.CalculationException;
import com.lorgen.calculator.operation.Operator;

public interface ExpressionComponent {

    String asString();

    default ExpressionComponent abbreviate() {
        return this;
    }

    default boolean assumeMultiplication() {
        return this.getClass().getAnnotation(AssumedMultiplication.class) != null;
    }

    default boolean isOperator() {
        return this instanceof Operator;
    }

    default Operator asOperator() {
        return (Operator) this;
    }

    default boolean isNumber() {
        return this instanceof Number;
    }

    default Number asNumber() throws CalculationException {
        return (Number) this;
    }

    default boolean hasAssociatedNumber() {
        return this.isNumber() || this.hasCoefficient();
    }

    default Number getAssociatedNumber() {
        if (this.isNumber()) {
            return this.asNumber();
        }

        return this.getCoefficient();
    }

    default boolean hasCoefficient() {
        return !this.isOperator() && !this.isNumber();
    }

    default Number getCoefficient() {
        if (!this.hasCoefficient()) {
            throw new UnsupportedOperationException("Component doesn't have a coefficient");
        }

        throw new UnsupportedOperationException("Component has coefficient but doesn't override method!");
    }
}
