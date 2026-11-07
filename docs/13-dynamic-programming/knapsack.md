# 0/1 knapsack

<span class="badge medium">Medium</span> <span class="badge tag">Knapsack</span> <span class="badge tag">2D → 1D</span>

## Problem
Items have a weight and a value, and each can be used **at most once**. Maximise the value with total weight ≤ capacity.
weights `[10,20,30]`, values `[60,100,120]`, capacity 50 → **220** (items 2 and 3).

## Thinking process
<div class="steps" markdown>

1. **Greedy by value/weight ratio fails** for 0/1 (it works for the *fractional* knapsack in the [Greedy](../14-greedy/index.md) topic).
2. **Decision per item**: **skip** it, giving `f(i−1, w)`, or **take** it if it fits, giving `val[i] + f(i−1, w − wt[i])`.
3. **State** = (item index, remaining capacity), so there are n × W states with O(1) work each: **O(n·W)**.
   That's pseudo-polynomial, since it depends on the *value* of W.
4. **2D table**: `dp[i][w]` uses only row `i−1`, so one row is enough.
5. **The direction trick**: in the 1D version, iterate w **downward**. That way `dp[w − wt]` is still the *previous item's*
   value, and each item is used once. Iterating **upward** lets an item be reused, which turns it into the **unbounded** knapsack.

</div>

## 2D table (capacity 0..50 in steps of 10)
| items \ w | 0 | 10 | 20 | 30 | 40 | 50 |
|-----------|---|----|----|----|----|----|
| none | 0 | 0 | 0 | 0 | 0 | 0 |
| (10, 60) | 0 | 60 | 60 | 60 | 60 | 60 |
| (20, 100) | 0 | 60 | 100 | 160 | 160 | 160 |
| (30, 120) | 0 | 60 | 100 | 160 | 180 | **220** |

## Code
```java
--8<-- "src/main/java/com/playbook/dp/Knapsack01.java"
```

## Complexity
| Version | Time | Space |
|---------|------|-------|
| Recursion | O(2ⁿ) | O(n) |
| Memo / 2D table | O(n·W) | O(n·W) |
| 1D | O(n·W) | O(W) |

## The knapsack family
[Partition equal subset](partition-equal-subset.md) · target sum (± signs) · last stone weight II · [coin change](coin-change.md) (unbounded) ·
rod cutting (unbounded).
