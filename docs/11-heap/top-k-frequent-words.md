# Top K frequent words

<span class="badge medium">Medium</span> <span class="badge tag">HashMap</span> <span class="badge tag">Heap</span> <span class="badge tag">Comparator</span>

## Problem
Return the k most frequent words, **sorted by frequency and then alphabetically**.
`["i","love","leetcode","i","love","coding"]`, k = 2 → `["i","love"]` (both appear twice, and "i" < "love").

## Thinking process
<div class="steps" markdown>

1. Count with a `HashMap`. Let d = the number of distinct words.
2. **Sort all entries** with the "better" order: count descending, then word ascending. O(d log d).
3. **Heap of size k**: the heap must evict the **worst** candidate, so it's ordered by the **reverse** of "better":
   lower count first, and on equal counts the alphabetically **later** word first. Getting this tie-break backwards is the main bug.
4. Popping returns the worst first, so reverse at the end.

</div>

## Comparator, spelled out
```java
Comparator<Entry<String, Long>> better =
    Entry.<String, Long>comparingByValue().reversed()      // higher count first
         .thenComparing(Entry.comparingByKey());           // then a, b, c…
PriorityQueue<…> heap = new PriorityQueue<>(better.reversed());   // root = worst
```

## Code
```java
--8<-- "src/main/java/com/playbook/heap/TopKFrequentWords.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Sort | O(n + d log d) | O(d) |
| Heap of size k | O(n + d log k) | O(d + k) |

## Related
[Top K frequent elements](../08-hashing/top-k-frequent.md) (numbers, with bucket sort) · Sort characters by frequency.
