# First & last occurrence (lower / upper bound)

<span class="badge medium">Medium</span> <span class="badge tag">Binary search</span>

## Problem
In a sorted array with duplicates, return the first and last index of `target`, or `[−1, −1]`.
`[5,7,7,8,8,10]`, 8 → `[3, 4]`. **Bonus**: count the occurrences in O(log n).

## Thinking process
<div class="steps" markdown>

1. **Linear scan**: O(n). **Binary search, then expand left and right**: O(n) in the worst case (`[8,8,8,…,8]`).
2. **Insight**: don't search for *the target*. Search for a **boundary**:
    - **lower bound** = the first index with `a[i] ≥ target`;
    - **upper bound** = the first index with `a[i] > target`.
3. Both are "find the first index where a condition becomes true", which is the **half-open template**. When the
   condition holds at mid, keep mid as a candidate (`hi = mid`). Otherwise discard it (`lo = mid + 1`).
4. first = lower bound (if `a[first] == target`), last = upper bound − 1, and count = upper bound − lower bound.

</div>

## Dry run (lower bound of 8 in `[5,7,7,8,8,10]`)
| lo | hi | mid | a[mid] ≥ 8? | Action |
|----|----|-----|-------------|--------|
| 0 | 6 | 3 | yes | hi = 3 |
| 0 | 3 | 1 | no | lo = 2 |
| 2 | 3 | 2 | no | lo = 3 |
| 3 | 3 | stop | | **3** |

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/FirstLastOccurrence.java"
```

## Complexity
O(log n) time, O(1) space, versus O(n) for the linear or "find and expand" approaches.

## Java equivalents
`Arrays.binarySearch` returns **some** matching index, not necessarily the first. For bounds on collections, use
`TreeMap.ceilingKey` / `higherKey`. C++ users know these as `lower_bound` / `upper_bound`.
