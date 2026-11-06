package com.playbook.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Java 8 stream interview questions on strings (Easy → Hard). */
public final class StringStreamProblems {
    private StringStreamProblems() {}

    /** Q1. Character frequency (order preserved). */
    public static Map<Character, Long> charFrequency(String s) {
        return s.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
    }

    /** Q2. First non-repeated character. */
    public static Optional<Character> firstNonRepeated(String s) {
        return charFrequency(s).entrySet().stream().filter(e -> e.getValue() == 1).map(Map.Entry::getKey).findFirst();
    }

    /** Q3. First repeated character. */
    public static Optional<Character> firstRepeated(String s) {
        return charFrequency(s).entrySet().stream().filter(e -> e.getValue() > 1).map(Map.Entry::getKey).findFirst();
    }

    /** Q4. Duplicate characters. */
    public static List<Character> duplicateChars(String s) {
        return charFrequency(s).entrySet().stream().filter(e -> e.getValue() > 1).map(Map.Entry::getKey).toList();
    }

    /** Q5. Reverse each word. */
    public static String reverseEachWord(String s) {
        return Arrays.stream(s.split(" ")).map(w -> new StringBuilder(w).reverse()).collect(Collectors.joining(" "));
    }

    /** Q6. Longest word (first one on ties). */
    public static Optional<String> longestWord(String sentence) {
        return Arrays.stream(sentence.split("\\s+")).reduce((a, b) -> b.length() > a.length() ? b : a);
    }

    /** Q7. Word frequency (case-insensitive). */
    public static Map<String, Long> wordFrequency(String sentence) {
        return Arrays.stream(sentence.toLowerCase().split("\\W+")).filter(w -> !w.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
    }

    /** Q8. Most frequent character. */
    public static Optional<Character> mostFrequentChar(String s) {
        return charFrequency(s).entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey);
    }

    /** Q9. Is palindrome (IntStream over the first half). */
    public static boolean isPalindrome(String s) {
        return IntStream.range(0, s.length() / 2).allMatch(i -> s.charAt(i) == s.charAt(s.length() - 1 - i));
    }

    /** Q10. Sort strings by length, then alphabetically. */
    public static List<String> sortByLengthThenAlpha(List<String> words) {
        return words.stream().sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())).toList();
    }

    /** Q11. Group words by length. */
    public static Map<Integer, List<String>> groupByLength(List<String> words) {
        return words.stream().collect(Collectors.groupingBy(String::length));
    }

    /** Q12. Remove duplicate characters, keep first occurrence. */
    public static String removeDuplicateChars(String s) {
        return s.chars().distinct().mapToObj(c -> String.valueOf((char) c)).collect(Collectors.joining());
    }

    /** Q13. Count vowels. */
    public static long countVowels(String s) {
        return s.toLowerCase().chars().filter(c -> "aeiou".indexOf(c) >= 0).count();
    }

    /** Q14. Are two strings anagrams? */
    public static boolean areAnagrams(String a, String b) {
        return Arrays.equals(a.chars().sorted().toArray(), b.chars().sorted().toArray());
    }

    /** Q15. Capitalise the first letter of every word. */
    public static String capitalize(String s) {
        return Arrays.stream(s.split(" "))
                .map(w -> w.isEmpty() ? w : Character.toUpperCase(w.charAt(0)) + w.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
