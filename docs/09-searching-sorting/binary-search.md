# Linear & binary search

<span class="badge easy">Easy</span> <span class="badge tag">Binary search</span> <span class="badge tag">Recursion</span>

## Problem
Return the index of `target` in a **sorted** array, or −1. `[−5,−1,0,3,8,13,21]`, 8 → 4.
**Bonus**: integer square root (the largest r with r² ≤ x) using binary search on the answer.

## Clarifying questions
Sorted ascending? Duplicates (which index do they want)? See [first & last occurrence](first-last-occurrence.md). Empty array?

## Thinking process
<div class="steps" markdown>

1. **Linear search** checks every element. O(n), and it's the only option for unsorted data.
2. **Sorted insight**: comparing with the middle element tells you **which half cannot contain the target**. Discard it.
3. Halving gives about log₂ n steps. For a million elements that's about 20 comparisons.
4. **Invariant**: "if the target exists, it is inside `[lo, hi]`". Every update must keep that true, which is why
   it's `mid + 1` / `mid − 1` (mid has already been checked).
5. **Recursive form**: the same logic, with the range passed as parameters. The base case is an empty range.

</div>

## Dry run (target 8)
| lo | hi | mid | a[mid] | Action |
|----|----|-----|--------|--------|
| 0 | 6 | 3 | 3 | 3 < 8, go right |
| 4 | 6 | 5 | 13 | 13 > 8, go left |
| 4 | 4 | 4 | 8 | found ✅ |

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/BinarySearch.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Linear | O(n) | O(1) |
| Binary, iterative | O(log n) | O(1) |
| Binary, recursive | O(log n) | O(log n) stack |

## Pitfalls
`while (lo < hi)` with closed bounds misses a single remaining element. `(lo + hi) / 2` overflows.
Setting `lo = mid` with a lower mid loops forever. That's why `sqrt` uses the **upper** mid.

## Follow-ups
Search insert position (lower bound) · Search a 2D sorted matrix (treat it as one array: `mid / cols`, `mid % cols`) ·
Exponential search on an unbounded or infinite array.
