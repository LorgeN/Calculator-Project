package com.lorgen.terminal;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.session.WorkSession;

public class JavaLauncher {

    private static WorkSession session;

    public static void main(String[] args) {
        session = Calculator.getNewSession();
        System.out.println("New session created!");

        // TODO: Terminal UI
    }
}
