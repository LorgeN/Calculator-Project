package com.lorgen.calculator.session;

import com.google.common.collect.Maps;
import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.NamedComponent;

import java.util.Map;

public class WorkSession {

    private Map<String, ExpressionComponent> componentMap;

    public WorkSession() {
        this.componentMap = Maps.newHashMap();
    }

    public <T extends ExpressionComponent> T getComponent(String string, Class<T> type) {
        ExpressionComponent component = this.getComponent(string);
        if (component == null) {
            return null;
        }

        if (!type.isAssignableFrom(component.getClass())) {
            return null;
        }

        return (T) component;
    }

    public ExpressionComponent getComponent(String string) {
        return this.componentMap.get(string);
    }

    public void addComponent(NamedComponent component) {
        this.addComponent(component.getName(), component);
    }

    public void addComponent(String name, ExpressionComponent component) {
        this.componentMap.put(name, component);
    }
}
