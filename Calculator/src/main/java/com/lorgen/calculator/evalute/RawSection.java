package com.lorgen.calculator.evalute;

import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.ExpressionComponentArray;

public class RawSection {

    private int starting;
    private int ending;
    private String rawContents;

    public RawSection(int starting, int ending, String rawContents) {
        this.starting = starting;
        this.ending = ending;
        this.rawContents = rawContents;
    }

    public int getStarting() {
        return starting;
    }

    public int getEnding() {
        return ending;
    }

    public String getRawContents() {
        return rawContents;
    }

    public ExpressionComponentArray[] splitEval(CharSequence delimiter) {
        return null;
    }

    public ExpressionComponent eval() {
        return null;
    }
}
