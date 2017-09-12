package com.lorgen.calculator.util;

public class UtilString {

    public static String getRange(int start, int end, char... chars) {
        if (chars.length < end) {
            throw new IllegalArgumentException("End is too high!");
        }

        if (start >= end) {
            throw new IllegalArgumentException("Start is greater than or equal to end!");
        }

        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            builder.append(chars[i]);
        }

        return builder.toString();
    }
}
