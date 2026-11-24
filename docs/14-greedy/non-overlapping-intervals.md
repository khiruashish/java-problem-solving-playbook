# Non-overlapping intervals

<span class="badge medium">Medium</span> <span class="badge tag">Sort by end</span> <span class="badge tag">Activity selection</span>

## Problem
Return the **minimum number of intervals to remove** so the remaining intervals don't overlap.
Intervals that only touch (`[1,2]` and `[2,3]`) do **not** overlap.

## Examples
| intervals | remove | keep |
|-----------|--------|------|
| `[1,2] [2,3] [3,4] [1,3]` | 1 | drop `[1,3]` |
| `[1,2] [1,2] [1,2]` | 2 | keep one |
| `[1,2] [2,3]` | 0 | touching is fine |

## Clarifying questions
- Do touching endpoints overlap? No, here. Ask, because it changes `>=` to `>`.
- Return the count or the intervals? The count.

## Thinking process
<div class="steps" markdown>

1. **Reframe**: removing the fewest is the same as **keeping the most** non-overlapping intervals. That is the classic
   [activity selection](activity-selection.md) problem, and the answer is `n − maxKept`.
2. **Which interval should you keep first?** Not the shortest, and not the earliest **start**. Keep the one that **ends earliest**.
   It leaves the most room for everything after it.
3. **Exchange argument**: take any optimal solution. Its first interval ends no earlier than the earliest-ending interval,
   so swapping it for the earliest-ending one keeps the solution valid and the same size. Repeat the argument for the rest.
4. **Algorithm**: sort by end. Keep an interval if it starts at or after the last kept end. Otherwise count it as removed.
5. **Sort-by-start variant**: on a conflict, keep whichever of the two **ends earlier**. It is the same idea applied locally.

</div>

## Dry run (sort by end)
| interval | start ≥ lastEnd? | action | lastEnd |
|----------|------------------|--------|---------|
| [1,2] | (first) | keep | 2 |
| [2,3] | 2 ≥ 2 | keep | 3 |
| [1,3] | 1 < 3 | **remove** | 3 |
| [3,4] | 3 ≥ 3 | keep | 4 |

Removed: **1**.

## Code
```java
--8<-- "src/main/java/com/playbook/greedy/NonOverlappingIntervals.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| All subsets (oracle) | O(2ⁿ · n log n) | O(n) |
| DP, like LIS | O(n²) | O(n) |
| Greedy by end | **O(n log n)** | O(n) for the sorted copy |
| Greedy by start | **O(n log n)** | O(n) |

## Edge cases & pitfalls
- Sorting by start and **always** keeping the earlier one is wrong: `[1,10] [2,3] [3,4]` would keep `[1,10]` and remove two instead of one.
- `>=` vs `>` decides whether touching intervals count as overlapping.
- `Comparator.comparingInt(x -> x[1])` avoids the overflow that `(a, b) -> a[1] − b[1]` has with extreme values.

## Related
[Activity selection](activity-selection.md) · [Merge intervals](../02-arrays/merge-intervals.md) · [Minimum platforms](minimum-platforms.md) · *Minimum arrows to burst balloons* (the same greedy, counting kept intervals instead of removed ones).
