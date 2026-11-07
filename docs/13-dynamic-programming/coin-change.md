# Coin change: fewest coins & number of ways

<span class="badge medium">Medium</span> <span class="badge tag">Unbounded knapsack</span>

## Problem
Each coin denomination can be used any number of times.
1. **Fewest coins** to make `amount`, or −1 (LeetCode 322): `[1,2,5]`, 11 → 3 (5 + 5 + 1).
2. **Number of combinations** (LeetCode 518): `[1,2,5]`, 5 → 4.

## Thinking process
<div class="steps" markdown>

1. **Why greedy fails**: with `[1,3,4]` and 6, "largest coin first" gives 4 + 1 + 1 = 3 coins, but 3 + 3 = 2. You have to try every option.
2. **Last decision**: the last coin used was some c, so `f(a) = 1 + min over c of f(a − c)`, with `f(0) = 0`.
3. **State** = the remaining amount alone, giving amount + 1 states × #coins transitions.
4. **Table**: fill `dp[1..amount]` upward. Use `amount + 1` as "infinity", since no answer can use more coins than the amount.
5. **Counting ways, the loop-order subtlety**:
    - **coins outer, amounts inner**: each coin is added in a fixed order, so {1,2} and {2,1} count **once**. Those are **combinations**.
    - **amounts outer, coins inner**: every ordering is counted. Those are **permutations** (5 → 9).

</div>

## Table for min coins, `[1,2,5]`
| a | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 |
|---|---|---|---|---|---|---|---|---|---|---|----|----|
| dp | 0 | 1 | 1 | 2 | 2 | 1 | 2 | 2 | 3 | 3 | 2 | **3** |

## Code
```java
--8<-- "src/main/java/com/playbook/dp/CoinChange.java"
```

## Complexity
O(amount × coins) time and O(amount) space for the memo and table versions. The plain recursion is exponential.
