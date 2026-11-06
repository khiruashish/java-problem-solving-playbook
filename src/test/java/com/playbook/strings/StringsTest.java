package com.playbook.strings;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StringsTest {

    @Test void reverseString() {
        for (String s : new String[]{"", "a", "ab", "hello", "racecar", "Java 21!"}) {
            String expected = new StringBuilder(s).reverse().toString();
            assertEquals(expected, ReverseString.bruteForce(s));
            assertEquals(expected, ReverseString.optimal(s));
            assertEquals(expected, ReverseString.library(s));
            assertEquals(expected, ReverseString.recursive(s));
            assertEquals(expected, ReverseString.streams(s));
        }
        assertNull(ReverseString.optimal(null));
        assertNull(ReverseString.recursive(null));
    }

    @Test void palindrome() {
        for (String s : new String[]{"", "a", "aa", "aba", "abba", "racecar"}) {
            assertTrue(Palindrome.bruteForce(s), s);
            assertTrue(Palindrome.isPalindrome(s), s);
            assertTrue(Palindrome.recursive(s), s);
            assertTrue(Palindrome.streams(s), s);
        }
        for (String s : new String[]{"ab", "abca", "Aba"}) {
            assertFalse(Palindrome.isPalindrome(s), s);
            assertFalse(Palindrome.recursive(s), s);
            assertFalse(Palindrome.streams(s), s);
        }
        assertTrue(Palindrome.isValidPalindrome("A man, a plan, a canal: Panama"));
        assertFalse(Palindrome.isValidPalindrome("race a car"));
        assertTrue(Palindrome.isValidPalindrome(" ,."));
        assertFalse(Palindrome.isPalindrome(null));
    }

    @Test void vowelConsonant() {
        int[] expected = {3, 7};                              // "Hello World 2026" → e,o,o / h,l,l,w,r,l,d
        assertArrayEquals(expected, VowelConsonantCount.optimal("Hello World 2026"));
        assertArrayEquals(expected, VowelConsonantCount.recursive("Hello World 2026"));
        assertArrayEquals(expected, VowelConsonantCount.streams("Hello World 2026"));
        assertArrayEquals(new int[]{0, 0}, VowelConsonantCount.optimal(null));
        assertArrayEquals(new int[]{0, 0}, VowelConsonantCount.streams(""));
    }

    @Test void characterFrequency() {
        Map<Character, Integer> m = CharacterFrequency.optimal("programming");
        assertEquals(m, CharacterFrequency.bruteForce("programming"));
        assertEquals(2, m.get('r'));
        assertEquals(2, m.get('g'));
        assertEquals(2, m.get('m'));
        assertEquals(List.of('p', 'r', 'o', 'g', 'a', 'm', 'i', 'n'), List.copyOf(m.keySet()));
        assertEquals(2L, CharacterFrequency.streams("programming").get('g'));
        assertEquals(List.of('r', 'g', 'm'), CharacterFrequency.duplicates("programming"));
        assertEquals(3, CharacterFrequency.lowercaseCounts("banana")['a' - 'a']);
        assertTrue(CharacterFrequency.optimal(null).isEmpty());
    }

    @Test void anagram() {
        String[][] yes = {{"listen", "silent"}, {"", ""}, {"aab", "aba"}};
        String[][] no = {{"rat", "car"}, {"aab", "abb"}, {"a", "ab"}, {"Listen", "silent"}};
        for (String[] p : yes) {
            assertTrue(Anagram.sorting(p[0], p[1]));
            assertTrue(Anagram.optimal(p[0], p[1]));
            assertTrue(Anagram.hashMap(p[0], p[1]));
            assertTrue(Anagram.streams(p[0], p[1]));
        }
        for (String[] p : no) {
            assertFalse(Anagram.sorting(p[0], p[1]));
            assertFalse(Anagram.optimal(p[0], p[1]));
            assertFalse(Anagram.hashMap(p[0], p[1]));
            assertFalse(Anagram.streams(p[0], p[1]));
        }
        assertFalse(Anagram.optimal(null, "a"));
    }

    @Test void firstNonRepeating() {
        assertEquals('l', FirstNonRepeatingChar.optimal("leetcode"));
        assertEquals('v', FirstNonRepeatingChar.optimal("loveleetcode"));
        assertEquals('v', FirstNonRepeatingChar.bruteForce("loveleetcode"));
        assertEquals('v', FirstNonRepeatingChar.streams("loveleetcode"));
        assertEquals(FirstNonRepeatingChar.NONE, FirstNonRepeatingChar.optimal("aabb"));
        assertEquals(FirstNonRepeatingChar.NONE, FirstNonRepeatingChar.streams(""));
        assertEquals(2, FirstNonRepeatingChar.firstUniqueIndex("loveleetcode"));
        assertEquals(-1, FirstNonRepeatingChar.firstUniqueIndex("aabb"));
    }

    @Test void reverseWords() {
        String in = "  the sky   is blue ";
        String expected = "blue is sky the";
        assertEquals(expected, ReverseWords.library(in));
        assertEquals(expected, ReverseWords.optimal(in));
        assertEquals(expected, ReverseWords.streams(in));
        assertEquals("blue is sky the", ReverseWords.recursive("the sky is blue"));
        assertEquals("", ReverseWords.optimal("   "));
        assertEquals("", ReverseWords.library("   "));
        assertEquals("", ReverseWords.streams("   "));
        assertEquals("I evol avaJ", ReverseWords.reverseEachWord("I love Java"));
    }

    @Test void compression() {
        assertEquals("a3b2c1", StringCompression.compress("aaabbc"));
        assertEquals("a3b2c1", StringCompression.recursive("aaabbc"));
        assertEquals("a1", StringCompression.compress("a"));
        assertEquals("abc", StringCompression.compressIfShorter("abc"));
        assertEquals("a5b1", StringCompression.compressIfShorter("aaaaab"));
        assertEquals("aaaaaaaaaaaabbb", StringCompression.decode("a12b3"));
        assertEquals("", StringCompression.compress(""));
    }

    @Test void longestCommonPrefix() {
        String[][] cases = {{"flower", "flow", "flight"}, {"dog", "racecar", "car"}, {"a"}, {"ab", "ab"}, {"", "a"}};
        String[] expected = {"fl", "", "a", "ab", ""};
        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], LongestCommonPrefix.verticalScan(cases[i]));
            assertEquals(expected[i], LongestCommonPrefix.horizontalScan(cases[i]));
            assertEquals(expected[i], LongestCommonPrefix.sorting(cases[i]));
            assertEquals(expected[i], LongestCommonPrefix.divideAndConquer(cases[i]));
        }
        assertEquals("", LongestCommonPrefix.verticalScan(new String[0]));
    }

    @Test void validParentheses() {
        for (String s : new String[]{"", "()", "()[]{}", "{[()]}", "(([]){})"}) {
            assertTrue(ValidParentheses.optimal(s), s);
            assertTrue(ValidParentheses.bruteForce(s), s);
        }
        for (String s : new String[]{"(", ")", "(]", "([)]", "(()"}) {
            assertFalse(ValidParentheses.optimal(s), s);
            assertFalse(ValidParentheses.bruteForce(s), s);
        }
        assertTrue(ValidParentheses.singleTypeCounter("(())()"));
        assertFalse(ValidParentheses.singleTypeCounter(")("));
    }

    @Test void permutations() {
        List<String> rec = StringPermutations.recursive("abc");
        assertEquals(List.of("abc", "acb", "bac", "bca", "cab", "cba"), rec);
        assertEquals(List.of("abc", "acb", "bac", "bca", "cab", "cba"), StringPermutations.iterative("cba"));
        assertEquals(Set.of("aab", "aba", "baa"), StringPermutations.distinctBacktracking("aab"));
        assertEquals(List.of("aab", "aba", "baa"), StringPermutations.iterative("aba"));
        assertEquals(List.of(""), StringPermutations.recursive(""));
        assertEquals(24, StringPermutations.recursive("abcd").size());
    }

    @Test void longestSubstringWithoutRepeating() {
        String[] in = {"abcabcbb", "bbbbb", "pwwkew", "", " ", "abba", "dvdf"};
        int[] expected = {3, 1, 3, 0, 1, 2, 3};
        for (int i = 0; i < in.length; i++) {
            assertEquals(expected[i], LongestSubstringWithoutRepeating.bruteForce(in[i]), in[i]);
            assertEquals(expected[i], LongestSubstringWithoutRepeating.slidingWindowSet(in[i]), in[i]);
            assertEquals(expected[i], LongestSubstringWithoutRepeating.optimal(in[i]), in[i]);
        }
    }

    @Test void longestPalindromicSubstring() {
        String[] in = {"babad", "cbbd", "a", "forgeeksskeegfor", "abacdfgdcaba", ""};
        int[] lengths = {3, 2, 1, 10, 3, 0};
        for (int i = 0; i < in.length; i++) {
            for (String r : new String[]{
                    LongestPalindromicSubstring.bruteForce(in[i]),
                    LongestPalindromicSubstring.dynamicProgramming(in[i]),
                    LongestPalindromicSubstring.expandAroundCenter(in[i]),
                    LongestPalindromicSubstring.manacher(in[i])}) {
                assertEquals(lengths[i], r.length(), in[i]);
                assertTrue(in[i].contains(r) && Palindrome.isPalindrome(r));
            }
        }
        assertEquals("geeksskeeg", LongestPalindromicSubstring.manacher("forgeeksskeegfor"));
    }

    @Test void groupAnagrams() {
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> expected = List.of(List.of("eat", "tea", "ate"), List.of("tan", "nat"), List.of("bat"));
        assertEquals(expected, GroupAnagrams.sortedKey(words));
        assertEquals(expected, GroupAnagrams.countKey(words));
        assertEquals(expected, GroupAnagrams.streams(words));
    }

    @Test void minimumWindow() {
        assertEquals("BANC", MinimumWindowSubstring.optimal("ADOBECODEBANC", "ABC"));
        assertEquals("BANC", MinimumWindowSubstring.bruteForce("ADOBECODEBANC", "ABC"));
        assertEquals("a", MinimumWindowSubstring.optimal("a", "a"));
        assertEquals("", MinimumWindowSubstring.optimal("a", "aa"));
        assertEquals("", MinimumWindowSubstring.bruteForce("a", "aa"));
        assertEquals("aa", MinimumWindowSubstring.optimal("baab", "aa"));
    }

    @Test void kmp() {
        assertArrayEquals(new int[]{0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 5}, KmpPatternSearch.lps("AABAACAABAA"));
        String text = "AABAACAADAABAABA";
        List<Integer> expected = List.of(0, 9, 12);
        assertEquals(expected, KmpPatternSearch.naive(text, "AABA"));
        assertEquals(expected, KmpPatternSearch.kmp(text, "AABA"));
        assertEquals(expected, KmpPatternSearch.rabinKarp(text, "AABA"));
        assertEquals(List.of(0, 1, 2), KmpPatternSearch.kmp("aaaa", "aa"));
        assertEquals(List.of(), KmpPatternSearch.kmp("abc", "d"));
        assertEquals(List.of(), KmpPatternSearch.rabinKarp("ab", "abc"));
    }
}
