# Streams on numbers & lists

<span class="badge easy">Easy</span> → <span class="badge medium">Medium</span> <span class="badge tag">filter/map/reduce</span> <span class="badge tag">Collectors</span>

Sample input used in the tests: `[10, 15, 8, 49, 25, 98, 98, 32, 15, 1]`

| Q | Question | Answer for the sample | Key API |
|---|----------|-----------------------|---------|
| 1 | Even numbers | `[10, 8, 98, 98, 32]` | `filter` |
| 2 | Numbers starting with 1 | `[10, 15, 15, 1]` | `map(String::valueOf)` + `startsWith` |
| 3 | Duplicate elements | `[98, 15]` | `filter(n -> !seen.add(n))` |
| 4 | First element | `10` | `findFirst` |
| 5 | Max | `98` | `max(Integer::compare)` |
| 6 | Sort descending | `[98, 98, 49, …]` | `sorted(reverseOrder())` |
| 7 | Second highest distinct | `49` | `distinct().sorted(reverse).skip(1)` |
| 8 | Sum / average | `351` / `35.1` | `reduce`, `mapToInt().average()` |
| 9 | Average of squares > 100 | | `mapToInt(n*n).filter(>100).average()` |
| 10 | Frequency of each | `{98=2, 15=2, …}` | `groupingBy(identity(), counting())` |
| 11 | Partition even/odd | | `partitioningBy` |
| 12 | Flatten list of lists | | `flatMap(List::stream)` |
| 13 | Merge two arrays, sorted & distinct | | `IntStream.concat` |
| 14 | All positive? | `true` | `allMatch` |
| 15 | Top 3 | `[98, 98, 49]` | `sorted(reverse).limit(3)` |
| 16 | Sum of digits | | `chars().map(getNumericValue).sum()` |
| 17 | Join as `"[a, b]"` | | `joining(", ", "[", "]")` |

## How to think about any stream question
<div class="steps" markdown>

1. **What is the source?** A `list.stream()`, `Arrays.stream(int[])` (primitive), or `s.chars()`.
2. **Which elements?** `filter`.
3. **What shape?** `map` / `mapToInt` / `flatMap`.
4. **What order or uniqueness?** `distinct`, `sorted`, `limit`, `skip`.
5. **What result?** A single value (`reduce`, `max`, `count`) or a container (`collect`).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/java8/StreamBasics.java"
```
