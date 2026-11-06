# Fibonacci: naive → memo → iterative → matrix

<span class="badge medium">Medium</span> <span class="badge tag">Tree recursion</span> <span class="badge tag">Memoization</span> <span class="badge tag">DP</span>

## Problem
`F(0) = 0`, `F(1) = 1`, `F(n) = F(n−1) + F(n−2)`. Compute F(n), and the first k terms.

## Thinking process
<div class="steps" markdown>

1. **Naive recursion** is a direct translation of the definition. It makes two calls per level, which gives **O(2ⁿ)** (F(45) already takes seconds).
2. **Waste**: the same subproblems are solved again and again (draw the tree on the
   [mental model](../00-foundations/recursion-mental-model.md) page).
3. **Memoization (top-down DP)**: cache each F(k). Every value is computed once, so O(n).
4. **Bottom-up (normal solution)**: you only ever need the last two values, so the cost is O(n) time and **O(1)** space.
5. **Expert**: `[[1,1],[1,0]]ⁿ = [[F(n+1),F(n)],[F(n),F(n−1)]]`. Fast matrix power gives **O(log n)**.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/Fibonacci.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Naive | O(2ⁿ) (≈ φⁿ) | O(n) stack |
| Memo | O(n) | O(n) |
| Iterative | O(n) | O(1) |
| Matrix power | O(log n) | O(1) |

## Same recurrence elsewhere
Climbing stairs (1 or 2 steps) · Tiling a 2×n board · Counting binary strings without consecutive 1s.
