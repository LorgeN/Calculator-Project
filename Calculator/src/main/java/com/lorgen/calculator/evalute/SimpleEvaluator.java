package com.lorgen.calculator.evalute;

import com.lorgen.calculator.Calculator;
import com.lorgen.calculator.component.ExpressionComponent;
import com.lorgen.calculator.component.ExpressionComponentArray;
import com.lorgen.calculator.component.Number;
import com.lorgen.calculator.component.Variable;
import com.lorgen.calculator.exception.EvalutationException;
import com.lorgen.calculator.component.operator.BinaryOperator;
import com.lorgen.calculator.component.operator.Operator;
import com.lorgen.calculator.component.operator.UnaryOperator;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LinkedMap;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleEvaluator implements Evaluator {

    @Override
    public ExpressionComponent evaluate(String string) throws EvalutationException {
        if (!BracketType.PARANTHESES.isBalanced(string)) {
            throw new EvalutationException("Unbalanced parentheses!");
        }

        RawSection[] sections = BracketType.PARANTHESES.getSections(string);

        ExpressionComponentArray array = new ExpressionComponentArray();
        for (int i = 0; i < sections.length; i++) {
            array.add(this.evaluate(sections, i));
        }

        return array;
    }

    public ExpressionComponent evaluate(RawSection[] sections, int position) throws EvalutationException {
        RawSection section = sections[position];

        String sectionString = section.getRawContents();

        Pattern pattern = Calculator.getPattern().getPattern();
        Matcher matcher = pattern.matcher(sectionString);

        LinkedMap<Integer, Integer> objectIndexes = new LinkedMap<>();
        while (matcher.find()) {
            // Make sure we get all of it
            if (objectIndexes.size() != 0) {
                int lastValue = objectIndexes.get(objectIndexes.lastKey());

                if (lastValue != matcher.start()) {
                    objectIndexes.put(lastValue, matcher.start());
                }
            } else if (matcher.start() != 0) {
                objectIndexes.put(0, matcher.start());
            }

            objectIndexes.put(matcher.start(), matcher.end());
        }

        int lastValue = objectIndexes.get(objectIndexes.lastKey());
        if (lastValue != sectionString.length()) {
            objectIndexes.put(lastValue, sectionString.length());
        }

        if (objectIndexes.size() == 1) {
            boolean hasPrevious = position > 1 && BinaryOperator.fromString(sections[position - 1].getRawContents()) == null;
            boolean hasFollowing = sections.length > (position + 1);

            return this.getComponent(sectionString, hasFollowing, hasPrevious)
              .orElseThrow(() -> new EvalutationException("Unable to evaluate component for \"" + sectionString + "\"!"));
        }

        ExpressionComponentArray array = new ExpressionComponentArray();

        MapIterator<Integer, Integer> iterator = objectIndexes.mapIterator();

        ExpressionComponent previous = null;
        while (iterator.hasNext()) {
            iterator.next();

            String string = sectionString.substring(iterator.getKey(), iterator.getValue());

            boolean hasPrevious = previous != null && !(previous instanceof Operator);
            boolean hasFollowing = iterator.hasNext();

            ExpressionComponent component = previous = this.getComponent(string, hasFollowing, hasPrevious)
              .orElseThrow(() -> new EvalutationException("Unable to evaluate component for \"" + sectionString + "\"!"));

            array.add(component);
        }

        return array;
    }

    public Optional<ExpressionComponent> getComponent(String string, boolean hasFollowing, boolean hasPrevious) {
        if (string.matches("\\d+\\.?\\d*")) {
            return Optional.of(new Number(Double.parseDouble(string)));
        }

        if (hasFollowing && hasPrevious) {
            BinaryOperator operator = BinaryOperator.fromString(string);

            if (operator != null) {
                return Optional.of(operator);
            }
        }

        if (!hasPrevious && hasFollowing) {
            UnaryOperator operator = UnaryOperator.fromString(string);

            if (operator != null) {
                return Optional.of(operator);
            }
        }

        for (char ch : string.toCharArray()) {
            if (Character.isLetter(ch)) {
                continue;
            }

            return Optional.empty();
        }

        Variable variable = new Variable(string);
        return Optional.of(variable);
    }
}
