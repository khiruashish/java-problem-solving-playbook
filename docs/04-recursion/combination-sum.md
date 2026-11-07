# Combination sum I & II

<span class="badge hard">Hard</span> <span class="badge tag">Backtracking</span> <span class="badge tag">Duplicate skipping</span>

## Problem
- **I**: distinct candidates, **each reusable**. All combinations summing to target. `[2,3,6,7]`, 7 → `[2,2,3]`, `[7]`.
- **II**: candidates **may repeat**, **each used at most once**, and no duplicate combinations.
  `[10,1,2,7,6,1,5]`, 8 → `[1,1,6] [1,2,5] [1,7] [2,6]`.

## Thinking process
<div class="steps" markdown>

1. This uses the [subsets template](subsets.md) with a running `remaining` total. Record when it hits 0, and stop when it goes negative.
2. **Avoid permutations of the same combination** (`[2,3,2]` vs `[2,2,3]`): pass a `start` index so you never pick an
   earlier candidate again. Combinations are then built in non-decreasing order.
3. **I, reuse allowed**: recurse with `i` (the same index), not `i + 1`.
4. **Sort first**, so you can `break` as soon as a candidate exceeds `remaining`. Every later one is bigger.
5. **II, the duplicate-skip rule**: at the **same depth**, skip a value equal to the previous one
   (`i > start && c[i] == c[i−1]`). Equal values are still allowed at *deeper* levels, which is how `[1,1,6]` survives.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/CombinationSum.java"
```

## Complexity
Exponential in the worst case, bounded by the number of combinations times their length. Sorting and the `break` prune
dramatically in practice.

## Testing note
Version II is compared with a brute force over all 2ⁿ subsets (deduplicated) on 200 random inputs. It must produce the same
set **and** contain no duplicates. Every combination returned by version I is checked to sum to the target.
