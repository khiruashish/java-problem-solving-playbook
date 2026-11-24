package com.playbook.logical;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogicalBatch3Test {

    @Test void happyNumber() {
        List<Integer> happyUpTo50 = List.of(1, 7, 10, 13, 19, 23, 28, 31, 32, 44, 49);
        for (int n = 1; n <= 50; n++) {
            boolean want = happyUpTo50.contains(n);
            assertEquals(want, HappyNumber.hashSet(n), "n=" + n);
            assertEquals(want, HappyNumber.floyd(n), "n=" + n);
            assertEquals(want, HappyNumber.cycleThroughFour(n), "n=" + n);
            assertEquals(want, HappyNumber.recursive(n), "n=" + n);
        }
        for (int n = 1; n <= 20_000; n++) {
            boolean want = HappyNumber.hashSet(n);
            assertEquals(want, HappyNumber.floyd(n));
            assertEquals(want, HappyNumber.cycleThroughFour(n));
        }
        assertEquals(82, HappyNumber.next(19));
        assertFalse(HappyNumber.floyd(0));
        assertTrue(HappyNumber.floyd(Integer.MAX_VALUE) == HappyNumber.hashSet(Integer.MAX_VALUE));
    }

    @Test void romanNumerals() {
        assertEquals("III", RomanNumerals.toRoman(3));
        assertEquals("LVIII", RomanNumerals.toRoman(58));
        assertEquals("MCMXCIV", RomanNumerals.toRoman(1994));
        assertEquals("MMMCMXCIX", RomanNumerals.toRoman(3999));
        assertEquals(1994, RomanNumerals.fromRoman("MCMXCIV"));
        for (int n = 1; n <= 3999; n++) {
            String r = RomanNumerals.toRoman(n);
            assertEquals(r, RomanNumerals.toRomanByDigits(n));
            assertEquals(n, RomanNumerals.fromRoman(r));
            assertEquals(n, RomanNumerals.fromRomanRightToLeft(r));
        }
        assertThrows(IllegalArgumentException.class, () -> RomanNumerals.toRoman(0));
        assertThrows(IllegalArgumentException.class, () -> RomanNumerals.toRoman(4000));
        assertThrows(IllegalArgumentException.class, () -> RomanNumerals.fromRoman("XB"));
    }

    @Test void excelColumn() {
        String[] titles = {"A", "Z", "AA", "AZ", "BA", "ZY", "ZZ", "AAA", "FXSHRXW"};
        long[] numbers = {1, 26, 27, 52, 53, 701, 702, 703, 2147483647L};
        for (int i = 0; i < titles.length; i++) {
            assertEquals(numbers[i], ExcelColumn.toNumber(titles[i]));
            assertEquals(titles[i], ExcelColumn.toTitle(numbers[i]));
            assertEquals(titles[i], ExcelColumn.toTitleRecursive(numbers[i]));
        }
        for (int n = 1; n <= 20_000; n++) {
            String t = ExcelColumn.toTitle(n);
            assertEquals(n, ExcelColumn.toNumber(t));
            assertEquals(t, ExcelColumn.toTitleRecursive(n));
        }
        for (int n = 1; n <= 1000; n++) assertEquals(ExcelColumn.toTitleBrute(n), ExcelColumn.toTitle(n));
        assertThrows(IllegalArgumentException.class, () -> ExcelColumn.toTitle(0));
        assertThrows(IllegalArgumentException.class, () -> ExcelColumn.toNumber("a"));
    }
}
