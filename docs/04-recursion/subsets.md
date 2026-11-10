# Subsets / power set

<span class="badge hard">Hard</span> <span class="badge tag">Backtracking</span> <span class="badge tag">Bitmask</span>

## Problem
Return all 2ⁿ subsets of distinct integers. `[1,2,3]` → `[], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]`.

## Thinking process
<div class="steps" markdown>

1. Each element is either **in or out**. That's a binary decision tree with depth n and 2ⁿ leaves.
2. **Pick/skip recursion**: at index i, recurse without `a[i]`, then with `a[i]`, then backtrack.
3. **Start-index backtracking**: every node is an answer. Loop `i` from `start`. This is the template for **combinations**.
4. **Iterative cascade**: start with `[[]]`. For each x, copy every existing subset and add x to the copy.
5. **Bitmask**: the numbers `0..2ⁿ−1` *are* the subsets. Bit j means "include a[j]".

</div>

## Decision tree (pick/skip)
```
                     []
            skip 1 /    \ pick 1
                 []      [1]
          skip 2 / \    / \
               []  [2] [1] [1,2]
               /\   /\  /\   /\
             [] [3] …                (8 leaves)
```

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/subsets.html"

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/Subsets.java"
```

## Complexity
O(n · 2ⁿ) time, which is the output size. O(n) recursion depth.

## Variations
Subsets II (with duplicates: sort, then skip `a[i] == a[i−1]` when `i > start`) · Combination sum · Subsets with a given sum · K-combinations.
