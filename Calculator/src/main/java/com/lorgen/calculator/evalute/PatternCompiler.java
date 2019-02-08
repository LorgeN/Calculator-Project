package com.lorgen.calculator.evalute;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.lorgen.calculator.component.operator.UnaryOperator;
import com.lorgen.calculator.component.operator.BinaryOperator;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class PatternCompiler {

    private List<Delimiter> delimiterList;
    private Pattern pattern;

    public PatternCompiler() {
        this.delimiterList = Lists.newArrayList();

        Collections.addAll(this.delimiterList, BinaryOperator.values());
        Collections.addAll(this.delimiterList, UnaryOperator.values());

        StringBuilder patternBuilder = new StringBuilder("\\d+\\.?\\d*|[a-z]");
        this.delimiterList.forEach(delimiter -> patternBuilder.append("|").append(delimiter.getPatternQuote()));
        this.pattern = Pattern.compile(patternBuilder.toString());
    }

    public List<Delimiter> getDelimiterList() {
        return ImmutableList.copyOf(this.delimiterList);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
