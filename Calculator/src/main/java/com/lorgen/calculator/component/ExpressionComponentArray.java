package com.lorgen.calculator.component;

import java.util.Arrays;
import java.util.Objects;

public class ExpressionComponentArray implements ExpressionComponent {

    private ExpressionComponent[] components;

    public ExpressionComponentArray(ExpressionComponent... components) {
        this.components = components;
    }

    @Override
    public String asString() {
        return this.toString();
    }

    public ExpressionComponent[] getComponents() {
        return components;
    }

    public int getLength() {
        return this.components.length;
    }

    public ExpressionComponent getAt(int i) {
        return this.components[i];
    }

    public void set(int index, ExpressionComponent component) {
        this.components[index] = component;
    }

    public void removeSurrounding(int index) {
        this.remove(index - 1, index + 1);
    }

    public void remove(int... indices) {
        for (int index : indices) {
            this.components[index] = null;
        }

        // Remove the element
        this.components = Arrays.stream(this.components)
          .filter(Objects::nonNull)
          .toArray(ExpressionComponent[]::new);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (ExpressionComponent component : this.components) {
            builder.append(component.asString());
        }

        return builder.toString();
    }
}
