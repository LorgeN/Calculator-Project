package com.lorgen.calculator;

import com.lorgen.calculator.session.WorkSession;

public class Calculator {

    // Might do something with thread
    public static WorkSession getNewSession() {
        return new WorkSession();
    }
}
