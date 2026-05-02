package com.evo.points.calculator.utils;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validateNonNegative(int value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException(paramName + " cannot be negative: " + value);
        }
    }
}
