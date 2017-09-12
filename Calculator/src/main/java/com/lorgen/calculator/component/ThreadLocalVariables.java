package com.lorgen.calculator.component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class ThreadLocalVariables {

    // 1 set of variables per thread to allow for multithreading in the future
    private static final Map<Long, ThreadLocalVariables> VARIABLES_MAP = Maps.newConcurrentMap();

    public static ThreadLocalVariables current() {
        return VARIABLES_MAP.computeIfAbsent(Thread.currentThread().getId(), id -> new ThreadLocalVariables());
    }

    public static void remove() {
        VARIABLES_MAP.remove(Thread.currentThread().getId());
    }

    private Map<String, Number> assignedValues;
    private List<String> existingValues;

    public ThreadLocalVariables() {
        this.assignedValues = Maps.newHashMap();
        this.existingValues = Lists.newArrayList();
    }

    public boolean exists(String variable) {
        return this.existingValues.contains(variable);
    }

    public void add(String variable) {
        this.existingValues.add(variable);
    }

    public boolean hasValue(String variable) {
        return this.assignedValues.containsKey(variable);
    }

    public void setValue(String variable, Number value) {

    }

    public Number getValue(String variable) {
        return this.assignedValues.get(variable);
    }
}
