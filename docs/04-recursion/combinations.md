# Combinations (n choose k)

<span class="badge medium">Medium</span> <span class="badge tag">Backtracking</span> <span class="badge tag">Pruning</span> <span class="badge tag">Iterative odometer</span>

## Problem
Return all combinations of **k** numbers chosen from **1..n**. Each combination is ascending, and the list is in lexicographic order.

## Examples
| n, k | Output |
|------|--------|
| 4, 2 | `[1,2] [1,3] [1,4] [2,3] [2,4] [3,4]` |
| 3, 3 | `[1,2,3]` |
| 3, 0 | `[]` (one empty combination) |
| 2, 3 | none |

## Clarifying questions
- Is order inside a combination irrelevant? Yes, `[1,2]` and `[2,1]` are the same combination, so emit it ascending only.
- k = 0 → one empty combination. k > n → none.

## Thinking process
<div class="steps" markdown>

1. **Restate**: choose a size-k subset. That is the [subsets](subsets.md) tree, stopped as soon as the path has k items.
2. **Avoid duplicates by construction**: pick numbers in increasing order, using a `start` index. Never look back.
3. **Prune**: if `need` more numbers are still required, the current pick can be at most `n − need + 1`. Branches that can't
   be completed are never entered, so every leaf is a real answer.
4. **Iterative alternative (odometer)**: keep indices `c[0] < … < c[k−1]`. Find the rightmost one that can still grow, bump it,
   and reset everything to its right to consecutive values.

</div>

## Recursion tree: n = 4, k = 2
```
                    []
        ┌───────────┼───────────┐
       [1]         [2]         [3]        ← x ≤ n − need + 1 = 3, so [4] is never tried
     ┌──┼──┐      ┌──┴──┐        │
  [1,2][1,3][1,4][2,3][2,4]   [3,4]
```

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/Combinations.java"
```

## Complexity
| Approach | Time | Space (excluding output) |
|----------|------|--------------------------|
| Backtracking + pruning | O(C(n,k) · k) | O(k) recursion |
| Include / exclude | O(C(n,k) · k) with the "enough left" check | O(n) recursion |
| Iterative odometer | O(C(n,k) · k) | O(k) |
| Bitmask brute force | O(2ⁿ · n) | O(n) |

Copying each answer costs k, and there are C(n, k) answers, so O(C(n,k) · k) is optimal.

## Edge cases & pitfalls
- Adding `path` itself instead of `new ArrayList<>(path)` fills the result with references to one list, which is empty at the end.
- Without pruning, the search still works but explores dead branches. For k close to n, that is most of the tree.
- `count(n, k)`: multiply and divide in alternation (`r = r · (n − k + i) / i`). Each step is an exact binomial, so there is no rounding, and `long` holds up to C(60, 30).

## Related
[Subsets](subsets.md) · [Combination sum](combination-sum.md) · [Bitmask subsets](../15-bit-manipulation/bitmask-subsets.md).
