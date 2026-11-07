# 13 · Dynamic Programming

## Concept primer

DP is **recursion plus never solving the same subproblem twice**. A problem is a DP problem when it has:

1. **Optimal substructure**: the answer is built from answers to smaller versions of the same problem.
2. **Overlapping subproblems**: plain recursion would solve the same smaller version many times.

## The 4-step ladder (every page in this topic follows it)

<div class="steps" markdown>

1. **Recursion**: write the brute-force recurrence. Ask: *"what is the last decision?"* (last step, last coin, last item, last character).
2. **Memoization (top-down)**: cache results by the recursion's **changing parameters**. That parameter list *is* your DP state.
3. **Tabulation (bottom-up)**: fill a table in an order where every dependency is already computed. No recursion, so no stack overflow.
4. **Space optimisation**: if row i only reads row i − 1, keep one or two rows.

</div>

!!! tip "Finding the state"
    The state is **the smallest set of facts that fully determines the remaining sub-problem**: an index (`i`), a remaining
    capacity (`w`), two prefix lengths (`i, j`) or an interval (`i, j`). Count the states and the work per state to get the
    complexity: states × transitions.

## The DP families

| Family | State | Examples here |
|--------|-------|---------------|
| 1D linear | `dp[i]` | [Climbing stairs](climbing-stairs.md), [House robber](house-robber.md), [LIS](longest-increasing-subsequence.md), [Word break](word-break.md) |
| Unbounded knapsack | `dp[amount]` | [Coin change](coin-change.md) |
| 0/1 knapsack | `dp[i][w]` → `dp[w]` (iterate w **down**) | [0/1 Knapsack](knapsack.md), [Partition equal subset](partition-equal-subset.md) |
| Grid | `dp[r][c]` | [Unique paths](unique-paths.md) |
| Two strings | `dp[i][j]` over prefixes | [LCS](longest-common-subsequence.md), [Edit distance](edit-distance.md) |
| Interval | `dp[i][j]` over a range, by increasing length | [Matrix chain](matrix-chain-multiplication.md), [Burst balloons](burst-balloons.md) |

## Problems (Easy → Expert)

| # | Problem | Level | Recurrence in one line |
|---|---------|-------|------------------------|
| 1 | [Climbing stairs](climbing-stairs.md) | <span class="badge easy">Easy</span> | `f(n) = f(n−1) + f(n−2)` |
| 2 | [House robber](house-robber.md) | <span class="badge easy">Easy</span> | `f(i) = max(f(i−1), a[i] + f(i−2))` |
| 3 | [Coin change: min coins & ways](coin-change.md) | <span class="badge medium">Medium</span> | `f(a) = 1 + min f(a − c)` |
| 4 | [Longest increasing subsequence](longest-increasing-subsequence.md) | <span class="badge medium">Medium</span> | `f(i) = 1 + max f(j), j < i, a[j] < a[i]` |
| 5 | [Unique paths](unique-paths.md) | <span class="badge medium">Medium</span> | `f(r,c) = f(r−1,c) + f(r,c−1)` |
| 6 | [0/1 knapsack](knapsack.md) | <span class="badge medium">Medium</span> | `f(i,w) = max(skip, take)` |
| 7 | [Longest common subsequence](longest-common-subsequence.md) | <span class="badge hard">Hard</span> | match: `1 + f(i−1,j−1)`; else `max(f(i−1,j), f(i,j−1))` |
| 8 | [Edit distance](edit-distance.md) | <span class="badge hard">Hard</span> | `1 + min(replace, delete, insert)` |
| 9 | [Partition equal subset sum](partition-equal-subset.md) | <span class="badge hard">Hard</span> | subset sum to total/2 |
| 10 | [Word break](word-break.md) | <span class="badge expert">Expert</span> | `f(i) = ∃ j: f(j) ∧ s[j..i) ∈ dict` |
| 11 | [Matrix chain multiplication](matrix-chain-multiplication.md) | <span class="badge expert">Expert</span> | `min over split k` |
| 12 | [Burst balloons](burst-balloons.md) | <span class="badge expert">Expert</span> | choose the **last** balloon to burst |

Also DP elsewhere: [Fibonacci](../04-recursion/fibonacci.md) · [Kadane](../02-arrays/max-subarray-sum.md) ·
[Longest palindromic substring](../01-strings/longest-palindromic-substring.md) · [Buy & sell stock](../02-arrays/buy-sell-stock.md).
