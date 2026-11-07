# Climbing stairs

<span class="badge easy">Easy</span> <span class="badge tag">1D DP</span> <span class="badge tag">Recursion → memo → table → O(1)</span>

## Problem
You can climb 1 or 2 steps at a time. How many distinct ways are there to reach step n? n = 3 → 3 (`1+1+1`, `1+2`, `2+1`).
**Generalisation**: any set of allowed step sizes.

## Thinking process
<div class="steps" markdown>

1. **Last decision**: the final move onto step n was a 1-step (from n−1) or a 2-step (from n−2). Those two groups don't overlap and
   together cover every way, so `f(n) = f(n−1) + f(n−2)` with `f(0) = f(1) = 1`. It's Fibonacci.
2. **Recursion** recomputes the same values exponentially often, so cache them (**memo**), which is O(n).
3. **Table**: `dp[i]` only needs `dp[i−1]` and `dp[i−2]`, so fill it left to right.
4. **O(1) space**: keep just the two previous values.
5. **Any step set** {s₁…sₖ}: `dp[i] = Σ dp[i − s]`, for O(n·k).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/dp/ClimbingStairs.java"
```

## Complexity
| Version | Time | Space |
|---------|------|-------|
| Recursive | O(2ⁿ) | O(n) stack |
| Memo | O(n) | O(n) |
| Table | O(n) | O(n) |
| Optimal | O(n) | O(1) |

## Follow-ups
Min-cost climbing stairs (`dp[i] = cost[i] + min(dp[i−1], dp[i−2])`) · Decode ways ("12" → "AB" or "L") · Tiling a 2×n board.
