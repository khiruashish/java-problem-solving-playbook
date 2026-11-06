# 05 · Java 8+ Streams

Java 8 interview rounds test whether you can **express data transformations declaratively** and whether
you know the **Collectors** API well.

## The 10 operations to know cold

| Kind | Operation | Example |
|------|-----------|---------|
| Intermediate | `filter` | `filter(n -> n % 2 == 0)` |
| | `map` / `mapToInt` | `map(String::length)` |
| | `flatMap` | `flatMap(List::stream)` |
| | `distinct`, `sorted`, `limit`, `skip` | `sorted(Comparator.reverseOrder()).skip(1)` |
| Terminal | `collect` | `collect(Collectors.groupingBy(...))` |
| | `reduce` | `reduce(0, Integer::sum)` |
| | `findFirst` / `anyMatch` / `allMatch` | returns `Optional` / `boolean` |
| | `count`, `min`, `max` | `max(Comparator.comparing(...))` |

## Collectors cheat sheet

```java
groupingBy(classifier)                              // Map<K, List<T>>
groupingBy(classifier, counting())                  // Map<K, Long>  (frequency)
groupingBy(classifier, LinkedHashMap::new, counting())   // keep encounter order
groupingBy(classifier, mapping(f, toList()))        // transform the grouped values
groupingBy(classifier, maxBy(comparator))           // Map<K, Optional<T>>
collectingAndThen(maxBy(c), Optional::get)          // unwrap
partitioningBy(predicate)                           // Map<Boolean, List<T>>
toMap(k, v, (a, b) -> a, TreeMap::new)              // with merge function + sorted map
joining(", ", "[", "]")
summarizingDouble(f)                                // count/min/max/avg/sum in one pass
```

## Question banks (Easy → Hard)

| Bank | Questions | Level |
|------|-----------|-------|
| [Numbers & lists](stream-basics.md) | 17 | <span class="badge easy">Easy</span> → <span class="badge medium">Medium</span> |
| [Strings](string-streams.md) | 15 | <span class="badge easy">Easy</span> → <span class="badge medium">Medium</span> |
| [Employee dataset](employee-streams.md) | 17 | <span class="badge medium">Medium</span> → <span class="badge hard">Hard</span> |

!!! warning "Pitfalls interviewers probe"
    - Streams are **single-use**. Reusing one throws `IllegalStateException`.
    - `toMap` throws on **duplicate keys** unless you pass a merge function.
    - `parallelStream()` is not free. It uses the common ForkJoinPool, which hurts small inputs and anything with ordered or stateful lambdas.
    - Side effects inside `map`/`filter` (like the `Set.add` trick for duplicates) are acceptable only in **sequential** streams.
    - `Stream.toList()` (Java 16+) returns an **unmodifiable** list. `Collectors.toList()` makes no guarantee.
