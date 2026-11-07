# Custom collectors & teeing

<span class="badge hard">Hard</span> <span class="badge tag">Collector.of</span> <span class="badge tag">teeing</span> <span class="badge tag">collectingAndThen</span>

## How `collect()` actually works
A `Collector<T, A, R>` is four functions:

| Function | Role | `toList()` equivalent |
|----------|------|-----------------------|
| **supplier** `() → A` | create an empty mutable container | `ArrayList::new` |
| **accumulator** `(A, T) → void` | fold one element in | `List::add` |
| **combiner** `(A, A) → A` | merge two partial containers (parallel streams) | `addAll` |
| **finisher** `A → R` | final transformation | identity, or `unmodifiableList` |

In a parallel stream, each chunk gets its own container from the supplier, which is why `collect` is thread-safe while
`forEach(list::add)` isn't. See [parallel pitfalls](parallel-stream-pitfalls.md).

## Questions in this bank
| Q | Collector | Idea |
|---|-----------|------|
| 1 | `toUnmodifiableList()` | the four functions, with an explicit finisher |
| 2 | `averaging()` | a custom accumulation type (`sum`, `count`) and a combiner for parallel use |
| 3 | `joiningLimited(2)` → `"a, b and 3 more"` | `collectingAndThen(toList(), …)` |
| 4 | min **and** max in one pass | `Collectors.teeing` (Java 12) |
| 5 | sorted frequency map | `groupingBy(f, TreeMap::new, counting())` |
| 6 | `chunked(n)` → batches | a **stateful, order-dependent** collector whose combiner refuses parallel use |

## Code
```java
--8<-- "src/main/java/com/playbook/java8/CustomCollectors.java"
```

## Testing note
The `toUnmodifiableList` and `averaging` collectors are also run on **parallel** streams of 10,000 elements, which proves the
combiners are correct and that encounter order is preserved.
