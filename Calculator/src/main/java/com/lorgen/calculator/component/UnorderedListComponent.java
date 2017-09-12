package com.lorgen.calculator.component;

import com.google.common.collect.Lists;
import com.lorgen.calculator.exception.CalculationException;

import java.util.List;
import java.util.stream.Collectors;

public abstract class UnorderedListComponent implements ExpressionComponent {

    private List<ExpressionComponent> components;

    public UnorderedListComponent(ExpressionComponent... components) {
        this.components = Lists.newArrayList(components);
    }


    public List<ExpressionComponent> getComponents() {
        return components;
    }

    public void addComponent(ExpressionComponent component) {
        this.components.add(component);
    }

    @Override
    public boolean isNumber() {
        return this.getComponents().stream().allMatch(ExpressionComponent::isNumber);
    }

    public abstract Number asNumber() throws CalculationException;

    @Override
    public String asString() {
        return String.join(" " + this.getOperator() + " ", this.getComponents().stream().map(ExpressionComponent::asString).collect(Collectors.toList()));
    }

    protected abstract char getOperator();
}
