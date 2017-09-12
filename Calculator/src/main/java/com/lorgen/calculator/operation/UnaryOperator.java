package com.lorgen.calculator.operation;

import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.ExpressionComponentArray;
import com.lorgen.calculator.exception.CalculationException;

import java.util.Arrays;

public enum UnaryOperator implements Operator {
    POSITIVE("+") {
        @Override
        public void calc(ExpressionComponentArray array, int position) throws CalculationException {
            try {
                // An operator such as this one simply doesn't do anything. We just subtract it
                // to make sure it doesn't cause us any trouble
                array.remove(position);
            } catch (Throwable t) {
                throw new CalculationException(t);
            }
        }
    },
    NEGATIVE("-") {
        @Override
        public void calc(ExpressionComponentArray array, int position) throws CalculationException {
            try {
                // This operator is the same as multiplying by -1
                this.reverse(array.getAt(position + 1));
                array.remove(position);
            } catch (Throwable t) {
                if (t instanceof CalculationException) {
                    throw t;
                }

                throw new CalculationException(t);
            }
        }

        private void reverse(ExpressionComponent component) throws CalculationException {
            if (component.hasCoefficient()) {
                component.getCoefficient().multiply(-1);
                return;
            }

            if (component instanceof ExpressionComponentArray) {
                for (ExpressionComponent other : ((ExpressionComponentArray) component).getComponents()) {
                    this.reverse(other);
                }

                return;
            }

            throw new CalculationException("Couldn't reverse component!");
        }
    };

    private String delimiter;

    UnaryOperator(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String asString() {
        return this.delimiter;
    }

    @Override
    public String getDelimiter() {
        return this.delimiter;
    }

    public static UnaryOperator fromString(String string) {
        return Arrays.stream(values()).filter(value -> value.getDelimiter().equalsIgnoreCase(string)).findFirst().orElse(null);
    }
}
