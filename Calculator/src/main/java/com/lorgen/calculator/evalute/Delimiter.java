package com.lorgen.calculator.evalute;

import java.util.regex.Pattern;

public interface Delimiter {
    String getDelimiter();

    default String getPatternQuote() {
        return Pattern.quote(this.getDelimiter());
    }
}
