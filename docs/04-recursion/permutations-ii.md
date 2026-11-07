# Permutations II (with duplicates)

<span class="badge hard">Hard</span> <span class="badge tag">Backtracking</span> <span class="badge tag">Pruning</span>

## Problem
All **distinct** permutations of an array that may contain duplicates. `[1,1,2]` → `[1,1,2] [1,2,1] [2,1,1]`.

## Thinking process
<div class="steps" markdown>

1. **Generate everything and dedupe with a `Set`**: correct, but `[5,5,5,5,5,5]` builds 720 lists to keep 1.
2. **Insight**: duplicates appear because two equal values can swap roles. Force equal values to always be used **in their
   original (sorted) order**, and each arrangement is produced exactly once.
3. **Rule**: after sorting, skip `a[i]` if `a[i] == a[i−1]` and `a[i−1]` is **not currently used**. That means its twin on the
   left must be placed first.
4. A `used[]` array tracks which indexes are on the current path (choose, explore, un-choose).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/PermutationsII.java"
```

## Complexity
O(n! · n) in the worst case (all distinct), but proportional to the number of *distinct* permutations when there are duplicates.

## Alternatives
The iterative [next permutation](../02-arrays/next-permutation.md) also skips duplicates naturally, and is used by
[string permutations](../01-strings/string-permutations.md).

## Testing note
Compared with the set-based version on 200 random arrays over a 3-value alphabet (lots of duplicates).
