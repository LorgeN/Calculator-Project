package com.lorgen.calculator.component;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

@AssumedMultiplication
public class ExpressionComponentArray implements ExpressionComponent, Iterable<ExpressionComponent> {

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

    public void add(ExpressionComponent... components) {
        this.components = Arrays.copyOf(this.components, this.components.length + components.length);
        System.arraycopy(components, 0, this.components, this.components.length - components.length, components.length);
    }

    @Override
    public Iterator<ExpressionComponent> iterator() {
        return Lists.newArrayList(this.components).iterator();
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
