# Median of two sorted arrays

<span class="badge expert">Expert</span> <span class="badge tag">Binary search on a partition</span>

## Problem
Two sorted arrays of sizes m and n. Return the median of their union in **O(log(min(m, n)))**.
`[1,3]` + `[2]` → 2.0 · `[1,2]` + `[3,4]` → 2.5.

## Thinking process
<div class="steps" markdown>

1. **Merge everything, then pick the middle**: O(m + n) time and space.
2. **Merge only to the middle**: O(m + n) time, O(1) space. That's a good answer, but not the target.
3. **Reframe the question**: the median splits the combined data into a left half and a right half of equal size, where
   **everything on the left ≤ everything on the right**.
4. Cut array a after i elements. Then b must be cut after `j = half − i` elements. Only i is free, so **binary search i**
   over the **shorter** array.
5. The cut is valid when `aLeft ≤ bRight` and `bLeft ≤ aRight`. If `aLeft > bRight`, you took too many from a, so move i left.
   Otherwise move it right.
6. Median = `max(leftSide)` for an odd total, or `(max(leftSide) + min(rightSide)) / 2` for an even total.
   Use ±∞ sentinels for empty sides.

</div>

## Picture
```
a:  1  3  | 8  9          i = 2
b:  2  4  5 | 7  10       j = half − i = 3
left = {1,3,2,4,5}  right = {8,9,7,10}
check: 3 ≤ 7 ✅  5 ≤ 8 ✅  → odd total, median = max(3,5) = 5
```

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/MedianTwoSortedArrays.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Full merge | O(m + n) | O(m + n) |
| Merge to the middle | O(m + n) | O(1) |
| Partition binary search | O(log min(m, n)) | O(1) |

## Why it's "Expert"
The jump from "search for a value" to "search for a **cut**" is the whole insight, and the boundary handling
(empty sides, odd/even totals, searching the shorter array) has to be exactly right. The tests compare it with a full
merge on 500 random cases, including empty arrays.
