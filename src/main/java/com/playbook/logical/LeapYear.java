package com.playbook.logical;

import java.time.Year;

/** Gregorian leap year: divisible by 4, except centuries, except centuries divisible by 400. */
public final class LeapYear {
    private LeapYear() {}

    /** Nested ifs — mirrors how the rule is usually stated. */
    public static boolean nested(int year) {
        if (year % 400 == 0) return true;
        if (year % 100 == 0) return false;
        return year % 4 == 0;
    }

    /** One boolean expression. */
    public static boolean expression(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /** Production: let the JDK decide (java.time). */
    public static boolean library(int year) {
        return Year.isLeap(year);
    }
}
