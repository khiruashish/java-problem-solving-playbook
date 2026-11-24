# Smallest range covering K lists

<span class="badge expert">Expert</span> <span class="badge tag">K-way merge</span> <span class="badge tag">Min-heap + running max</span> <span class="badge tag">Sliding window</span>

## Problem
You get k **sorted** lists. Find the smallest range `[a, b]` that includes **at least one number from each list**.
"Smaller" means a smaller `b − a`. On a tie, the smaller `a` wins.

## Example
```
[4, 10, 15, 24, 26]
[0, 9, 12, 20]          →  [20, 24]   (24 from list 1, 20 from list 2, 22 from list 3)
[5, 18, 22, 30]
```

## Clarifying questions
- Can a list be empty? Then no range exists. The code throws.
- Can values repeat across lists? Yes. `[1,2,3]` three times gives `[1, 1]`.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try every value as the lower bound `a`. For each list, the best pick is the smallest value ≥ a (binary search), and `b` is the max of those picks.
2. **Pick one element per list.** The range they span is `[min, max]`. To shrink it you can't lower the max (every other pick is already the smallest possible),
   so you **must raise the min**: advance the list that owns the minimum.
3. **"Repeatedly take the global minimum among k pointers"** is a **k-way merge with a min-heap**. Track the running max as you push.
   Every heap state is a valid range, so record the best one.
4. **Stop** when the list owning the minimum runs out. Any later range would have to leave that list out.
5. **Alternative**: merge everything into (value, list id), sort, then find the **minimum window that contains all k colours** with two pointers, the [minimum window substring](../01-strings/minimum-window-substring.md) pattern.

</div>

## Dry run (heap) on the example
| pop min (list) | max | range | best so far |
|----------------|-----|-------|-------------|
| 0 (L2) | 5 | [0, 5] | [0, 5] |
| 4 (L1) | 9 | [4, 9] | [0, 5] (same width, larger a) |
| 5 (L3) | 10 | [5, 10] | |
| 9 (L2) | 18 | [9, 18] | |
| 10 (L1) | 18 | [10, 18] | |
| 12 (L2) | 18 | [12, 18] | |
| 15 (L1) | 20 | [15, 20] | |
| 18 (L3) | 24 | [18, 24] | |
| 20 (L2) | 24 | **[20, 24]** | width 4 |

20 was the last value of L2, so the search stops: no later range could include L2.

## Code
```java
--8<-- "src/main/java/com/playbook/heap/SmallestRange.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force + binary search | O(N · k · log m) | O(1) |
| Min-heap k-way merge | **O(N log k)** | O(k) |
| Merge + sliding window | O(N log N) | O(N) |

N is the total number of values, m the longest list.

## Edge cases & pitfalls
- Stopping when the heap is empty is wrong: stop as soon as the **owner of the minimum** runs out.
- Compute widths in `long`. `b − a` overflows for `[MIN_VALUE, MAX_VALUE]`.
- The tie rule (a smaller `a` wins) has to be implemented explicitly. Otherwise you return the last equal-width range instead of the first.

## Related
[Merge K sorted arrays](merge-k-sorted-arrays.md) · [Merge K sorted lists](../06-linked-list/merge-k-sorted-lists.md) · [Minimum window substring](../01-strings/minimum-window-substring.md).
