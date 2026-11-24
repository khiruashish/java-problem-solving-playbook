package com.playbook.strings;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StringsBatch3Test {

    @Test void countAndSay() {
        String[] expected = {"1", "11", "21", "1211", "111221", "312211", "13112221", "1113213211"};
        for (int n = 1; n <= expected.length; n++) {
            assertEquals(expected[n - 1], CountAndSay.iterative(n));
            assertEquals(expected[n - 1], CountAndSay.recursive(n));
            assertEquals(expected[n - 1], CountAndSay.regex(n));
        }
        for (int n = 1; n <= 25; n++) assertEquals(CountAndSay.iterative(n), CountAndSay.regex(n));
        // Conway: no digit greater than 3 ever appears
        assertTrue(CountAndSay.iterative(30).chars().allMatch(c -> c >= '1' && c <= '3'));
        assertThrows(IllegalArgumentException.class, () -> CountAndSay.iterative(0));
    }

    @Test void stringToInteger() {
        Object[][] cases = {
                {"42", 42}, {"   -42", -42}, {"4193 with words", 4193}, {"words and 987", 0},
                {"-91283472332", Integer.MIN_VALUE}, {"91283472332", Integer.MAX_VALUE},
                {"2147483647", Integer.MAX_VALUE}, {"2147483648", Integer.MAX_VALUE},
                {"-2147483648", Integer.MIN_VALUE}, {"-2147483649", Integer.MIN_VALUE},
                {"+1", 1}, {"+-12", 0}, {"-+12", 0}, {"", 0}, {"   ", 0}, {"-", 0}, {"00000123", 123},
                {"  0000000000012345678", 12345678}, {"3.14", 3}, {" + 1", 0}, {"-0", 0},
        };
        for (Object[] c : cases) {
            String s = (String) c[0];
            int want = (int) c[1];
            assertEquals(want, StringToInteger.optimal(s), s);
            assertEquals(want, StringToInteger.withLong(s), s);
            assertEquals(want, StringToInteger.automaton(s), s);
        }
        assertEquals(0, StringToInteger.optimal(null));
        // every int round-trips
        Random rnd = new Random(8);
        for (int i = 0; i < 2000; i++) {
            int v = rnd.nextInt();
            assertEquals(v, StringToInteger.optimal("  " + v + "xyz"));
            assertEquals(v, StringToInteger.automaton(String.valueOf(v)));
        }
    }

    @Test void zArrayMatchesBruteForce() {
        assertArrayEquals(new int[]{0, 1, 0, 0, 3, 1, 0, 0, 2, 2, 1, 0}, ZAlgorithm.z("aabcaabxaaaz"));
        assertArrayEquals(new int[]{0, 4, 3, 2, 1}, ZAlgorithm.z("aaaaa"));
        assertArrayEquals(new int[0], ZAlgorithm.z(""));
        Random rnd = new Random(3);
        for (int t = 0; t < 600; t++) {
            int n = rnd.nextInt(15);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) sb.append((char) ('a' + rnd.nextInt(2)));
            assertArrayEquals(ZAlgorithm.zBrute(sb.toString()), ZAlgorithm.z(sb.toString()), sb.toString());
        }
    }

    @Test void zSearch() {
        assertEquals(List.of(0, 9, 12), ZAlgorithm.search("AABAACAADAABAABA", "AABA"));
        assertEquals(List.of(0, 1, 2), ZAlgorithm.search("aaaa", "aa"));
        assertEquals(List.of(), ZAlgorithm.search("abc", "abcd"));
        assertEquals(List.of(), ZAlgorithm.search("abc", ""));
        Random rnd = new Random(5);
        for (int t = 0; t < 600; t++) {
            String text = randomAb(rnd, rnd.nextInt(14)), pat = randomAb(rnd, 1 + rnd.nextInt(3));
            assertEquals(ZAlgorithm.searchBrute(text, pat), ZAlgorithm.search(text, pat), text + " / " + pat);
        }
    }

    private static String randomAb(Random rnd, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append((char) ('a' + rnd.nextInt(2)));
        return sb.toString();
    }
}
