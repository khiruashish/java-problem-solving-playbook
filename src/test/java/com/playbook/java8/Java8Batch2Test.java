package com.playbook.java8;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Java8Batch2Test {

    @Test void optional() {
        Map<String, String> city = Map.of("ann", "  Pune ", "bob", "   ");
        assertEquals(Optional.of("  Pune "), OptionalProblems.lookup(city, "ann"));
        assertEquals(Optional.empty(), OptionalProblems.lookup(city, "zed"));
        assertEquals(Optional.of(4), OptionalProblems.cityNameLength(city, "ann"));
        assertEquals(Optional.empty(), OptionalProblems.cityNameLength(city, "bob"));     // blank filtered out
        assertEquals(Optional.of("Delhi"),
                OptionalProblems.managerCity(Map.of("e1", "m1"), Map.of("m1", "Delhi"), "e1"));
        assertEquals(Optional.empty(), OptionalProblems.managerCity(Map.of(), Map.of("m1", "Delhi"), "e1"));
        assertArrayEquals(new int[]{1, 0}, OptionalProblems.orElseVsOrElseGet(Optional.of("x"), () -> "costly"));
        assertThrows(IllegalArgumentException.class, () -> OptionalProblems.requireUser(Map.of(), "42"));
        assertEquals(List.of("a", "c"), OptionalProblems.presentValues(List.of(Optional.of("a"), Optional.empty(), Optional.of("c"))));
        assertEquals(Optional.of("db"), OptionalProblems.fromCacheOrDb(Map.of(), Map.of("k", "db"), "k"));
        assertEquals("age 30", OptionalProblems.describe(Optional.of(30)));
        assertEquals("unknown", OptionalProblems.describe(Optional.empty()));
    }

    @Test void collectors() {
        List<Integer> l = Stream.of(3, 1, 2).collect(CustomCollectors.toUnmodifiableList());
        assertEquals(List.of(3, 1, 2), l);
        assertThrows(UnsupportedOperationException.class, () -> l.add(4));
        List<Integer> big = IntStream.range(0, 10_000).boxed().parallel().collect(CustomCollectors.toUnmodifiableList());
        assertEquals(IntStream.range(0, 10_000).boxed().toList(), big, "combiner keeps order in parallel");
        assertEquals(2.0, Stream.of(1, 2, 3).collect(CustomCollectors.averaging()));
        assertEquals(0.0, Stream.<Integer>empty().collect(CustomCollectors.averaging()));
        assertEquals(4999.5, IntStream.range(0, 10_000).boxed().parallel().collect(CustomCollectors.averaging()));
        assertEquals("a, b and 3 more", Stream.of("a", "b", "c", "d", "e").collect(CustomCollectors.joiningLimited(2)));
        assertEquals("a, b", Stream.of("a", "b").collect(CustomCollectors.joiningLimited(2)));
        assertArrayEquals(new int[]{-5, 9}, CustomCollectors.minAndMax(List.of(3, -5, 9, 0)));
        assertEquals(List.of('a', 'b', 'n'), List.copyOf(CustomCollectors.sortedFrequency("banana").keySet()));
        assertEquals(List.of(List.of(1, 2), List.of(3, 4), List.of(5)),
                Stream.of(1, 2, 3, 4, 5).collect(CustomCollectors.chunked(2)));
    }

    @Test void parallelPitfalls() {
        int n = 100_000;
        assertEquals(IntStream.range(0, n).boxed().toList(), ParallelStreamPitfalls.safeCollect(n));
        assertEquals(IntStream.range(0, n).boxed().toList(), ParallelStreamPitfalls.forEachOrdered(n));
        int unsafe = ParallelStreamPitfalls.unsafeSideEffectSize(n);   // racy by design: may or may not lose elements
        assertTrue(unsafe == -1 || unsafe <= n);

        List<Integer> nums = IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toList());
        int sequential = ParallelStreamPitfalls.nonNeutralIdentity(nums, false);
        assertEquals(500_500 + 10, sequential);
        assertEquals(500_510, ParallelStreamPitfalls.neutralIdentity(nums));
        // With a common pool of >1 thread the parallel result adds 10 once per chunk; on a 1-CPU box it may match.
        int parallel = ParallelStreamPitfalls.nonNeutralIdentity(nums, true);
        assertTrue(parallel >= sequential && (parallel - 500_500) % 10 == 0);
        assertEquals(-500_500, ParallelStreamPitfalls.nonAssociative(nums, false));
    }

    @Test void requireUserFound() {
        assertEquals("Asha", OptionalProblems.requireUser(Map.of("42", "Asha"), "42"));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> OptionalProblems.requireUser(Map.of("42", "Asha"), "7"));
        assertEquals("no user 7", e.getMessage());               // a meaningful message, unlike get()
    }
}
