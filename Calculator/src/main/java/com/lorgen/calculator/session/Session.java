package com.lorgen.calculator.session;

import com.google.common.collect.Maps;
import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.NamedComponent;

import java.util.Map;

public class Session {

    private static final Map<Long, Session> THREAD_SESSIONS = Maps.newConcurrentMap();

    public static Session current() {
        return THREAD_SESSIONS.get(Thread.currentThread().getId());
    }

    private Map<String, ExpressionComponent> componentMap;

    private SessionThread thread;
    private long threadId = -1;
    private char naming;

    public Session() {
        this.componentMap = Maps.newHashMap();
        this.naming = 'a';

        this.thread = new SessionThread(this);
        this.thread.start();
    }

    public char getNextName() {
        return this.naming++;
    }

    public void evaluate(String string) {

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

    public void setThreadId(long threadId) {
        if (this.threadId != -1) {
            THREAD_SESSIONS.remove(this.threadId);
        }

        this.threadId = threadId;
        THREAD_SESSIONS.put(this.threadId, this);
    }

    public void undefineThread() {
        THREAD_SESSIONS.remove(this.threadId);
        this.threadId = -1;
    }
}
