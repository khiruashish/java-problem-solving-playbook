package com.playbook.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Java 8 stream interview questions on numbers and lists (Easy → Medium). Each method = one question. */
public final class StreamBasics {
    private StreamBasics() {}

    /** Q1. Even numbers from a list. */
    public static List<Integer> evens(List<Integer> list) {
        return list.stream().filter(n -> n % 2 == 0).toList();
    }

    /** Q2. Numbers starting with digit 1 (e.g. 10, 15, 1). */
    public static List<Integer> startingWithOne(List<Integer> list) {
        return list.stream().map(String::valueOf).filter(s -> s.startsWith("1")).map(Integer::valueOf).toList();
    }

    /** Q3. Duplicate elements (each reported once, in first-seen order). Set.add returns false on repeat. */
    public static Set<Integer> duplicates(List<Integer> list) {
        Set<Integer> seen = new java.util.HashSet<>();
        return list.stream().filter(n -> !seen.add(n)).collect(Collectors.toCollection(java.util.LinkedHashSet::new));
    }

    /** Q4. First element, or empty. */
    public static Optional<Integer> first(List<Integer> list) {
        return list.stream().findFirst();
    }

    /** Q5. Max value (Optional because the list may be empty). */
    public static Optional<Integer> max(List<Integer> list) {
        return list.stream().max(Integer::compare);
    }

    /** Q6. Sort descending. */
    public static List<Integer> sortDescending(List<Integer> list) {
        return list.stream().sorted(Comparator.reverseOrder()).toList();
    }

    /** Q7. Second highest distinct number. */
    public static Optional<Integer> secondHighest(List<Integer> list) {
        return list.stream().distinct().sorted(Comparator.reverseOrder()).skip(1).findFirst();
    }

    /** Q8. Sum, average via reduce and IntStream. */
    public static int sum(List<Integer> list) {
        return list.stream().reduce(0, Integer::sum);
    }

    public static double average(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    /** Q9. Square each, keep those > 100, average them. */
    public static double averageOfSquaresAbove100(List<Integer> list) {
        return list.stream().mapToInt(n -> n * n).filter(sq -> sq > 100).average().orElse(0);
    }

    /** Q10. Frequency of each element. */
    public static Map<Integer, Long> frequency(List<Integer> list) {
        return list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /** Q11. Partition into even / odd. */
    public static Map<Boolean, List<Integer>> partitionEvenOdd(List<Integer> list) {
        return list.stream().collect(Collectors.partitioningBy(n -> n % 2 == 0));
    }

    /** Q12. Flatten a list of lists. */
    public static List<Integer> flatten(List<List<Integer>> lists) {
        return lists.stream().flatMap(List::stream).toList();
    }

    /** Q13. Merge two arrays, sorted and distinct. */
    public static int[] mergeSortedDistinct(int[] a, int[] b) {
        return IntStream.concat(Arrays.stream(a), Arrays.stream(b)).sorted().distinct().toArray();
    }

    /** Q14. Check if any / all / none match. */
    public static boolean allPositive(List<Integer> list) {
        return list.stream().allMatch(n -> n > 0);
    }

    /** Q15. Top-3 largest. */
    public static List<Integer> top3(List<Integer> list) {
        return list.stream().sorted(Comparator.reverseOrder()).limit(3).toList();
    }

    /** Q16. Sum of digits of a number via stream. */
    public static int sumOfDigits(int n) {
        return String.valueOf(Math.abs(n)).chars().map(Character::getNumericValue).sum();
    }

    /** Q17. Join numbers into "[1, 2, 3]" style string. */
    public static String join(List<Integer> list) {
        return list.stream().map(String::valueOf).collect(Collectors.joining(", ", "[", "]"));
    }
}
