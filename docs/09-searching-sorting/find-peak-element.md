# Find a peak element

<span class="badge hard">Hard</span> <span class="badge tag">Binary search on a slope</span>

## Problem
A peak is strictly greater than its neighbours. Treat out-of-bounds neighbours as −∞, and adjacent values are never equal.
Return the index of **any** peak in O(log n). `[1,2,1,3,5,6,4]` → 1 or 5.

## Thinking process
<div class="steps" markdown>

1. **Linear**: the first i with `a[i] > a[i+1]` is a peak, because everything before it was climbing. O(n).
2. **Why binary search works on an unsorted array**: look at the slope at mid.
    - If `a[mid] < a[mid+1]`, you're going **uphill**. Keep walking right: either it keeps rising to the end (then the last
      element is a peak, since `a[n] = −∞`) or it turns down somewhere, and that turn is a peak. **A peak must exist to the right.**
    - Otherwise you're going downhill or standing on a peak, so a peak exists at mid or to its left.
3. Every step keeps a half that is **guaranteed** to contain a peak. That guarantee is all binary search needs.
   Being sorted is just one way to get it.

</div>

## Picture
```
        6
      5   4        mid at 3 (value 3): a[3] < a[4] → uphill → go right
  2     3
1   1
```

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/FindPeakElement.java"
```

## Complexity
Linear O(n). Binary O(log n), with O(1) space iteratively or O(log n) stack recursively.

## Related
Peak in a 2D grid (binary search on columns, taking each column's max) · Mountain array (find the single peak, then
binary search each side) · Find the minimum in a rotated array (the same "which side is guaranteed" reasoning).
