package com.lorgen.terminal;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.session.Session;

public class JavaLauncher {

    private static Session session;

    public static void main(String[] args) {
        session = Calculator.getNewSession();
        System.out.println("New session created!");

        // TODO: Terminal UI
    }
}
