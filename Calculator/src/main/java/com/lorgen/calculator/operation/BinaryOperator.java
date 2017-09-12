package com.lorgen.calculator.operation;

import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.ExpressionComponentArray;
import com.lorgen.calculator.component.operation.Addition;
import com.lorgen.calculator.component.operation.Division;
import com.lorgen.calculator.component.operation.Exponent;
import com.lorgen.calculator.component.operation.Modulo;
import com.lorgen.calculator.component.operation.Multiplication;
import com.lorgen.calculator.component.operation.Subtraction;
import com.lorgen.calculator.exception.CalculationException;

import java.util.Arrays;

public enum BinaryOperator implements Operator {
    EXPONENT("^") {
        @Override
        public ExpressionComponent getComponent(ExpressionComponent previous, ExpressionComponent following) {
            return new Exponent(previous, following);
        }
    },
    MODULO("%") {
        @Override
        public ExpressionComponent getComponent(ExpressionComponent previous, ExpressionComponent following) {
            return new Modulo(previous, following);
        }
    },
    PLUS("+") {
        @Override
        public ExpressionComponent getComponent(ExpressionComponent previous, ExpressionComponent following) {
            if (previous instanceof Addition) {
                ((Addition) previous).addComponent(following);
                return previous;
            }

            return new Addition(previous, following);
        }
    },
    MINUS("-") {
        @Override
        public ExpressionComponent getComponent(ExpressionComponent previous, ExpressionComponent following) {
            if (previous instanceof Subtraction) {
                ((Subtraction) previous).addComponent(following);
                return previous;
            }

            return new Subtraction(previous, following);
        }
    },
    MULTIPLICATION("*") {
        @Override
        public ExpressionComponent getComponent(ExpressionComponent previous, ExpressionComponent following) {
            if (previous instanceof Multiplication) {
                ((Multiplication) previous).addComponent(following);
                return previous;
            }

            return new Multiplication(previous, following);
        }
    },
    DIVISION("/") {
        @Override
        public ExpressionComponent getComponent(ExpressionComponent previous, ExpressionComponent following) {
            if (previous instanceof Division) {
                ((Division) previous).addComponent(following);
                return previous;
            }

            return new Division(previous, following);
        }
    };

    private String delimiter;

    BinaryOperator(String delimiter) {
        this.delimiter = delimiter;
    }


    @Override
    public String asString() {
        return delimiter;
    }

    @Override
    public String getDelimiter() {
        return delimiter;
    }

    @Override
    public void calc(ExpressionComponentArray array, int position) throws CalculationException {
        try {
            ExpressionComponent previous = array.getAt(position - 1);
            ExpressionComponent following = array.getAt(position + 1);

            ExpressionComponent component = this.getComponent(previous, following);

            array.set(position, component);
            array.removeSurrounding(position);
        } catch (Throwable t) {
            throw new CalculationException(t);
        }
    }

    public abstract ExpressionComponent getComponent(ExpressionComponent previous, ExpressionComponent following);

    public static BinaryOperator fromString(String string) {
        return Arrays.stream(values()).filter(value -> value.getDelimiter().equalsIgnoreCase(string)).findFirst().orElse(null);
    }
}
