# Best time to buy & sell stock

<span class="badge medium">Medium</span> <span class="badge tag">Running minimum</span> <span class="badge tag">Greedy</span>

## Problem
`prices[i]` is the price on day i. Buy once and sell later to maximise profit, or return 0 if no profit is possible.
`[7,1,5,3,6,4]` → 5 (buy at 1, sell at 6).

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try every `(buy, sell)` pair with `buy < sell`. That's O(n²).
2. **Insight**: if you sell on day j, the best buy is the **minimum price before j**.
3. So keep a running `minSoFar`, and each day compute `price − minSoFar`. That's O(n) and O(1).
4. **Unlimited transactions**: collect every rise from one day to the next. Summing the up-moves equals buying at every valley and selling at every peak.

</div>

## Dry run
| day | price | minSoFar | profit today | best |
|-----|-------|----------|--------------|------|
| 0 | 7 | 7 | 0 | 0 |
| 1 | 1 | 1 | 0 | 0 |
| 2 | 5 | 1 | 4 | 4 |
| 4 | 6 | 1 | **5** | **5** |

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/buy-sell-stock.html"

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/BuySellStock.java"
```

## The series
I (one transaction) ✅ · II (unlimited) ✅ · III / IV (at most k transactions, a DP over states) · with cooldown · with a transaction fee. These are covered in [DP](../13-dynamic-programming/index.md).
