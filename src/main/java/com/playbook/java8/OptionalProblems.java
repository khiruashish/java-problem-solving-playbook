package com.playbook.java8;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/** Optional interview questions: creation, transformation, defaults, and the anti-patterns to avoid. */
public final class OptionalProblems {
    private OptionalProblems() {}

    /** Q1. Wrap a possibly-null lookup (Map.get returns null for missing keys). */
    public static Optional<String> lookup(Map<String, String> m, String key) {
        return Optional.ofNullable(m.get(key));              // Optional.of(null) would throw NPE
    }

    /** Q2. Chain transformations; any empty step short-circuits. Length of a trimmed, non-blank city name. */
    public static Optional<Integer> cityNameLength(Map<String, String> userCity, String user) {
        return Optional.ofNullable(userCity.get(user))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(String::length);
    }

    /** Q3. flatMap when the mapping function itself returns Optional (avoids Optional<Optional<T>>). */
    public static Optional<String> managerCity(Map<String, String> managerOf, Map<String, String> cityOf, String emp) {
        return Optional.ofNullable(managerOf.get(emp)).flatMap(m -> Optional.ofNullable(cityOf.get(m)));
    }

    /**
     * Q4. orElse vs orElseGet: orElse ALWAYS evaluates its argument (even when a value is present);
     * orElseGet only calls the supplier when empty. Returns how many times the expensive default ran.
     */
    public static int[] orElseVsOrElseGet(Optional<String> present, Supplier<String> expensive) {
        int[] calls = {0};
        Supplier<String> counted = () -> {
            calls[0]++;
            return expensive.get();
        };
        present.orElse(counted.get());                       // evaluated eagerly → counts 1 even though unused
        int afterOrElse = calls[0];
        present.orElseGet(counted);                          // lazy → not called when present
        return new int[]{afterOrElse, calls[0] - afterOrElse};
    }

    /** Q5. orElseThrow with a meaningful exception instead of get(). */
    public static String requireUser(Map<String, String> users, String id) {
        return Optional.ofNullable(users.get(id)).orElseThrow(() -> new IllegalArgumentException("no user " + id));
    }

    /** Q6. Optional.stream() (Java 9) — keep only present values from a list of Optionals. */
    public static List<String> presentValues(List<Optional<String>> list) {
        return list.stream().flatMap(Optional::stream).toList();
    }

    /** Q7. or() (Java 9): try a fallback Optional source lazily. */
    public static Optional<String> fromCacheOrDb(Map<String, String> cache, Map<String, String> db, String key) {
        return Optional.ofNullable(cache.get(key)).or(() -> Optional.ofNullable(db.get(key)));
    }

    /** Q8. ifPresentOrElse (Java 9): act on both outcomes without isPresent()/get(). */
    public static String describe(Optional<Integer> age) {
        StringBuilder sb = new StringBuilder();
        age.ifPresentOrElse(a -> sb.append("age ").append(a), () -> sb.append("unknown"));
        return sb.toString();
    }
}
