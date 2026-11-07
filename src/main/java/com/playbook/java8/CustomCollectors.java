package com.playbook.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Writing your own Collector with Collector.of(supplier, accumulator, combiner, finisher), plus teeing (Java 12)
 * and collectingAndThen. Knowing the 4 functions shows you understand how collect() really works.
 */
public final class CustomCollectors {
    private CustomCollectors() {}

    /** An unmodifiable list collector: accumulate into ArrayList, finish by wrapping. */
    public static <T> Collector<T, ?, List<T>> toUnmodifiableList() {
        return Collector.<T, List<T>, List<T>>of(          // explicit types: inference can't see T through ArrayList::new
                ArrayList::new,                              // supplier: a new mutable container per thread/segment
                List::add,                                   // accumulator: fold one element in
                (left, right) -> {                           // combiner: merge two partial containers (parallel)
                    left.addAll(right);
                    return left;
                },
                Collections::unmodifiableList);              // finisher: final transformation
    }

    /** Mutable result container for the average collector. */
    private static final class Avg {
        long sum, count;
    }

    /** Average of ints as a Collector — shows a custom accumulation type. Empty → 0.0. */
    public static Collector<Integer, ?, Double> averaging() {
        return Collector.of(Avg::new,
                (a, x) -> {
                    a.sum += x;
                    a.count++;
                },
                (a, b) -> {
                    a.sum += b.sum;
                    a.count += b.count;
                    return a;
                },
                a -> a.count == 0 ? 0.0 : (double) a.sum / a.count);
    }

    /** Joining with a custom separator and a LIMIT ("a, b, c and 2 more"). */
    public static Collector<String, ?, String> joiningLimited(int limit) {
        return Collectors.collectingAndThen(Collectors.toList(), all -> {
            String head = String.join(", ", all.subList(0, Math.min(limit, all.size())));
            return all.size() > limit ? head + " and " + (all.size() - limit) + " more" : head;
        });
    }

    /** Collectors.teeing: two collectors over ONE pass, merged. Here: {min, max} of a list. */
    public static int[] minAndMax(List<Integer> list) {
        return list.stream().collect(Collectors.teeing(
                Collectors.minBy(Integer::compare),
                Collectors.maxBy(Integer::compare),
                (min, max) -> new int[]{min.orElseThrow(), max.orElseThrow()}));
    }

    /** Frequency map sorted by key — groupingBy with a map factory and a downstream collector. */
    public static Map<Character, Long> sortedFrequency(String s) {
        return s.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));
    }

    /** Partition into chunks of size n (e.g. batching DB writes) — a stateful custom collector. */
    public static <T> Collector<T, ?, List<List<T>>> chunked(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be > 0");
        return Collector.of(
                () -> new ArrayList<List<T>>(),
                (chunks, x) -> {
                    if (chunks.isEmpty() || chunks.get(chunks.size() - 1).size() == n) chunks.add(new ArrayList<>());
                    chunks.get(chunks.size() - 1).add(x);
                },
                (a, b) -> {
                    throw new UnsupportedOperationException("chunking is order-dependent: sequential streams only");
                });
    }
}
