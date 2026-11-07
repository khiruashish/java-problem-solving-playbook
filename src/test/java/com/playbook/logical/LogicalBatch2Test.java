package com.playbook.logical;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LogicalBatch2Test {
    private final Random rnd = new Random(303);

    @Test void perfectAndStrong() {
        for (int n = -2; n <= 10_000; n++) {
            boolean expected = PerfectStrongNumbers.isPerfectBrute(n);
            assertEquals(expected, PerfectStrongNumbers.isPerfect(n), "n=" + n);
            if (n < 2000) assertEquals(expected, PerfectStrongNumbers.isPerfectStreams(n));
            assertEquals(PerfectStrongNumbers.isStrong(n), PerfectStrongNumbers.isStrongRecursive(n));
        }
        assertTrue(PerfectStrongNumbers.isPerfect(8128));
        assertTrue(PerfectStrongNumbers.isPerfect(33_550_336));
        assertTrue(PerfectStrongNumbers.isStrong(145));
        assertTrue(PerfectStrongNumbers.isStrong(40585));
        assertFalse(PerfectStrongNumbers.isStrong(144));
    }

    @Test void leapYear() {
        for (int y = 1; y <= 4000; y++) {
            boolean expected = LeapYear.library(y);
            assertEquals(expected, LeapYear.nested(y), "year " + y);
            assertEquals(expected, LeapYear.expression(y));
        }
        assertTrue(LeapYear.expression(2000));
        assertFalse(LeapYear.expression(1900));
    }

    @Test void baseConversion() {
        assertEquals("1010", BaseConversion.toBase(10, 2));
        assertEquals("ff", BaseConversion.toBase(255, 16));
        assertEquals("-z", BaseConversion.toBase(-35, 36));
        assertEquals(255, BaseConversion.fromBase("FF", 16));
        assertEquals("377", BaseConversion.convert("ff", 16, 8));
        assertThrows(IllegalArgumentException.class, () -> BaseConversion.fromBase("12", 2));
        for (int t = 0; t < 2000; t++) {
            long n = rnd.nextLong() >> rnd.nextInt(60);
            int base = 2 + rnd.nextInt(35);
            String s = BaseConversion.toBase(n, base);
            assertEquals(Long.toString(n, base), s);
            assertEquals(s, BaseConversion.toBaseRecursive(n, base));
            assertEquals(n, BaseConversion.fromBase(s, base));
        }
    }

    @Test void patterns() {
        assertEquals(List.of("*", "**", "***"), PatternPrinting.rightTriangle(3));
        assertEquals(List.of("  *", " ***", "*****"), PatternPrinting.pyramid(3));
        assertEquals(List.of("  *", " ***", "*****", " ***", "  *"), PatternPrinting.diamond(3));
        assertEquals(List.of("1", "2 3", "4 5 6"), PatternPrinting.floydsTriangle(3));
        assertEquals(List.of(1L, 4L, 6L, 4L, 1L), PatternPrinting.pascal(5).get(4));
        assertEquals(List.of("***", "* *", "***"), PatternPrinting.hollowSquare(3));
        assertEquals(List.of("*"), PatternPrinting.hollowSquare(1));
        for (int k = 0; k < 40; k++) assertEquals(PatternPrinting.pascal(k + 1).get(k), PatternPrinting.pascalRow(k));
    }

    @Test void lcmOfRange() {
        assertEquals(2520, LcmOfRange.bruteForce(10));
        assertEquals(232_792_560L, LcmOfRange.iterative(20));
        for (int n = 1; n <= 16; n++) assertEquals(LcmOfRange.bruteForce(n), LcmOfRange.iterative(n));
        for (int n = 1; n <= 40; n++) assertEquals(BigInteger.valueOf(LcmOfRange.iterative(n)), LcmOfRange.primePowers(n));
        assertEquals(new BigInteger("69720375229712477164533808935312303556800"), LcmOfRange.primePowers(100));
    }
}
