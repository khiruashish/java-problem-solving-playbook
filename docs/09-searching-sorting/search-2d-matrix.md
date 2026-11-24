# Search a 2D matrix (I & II)

<span class="badge medium">Medium</span> <span class="badge tag">Binary search</span> <span class="badge tag">Staircase search</span>

## Problem
Return whether `target` is in the matrix. There are two common versions:

- **I, fully sorted**: rows are sorted, and each row starts above the previous row's last value. Reading row by row gives one sorted list.
- **II, row- and column-sorted**: each row is sorted left to right and each column top to bottom, but rows can overlap.

## Examples
```
I:  1  3  5  7          II:  1  4  7 11 15
   10 11 16 20               2  5  8 12 19
   23 30 34 60               3  6  9 16 22
                            10 13 14 17 24
target 3 → true             18 21 23 26 30
target 13 → false           target 5 → true, target 20 → false
```

## Clarifying questions
- Which kind of sortedness? It decides between O(log mn) and O(m + n).
- Can the matrix be empty or have empty rows? Return false.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: scan all m·n cells. Either version, O(mn).
2. **I is a sorted array in disguise.** Index k of the virtual array is cell `(k / n, k % n)`. Run one binary search over `[0, m·n − 1]`.
3. **II isn't**: `[[1, 4], [2, 5]]` read row by row is `1 4 2 5`, which is not sorted. Binary search on the flattened matrix fails.
4. **Staircase insight for II**: stand at the **top-right** corner. Every value to its left is smaller and every value below is larger.
   If the corner is bigger than the target, the whole column is too big, so move left. If it's smaller, the whole row is too small, so move down.
   Each step throws away a row or a column: at most m + n steps.
5. The bottom-left corner works the same way. The top-left and bottom-right corners don't: from those, both directions increase (or both decrease), so a comparison tells you nothing.

</div>

## Dry run (II, staircase), target 5
| at (r, c) | value | compare | move |
|-----------|-------|---------|------|
| (0, 4) | 15 | > 5 | left |
| (0, 3) | 11 | > 5 | left |
| (0, 2) | 7 | > 5 | left |
| (0, 1) | 4 | < 5 | down |
| (1, 1) | 5 | = | **found** |

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/Search2DMatrix.java"
```

## Complexity
| Approach | Works for | Time | Space |
|----------|-----------|------|-------|
| Scan | I and II | O(m·n) | O(1) |
| Virtual-array binary search | I | **O(log(m·n))** | O(1) |
| Staircase | I and II | **O(m + n)** | O(1) |
| Binary search per row | I and II | O(m log n) | O(1) |

## Edge cases & pitfalls
- `mid / cols` and `mid % cols`: divide by the number of **columns**, not rows.
- Staircase loop bounds: `r < m && c >= 0`.
- Random tests compare every target in range (including just below the minimum and just above the maximum) against a full scan.

## Related
[Linear & binary search](binary-search.md) · [Rotated sorted array](rotated-sorted-array.md) · [Find peak element](find-peak-element.md).
