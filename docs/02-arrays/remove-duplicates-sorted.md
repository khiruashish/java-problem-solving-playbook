# Remove duplicates from a sorted array

<span class="badge easy">Easy</span> <span class="badge tag">Slow/fast pointers</span>

## Problem
In place, keep each value once and return the new length `k`. The first `k` slots hold the result.
`[0,0,1,1,1,2,2,3,3,4]` → `k = 5`, `[0,1,2,3,4,…]`.

## Thinking process
<div class="steps" markdown>

1. **Sorted** means duplicates are adjacent, so you only need to compare with the **last kept** value.
2. `k` = the length of the unique prefix. For each `i`, if `a[i] != a[k-1]`, write it to `a[k++]`.
3. **Generalisation**: to allow at most **m** copies, compare with `a[k-m]`.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/RemoveDuplicatesSorted.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| LinkedHashSet | O(n) | O(n) |
| Two pointers | O(n) | O(1) |
| `Arrays.stream(a).distinct()` | O(n) | O(n), new array |
