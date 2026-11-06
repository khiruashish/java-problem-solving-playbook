# Find max & min of an array

<span class="badge easy">Easy</span> <span class="badge tag">Linear scan</span> <span class="badge tag">Divide & conquer</span>

## Problem
Return `{min, max}` of a non-empty array. GfG asks for the version with the **minimum number of comparisons**.

## Thinking process
<div class="steps" markdown>

1. **Sorting** gives the min and max at the ends, but O(n log n) is overkill.
2. **One scan**: update `min` and `max` per element. That's O(n) and about 2n comparisons.
3. **Pairwise trick**: compare the two elements of a pair with each other first, then the smaller with `min` and the larger with `max`.
   That's 3 comparisons per 2 elements, about **1.5n**.
4. **Divide & conquer**: solve each half and combine. It also takes about 1.5n comparisons, and it's a nice recursion example.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/MaxMin.java"
```

## Complexity
| Approach | Time | Comparisons | Space |
|----------|------|-------------|-------|
| Sort | O(n log n) | — | O(n) copy |
| Linear | O(n) | ~2n | O(1) |
| Pairwise | O(n) | ~1.5n | O(1) |
| Recursive | O(n) | ~1.5n | O(log n) stack |
| `IntSummaryStatistics` | O(n) | ~2n | O(1) |

## Pitfall
Initialising with `0` breaks for all-negative arrays. Initialise with `a[0]` or `Integer.MAX_VALUE`/`MIN_VALUE`.
