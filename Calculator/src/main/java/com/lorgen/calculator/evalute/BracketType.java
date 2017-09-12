package com.lorgen.calculator.evalute;

import com.google.common.collect.Lists;
import com.lorgen.calculator.util.UtilString;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public enum BracketType {
    PARANTHESES('(', ')'),
    SQUARE('[', ']'),
    CURLY('{', '}');

    private char opening;
    private char closing;

    BracketType(char opening, char closing) {
        this.opening = opening;
        this.closing = closing;
    }

    public char getOpening() {
        return opening;
    }

    public char getClosing() {
        return closing;
    }

    public boolean isBalanced(String string) {
        Deque<Character> deque = new ArrayDeque<>();

        for (char ch : string.toCharArray()) {
            if (ch == this.getOpening()) {
                deque.addFirst(ch);
                continue;
            }

            // We don't care about this char
            if (ch != this.getClosing()) {
                continue;
            }

            // No opening = unbalanced
            if (deque.isEmpty()) {
                return false;
            }

            // The last bracket wasn't opening
            if (deque.removeFirst() != this.getOpening()) {
                return false;
            }
        }

        return deque.isEmpty();
    }

    public RawSection[] getSections(String string) {
        Deque<Character> deque = new ArrayDeque<>();
        List<RawSection> pairs = Lists.newLinkedList();

        char[] chars = string.toCharArray();

        int last = -1;
        int opening = -1;
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (ch == this.getOpening()) {
                if (deque.isEmpty()) {
                    opening = i;
                }

                deque.addFirst(ch);
                continue;
            }

            // We don't care about this char
            if (ch != this.getClosing()) {
                continue;
            }

            // No opening = unbalanced
            if (deque.isEmpty()) {
                throw new IllegalArgumentException("Not balanced!");
            }

            // The last bracket wasn't opening
            if (deque.removeFirst() != this.getOpening()) {
                throw new IllegalArgumentException("Not balanced!");
            }

            // There is something between the brackets
            if (last != -1) {
                String between = UtilString.getRange(last, opening, chars);
                RawSection section = new RawSection(last, opening, between);
                pairs.add(section);
            }

            String value = UtilString.getRange(opening + 1, i, chars);
            RawSection pair = new RawSection(opening + 1, i, value);

            opening = -1;
            last = i + 1;

            pairs.add(pair);
        }

        // Make sure we get all of the expression
        if (last != string.length()) {
            // If the string contained none of this bracket type
            last = last == -1 ? 0 : last;

            String ending = UtilString.getRange(last, string.length(), chars);
            RawSection section = new RawSection(last, string.length(), ending);
            pairs.add(section);
        }

        return pairs.toArray(new RawSection[pairs.size()]);
    }
}
