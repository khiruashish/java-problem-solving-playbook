package com.playbook.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Parallel streams: what goes wrong and the correct alternative. Each pitfall has a WRONG and a RIGHT method so the
 * difference can be demonstrated and tested.
 */
public final class ParallelStreamPitfalls {
    private ParallelStreamPitfalls() {}

    /** WRONG: side effects into a non-thread-safe ArrayList from a parallel stream → lost elements or exceptions. */
    public static int unsafeSideEffectSize(int n) {
        List<Integer> out = new ArrayList<>();
        try {
            IntStream.range(0, n).parallel().forEach(out::add);   // data race on ArrayList's size/elementData
        } catch (ArrayIndexOutOfBoundsException racedResize) {
            return -1;                                        // the race can also corrupt the array during a resize
        }
        return out.size();                                    // often < n
    }

    /** RIGHT: let the stream build the result — collect() gives each thread its own container and merges them. */
    public static List<Integer> safeCollect(int n) {
        return IntStream.range(0, n).parallel().boxed().collect(Collectors.toList());   // also keeps encounter order
    }

    /**
     * WRONG: reduce with an identity that is not neutral. reduce(10, Integer::sum) adds 10 ONCE sequentially but once
     * PER CHUNK in parallel → the answer depends on how the work was split.
     */
    public static int nonNeutralIdentity(List<Integer> list, boolean parallel) {
        return (parallel ? list.parallelStream() : list.stream()).reduce(10, Integer::sum);
    }

    /** RIGHT: neutral identity (0 for +), then add the constant once outside. */
    public static int neutralIdentity(List<Integer> list) {
        return list.parallelStream().reduce(0, Integer::sum) + 10;
    }

    /** WRONG: non-associative operation (subtraction) — sequential and parallel results differ. */
    public static int nonAssociative(List<Integer> list, boolean parallel) {
        return (parallel ? list.parallelStream() : list.stream()).reduce(0, (a, b) -> a - b);
    }

    /** forEach on a parallel stream has NO order guarantee; forEachOrdered restores encounter order (at a cost). */
    public static List<Integer> forEachOrdered(int n) {
        List<Integer> out = new ArrayList<>();
        IntStream.range(0, n).parallel().forEachOrdered(out::add);   // sequential hand-off: safe for an ArrayList
        return out;
    }
}
