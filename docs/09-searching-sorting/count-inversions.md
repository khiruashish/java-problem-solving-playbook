# Count inversions

<span class="badge hard">Hard</span> <span class="badge tag">Merge sort</span> <span class="badge tag">Divide & conquer</span>

## Problem
Count the pairs `i < j` with `a[i] > a[j]`. This measures how far the array is from sorted.
`[8,4,2,1]` → 6 (every pair). `[1,20,6,4,5]` → 5.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: check every pair. O(n²).
2. **Divide & conquer**: inversions = (inside the left half) + (inside the right half) + (**crossing**: left element > right element).
3. The crossing ones are the hard part, until both halves are **sorted**. Then, during the merge, whenever a right element
   `R[j]` is taken before the left element `L[i]`, `R[j]` is smaller than `L[i]` **and every element after it** in the
   left half. That's `mid − i + 1` inversions counted **in one step**.
4. So it's merge sort plus one line of counting, for O(n log n).

</div>

## Merge step counting
```
left  = [2, 4, 8]      right = [1, 3]
take 1 (right) → beats 2, 4, 8  → +3
take 2 (left)
take 3 (right) → beats 4, 8     → +2
take 4, 8
crossing inversions = 5
```

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/CountInversions.java"
```

## Complexity
O(n log n) time, O(n) space. Return a `long`, since n = 100,000 reversed gives about 5·10⁹ inversions, which overflows an
`int`. The tests check exactly this.

## Related
Count of smaller numbers after self (the same merge, tracking original indexes) · Reverse pairs (`a[i] > 2·a[j]`) · A
Fenwick-tree alternative (see [Tries & Advanced](../16-tries-advanced/index.md)).
