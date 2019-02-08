package com.lorgen.calculator;

import com.lorgen.calculator.concurrency.Scheduler;
import com.lorgen.calculator.evalute.PatternCompiler;
import com.lorgen.calculator.session.Session;

public class Calculator {

    private static final Scheduler SCHEDULER = new Scheduler();
    private static final PatternCompiler COMPILER = new PatternCompiler();

    public static Scheduler getScheduler() {
        return SCHEDULER;
    }

    public static PatternCompiler getPattern() {
        return COMPILER;
    }

    // Might do something with thread
    public static Session getNewSession() {
        return new Session();
    }
}
