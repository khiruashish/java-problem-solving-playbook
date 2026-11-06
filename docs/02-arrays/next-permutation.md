# Next permutation

<span class="badge hard">Hard</span> <span class="badge tag">Pivot · swap · reverse</span>

## Problem
Rearrange the array, in place, into the next lexicographically greater permutation. If none exists, return the smallest.
`[1,2,3]` → `[1,3,2]` · `[3,2,1]` → `[1,2,3]` · `[1,1,5]` → `[1,5,1]`.

## Thinking process
<div class="steps" markdown>

1. To get the *next* bigger number, change the **rightmost** position you can.
2. A suffix that is **non-increasing** is already at its maximum, and no rearrangement of it gets bigger.
3. So find the **pivot** `i`: the first index from the right with `a[i] < a[i+1]`.
4. Swap `a[i]` with the **smallest element in the suffix that is greater than it**. Since the suffix is descending, that is the rightmost element `> a[i]`.
5. The suffix is still descending. **Reverse it** to make it ascending, which is the smallest possible arrangement.
6. If there is no pivot, the whole array is descending. Reverse everything to get the smallest permutation.

</div>

## Dry run (`[1,3,5,4,2]`)
```
pivot: from right, 2<4? no, 4<5? no, 5>3 → a[1] = 3 is the pivot (3 < 5)
swap 3 with the rightmost > 3 in [5,4,2] → 4  →  [1,4,5,3,2]
reverse suffix [5,3,2]                         →  [1,4,2,3,5] ✅
```

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/NextPermutation.java"
```

## Complexity
O(n) time, O(1) space.

## Used by
[All permutations, iterative](../01-strings/string-permutations.md) · Next greater number with the same digits (GfG) · Kth permutation.
