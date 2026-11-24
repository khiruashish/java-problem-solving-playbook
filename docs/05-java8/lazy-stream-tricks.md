# Laziness, infinite streams & index tricks

<span class="badge hard">Hard</span> <span class="badge tag">IntStream.range</span> <span class="badge tag">iterate / takeWhile</span> <span class="badge tag">Laziness</span>

Streams have **no index, no zip and no sliding window**. The trick behind most "hard" stream questions is to
**stream the indexes** with `IntStream.range` and look elements up. The other half of this bank is about
**laziness**: why an infinite stream can terminate, and in what order the stages actually run.

| Q | Question | Key idea | Answer |
|---|----------|----------|--------|
| 1 | Zip two lists | `range(0, min(a, b)).mapToObj(i -> a.get(i) + "-" + b.get(i))` | `[a-1, b-2]` |
| 2 | Differences between neighbours | `range(1, n).map(i -> x[i] − x[i−1])` | `[3,7,4] → [4, -3]` |
| 3 | Sliding windows of size k | `rangeClosed(0, n − k).mapToObj(i -> subList(i, i + k))` | `[[1,2],[2,3],[3,4]]` |
| 4 | Indexes of negatives | `range(0, n).filter(i -> a[i] < 0)` | `[1, 3]` |
| 5 | First n Fibonacci numbers | `Stream.iterate(new long[]{0, 1}, p -> {p[1], p[0] + p[1]})` | `0 1 1 2 3 5 8 13` |
| 6 | Powers of two up to a limit | `LongStream.iterate(1, x -> x <= max, x -> x * 2)` (Java 9) | `1 2 4 8 16` |
| 7 | Leading run of positives / the rest | `takeWhile` / `dropWhile` (Java 9) | `[3, 1]` / `[-2, 5]` |
| 8 | First 10 primes from an **infinite** stream | `iterate(2, x -> x + 1).filter(isPrime).limit(10)` | `2 … 29` |
| 9 | How many elements were touched before `findFirst`? | `peek` + counter | 3 of 5 |
| 10 | Execution order with and without `sorted()` | log each stage | see below |
| 11 | Chunk into batches of n | `range(0, ceil(n / size))` + `subList` | `[[1,2],[3,4],[5]]` |
| 12 | Reverse a list | index from the end | `[3, 2, 1]` |

## Laziness: what actually runs (Q10)
Without `sorted()`, each element goes **all the way down the pipeline** before the next one starts:
```
filter:b  map:b  forEach:b  filter:a  map:a  forEach:a
```
With `sorted()`, the sort is a **barrier**. It must see every element before it can emit the smallest:
```
filter:b  filter:a  |sort|  map:a  forEach:a  map:b  forEach:b
```

## Thinking process
<div class="steps" markdown>

1. **Need an index or two lists at once?** Stream `IntStream.range(0, n)` and index into the list. This only works for random-access lists (`ArrayList`, `List.of`), not `LinkedList`.
2. **Infinite source?** It is fine as long as something **short-circuits** it: `limit`, `takeWhile`, `findFirst`, `anyMatch`.
3. **Stateless vs stateful operations**: `filter` and `map` handle one element at a time. `sorted` and `distinct` need to see more, and `sorted` blocks until the upstream is exhausted. An infinite stream followed by `sorted()` never finishes.
4. **Don't force it.** A prefix sum or a "previous element" dependency is clearer as a loop (see the running balance in the [transactions round](transaction-streams.md)).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/java8/LazyStreamTricks.java"
```

## Pitfalls
- `peek` is for debugging. Since Java 9, `count()` may skip the pipeline entirely when the size is known, so a `peek` before `count()` may never run.
- `takeWhile` on an **unordered** stream (like a `HashSet`'s) gives an unspecified answer.
- `subList` returns a **view**. The code copies each window with `List::copyOf`, so callers can't mutate the source through it.
- `Stream.iterate` with a mutable seed (`long[]`) is safe only because each step creates a **new** array.

## Related
[Numbers & lists](stream-basics.md) · [Parallel stream pitfalls](parallel-stream-pitfalls.md) · [Sliding window maximum](../07-stack-queue/sliding-window-maximum.md) (the efficient, non-stream way to do windows).
