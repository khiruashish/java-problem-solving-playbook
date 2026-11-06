# Merge intervals

<span class="badge hard">Hard</span> <span class="badge tag">Sort + sweep</span>

## Problem
Merge all overlapping intervals. `[[1,3],[2,6],[8,10],[15,18]]` → `[[1,6],[8,10],[15,18]]`. Touching intervals (`[1,4],[4,5]`) merge.

## Thinking process
<div class="steps" markdown>

1. Unsorted intervals can overlap anywhere, so comparing every pair is O(n²).
2. **Sort by start**. Now an interval can only overlap the **current merged block**.
3. Sweep: if `next.start <= current.end`, extend with `current.end = max(current.end, next.end)`. Otherwise close the block and start a new one.
4. Use `max` for the end because a later interval can sit entirely inside the current one (`[1,10],[2,3]`).

</div>

## Dry run (sorted)
| next | current | Action | Output |
|------|---------|--------|--------|
| [1,3] | [1,3] | start | |
| [2,6] | [1,6] | 2 ≤ 3, extend | |
| [8,10] | [8,10] | 8 > 6, close | [1,6] |
| [15,18] | [15,18] | close | [1,6] [8,10] |
| end | | flush | [1,6] [8,10] [15,18] |

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/MergeIntervals.java"
```

## Complexity
O(n log n) for the sort, plus an O(n) sweep. O(n) for the output.

## Interval family
Insert interval ✅ · Meeting rooms (can one person attend all? sort, then check overlaps) · Meeting rooms II (minimum number of rooms: min-heap of end times, or a sweep line) · Non-overlapping intervals (greedy by end time).
