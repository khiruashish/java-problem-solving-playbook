package com.playbook.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Laziness, infinite streams and index-based tricks (Hard). Streams have no zip, no sliding window and no index —
 * these questions test whether you know to drive them from IntStream.range.
 */
public final class LazyStreamTricks {
    private LazyStreamTricks() {}

    /** Q1. Zip two lists into "a1-b1" pairs, up to the shorter length. */
    public static List<String> zip(List<String> a, List<String> b) {
        return IntStream.range(0, Math.min(a.size(), b.size()))
                .mapToObj(i -> a.get(i) + "-" + b.get(i))
                .toList();
    }

    /** Q2. Differences between neighbours: [3, 7, 4] → [4, -3]. */
    public static List<Integer> adjacentDifferences(List<Integer> xs) {
        return IntStream.range(1, xs.size()).mapToObj(i -> xs.get(i) - xs.get(i - 1)).toList();
    }

    /** Q3. Sliding windows of size k: [1,2,3,4], k=2 → [[1,2],[2,3],[3,4]]. */
    public static List<List<Integer>> windows(List<Integer> xs, int k) {
        if (k <= 0) throw new IllegalArgumentException("k must be positive");
        return IntStream.rangeClosed(0, xs.size() - k)
                .mapToObj(i -> xs.subList(i, i + k))
                .map(List::copyOf)
                .toList();
    }

    /** Q4. Index of every element matching a predicate (here: negative numbers). */
    public static List<Integer> indexesOfNegatives(int[] a) {
        return IntStream.range(0, a.length).filter(i -> a[i] < 0).boxed().toList();
    }

    /** Q5. First n Fibonacci numbers with Stream.iterate on a pair. */
    public static List<Long> fibonacci(int n) {
        return Stream.iterate(new long[]{0, 1}, p -> new long[]{p[1], p[0] + p[1]})
                .limit(n)
                .map(p -> p[0])
                .toList();
    }

    /** Q6. Java 9 iterate(seed, hasNext, next): powers of two up to a limit — no limit() needed. */
    public static List<Long> powersOfTwoUpTo(long max) {
        return LongStream.iterate(1, x -> x <= max, x -> x * 2).boxed().toList();
    }

    /** Q7. takeWhile / dropWhile on an ordered stream: the leading run that satisfies a predicate. */
    public static List<Integer> leadingPositives(List<Integer> xs) {
        return xs.stream().takeWhile(x -> x > 0).toList();
    }

    public static List<Integer> afterLeadingPositives(List<Integer> xs) {
        return xs.stream().dropWhile(x -> x > 0).toList();
    }

    /** Q8. The first n primes from an INFINITE stream: laziness makes this terminate. */
    public static List<Integer> firstPrimes(int n) {
        return IntStream.iterate(2, x -> x + 1)
                .filter(LazyStreamTricks::isPrime)
                .limit(n)
                .boxed()
                .toList();
    }

    private static boolean isPrime(int x) {
        return x >= 2 && IntStream.rangeClosed(2, (int) Math.sqrt(x)).noneMatch(d -> x % d == 0);
    }

    /**
     * Q9. Prove laziness: count how many elements map() actually touched before findFirst() returned.
     * Streams process element by element (vertical), not stage by stage (horizontal).
     */
    public static int elementsTouchedUntilFirstMatch(List<Integer> xs, int target) {
        AtomicInteger touched = new AtomicInteger();
        xs.stream().peek(x -> touched.incrementAndGet()).filter(x -> x == target).findFirst();
        return touched.get();
    }

    /**
     * Q10. Execution order trace: which stage sees which element, in order. Shows that sorted() is a BARRIER
     * (stateful: it must see everything before emitting anything), while filter/map stream element-by-element.
     */
    public static List<String> executionOrder(List<String> xs, boolean withSorted) {
        List<String> log = new ArrayList<>();
        Stream<String> s = xs.stream().filter(x -> { log.add("filter:" + x); return true; });
        if (withSorted) s = s.sorted();
        s.map(x -> { log.add("map:" + x); return x; }).forEach(x -> log.add("forEach:" + x));
        return log;
    }

    /** Q11. Chunk a list into batches of size n (last batch may be smaller). */
    public static List<List<Integer>> chunks(List<Integer> xs, int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be positive");
        return IntStream.range(0, (xs.size() + n - 1) / n)
                .mapToObj(i -> List.copyOf(xs.subList(i * n, Math.min(xs.size(), (i + 1) * n))))
                .toList();
    }

    /** Q12. Reverse a list with streams (index from the end). */
    public static <T> List<T> reversed(List<T> xs) {
        return IntStream.range(0, xs.size()).mapToObj(i -> xs.get(xs.size() - 1 - i)).toList();
    }
}
