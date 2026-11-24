# Map API & advanced Collectors

<span class="badge medium">Medium</span> → <span class="badge hard">Hard</span> <span class="badge tag">Map.merge</span> <span class="badge tag">computeIfAbsent</span> <span class="badge tag">filtering / flatMapping</span>

Interviewers love to ask the same question twice: once with **imperative Map methods** (`merge`, `compute`,
`computeIfAbsent`) and once with **Collectors**. Knowing both, and when the difference matters, is what this bank is about.

All questions use this word list:
`apple, avocado, banana, blueberry, cherry, kiwi, apricot, fig`

| Q | Question | Key API | Answer |
|---|----------|---------|--------|
| 1 | Word frequency | `map.merge(w, 1, Integer::sum)` | `{a=2, b=1}` for `[a, b, a]` |
| 2 | Group by first letter (imperative) | `computeIfAbsent(k, k -> new ArrayList<>()).add(w)` | `a=[apple, avocado, apricot]` |
| 3 | Longest word per first letter | `toMap(k, v, merge, TreeMap::new)` | `a=avocado` (ties keep the first) |
| 4 | Invert a map (value → keys) | `groupingBy(getValue, mapping(getKey, toCollection(TreeSet::new)))` | `{1=[x, z], 2=[y]}` |
| 5 | Length → letter → count | nested `groupingBy` | `7={a=2}` |
| 6 | Long words per letter, **keeping empty groups** | `groupingBy(k, filtering(p, toList()))` (Java 9) | `k=[]` is kept |
| 6b | Same with `filter()` first | `filter(p).collect(groupingBy(k))` | `k` disappears |
| 7 | Distinct chars per word length | `flatMapping(w -> w.chars()…)` (Java 9) | `3=[f, g, i]` |
| 8 | Top-2 longest per letter | `collectingAndThen(toList(), l -> l.stream().sorted(c).limit(2).toList())` | `a=[apricot, avocado]` |
| 9 | Sort a map by value desc, then key | `sorted(comparingByValue().reversed().thenComparing(comparingByKey()))` into `LinkedHashMap` | `[c, a, b]` |
| 10 | Merge two count maps | `b.forEach((k, v) -> out.merge(k, v, Integer::sum))` | `{a=3, b=2, c=4}` |
| 11 | Running max per key | `compute(k, (k, old) -> old == null ? v : max(old, v))` | `{t=9, u=3}` |

## Thinking process
<div class="steps" markdown>

1. **"Count / sum per key" in a loop** → `merge`. It replaces get, check for null, put with one atomic-looking call.
2. **"List per key" in a loop** → `computeIfAbsent(k, k -> new ArrayList<>()).add(v)`. Never `putIfAbsent(k, new ArrayList<>())`, which allocates a list on every call.
3. **"One value per key" with a stream** → `toMap`. Ask yourself: can keys repeat? If yes, you **must** pass a merge function, or you get `IllegalStateException: Duplicate key`.
4. **Filter before or inside the grouping?** Before (`filter`) drops empty groups. Inside (`filtering`) keeps every key with an empty list.
   Interviewers ask precisely this difference.
5. **Sorted output** → pass `TreeMap::new`. **Custom order** → sort the entries, then collect into a `LinkedHashMap`, which keeps insertion order.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/java8/MapCollectorsAdvanced.java"
```

## Pitfalls
- `toMap` also throws on **null values** (it uses `merge` internally). Filter out nulls or use `groupingBy`.
- `merge` with a function that returns `null` **removes** the key. That is handy for decrement-to-zero counters.
- `compute*` lambdas must not modify the same map. That throws `ConcurrentModificationException` in `HashMap`.
- `Collectors.toMap` without a map factory returns a `HashMap`, whose iteration order is unspecified. Don't assert on it.

## Related
[Employee dataset](employee-streams.md) · [Custom collectors & teeing](custom-collectors.md) · [Transactions dataset](transaction-streams.md).
