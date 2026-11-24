# Buy & sell stock with cooldown

<span class="badge medium">Medium</span> <span class="badge tag">State machine DP</span> <span class="badge tag">O(1) space</span>

## Problem
Given daily prices, make as many buy/sell transactions as you like, but hold **at most one share**, and **after you sell you must skip
the next day** before buying again. Return the maximum profit.

## Examples
| prices | profit | how |
|--------|--------|-----|
| `[1, 2, 3, 0, 2]` | 3 | buy 1, sell 2, cooldown, buy 0, sell 2 |
| `[1]` | 0 | |
| `[5, 4, 3]` | 0 | never buy |

## Clarifying questions
- Can I buy and sell on the same day? No.
- Does the cooldown follow a **sell** or a buy? Only a sell.

## Thinking process
<div class="steps" markdown>

1. **Recursion over decisions**: on each day you're either holding or not. If holding: skip, or sell (and jump **2** days ahead, because of the cooldown).
   If not holding: skip, or buy. Exponential.
2. **Overlapping states**: the future only depends on `(day, holding)`, which is just 2n states. Memoise them and it is O(n).
3. **Name the states at the end of each day**, and the DP becomes a small state machine:
   - `hold`: I own a share,
   - `sold`: I sold **today** (so tomorrow I can't buy),
   - `rest`: I own nothing and I'm free to buy.
4. **Transitions**: `hold' = max(hold, rest − p)`, `sold' = hold + p`, `rest' = max(rest, sold)`.
   The cooldown lives in one detail: you can only buy **from `rest`**, never straight from `sold`.

</div>

## State diagram
```
        buy (−p)            sell (+p)
 rest ───────────▶ hold ───────────▶ sold
  ▲ ⟲ wait          ⟲ keep             │
  └────────────── cooldown day ◀───────┘
```

## Dry run: `[1, 2, 3, 0, 2]`
| day | price | hold | sold | rest |
|-----|-------|------|------|------|
| 0 | 1 | −1 | −∞ | 0 |
| 1 | 2 | −1 | 1 | 0 |
| 2 | 3 | −1 | 2 | 1 |
| 3 | 0 | **1** (rest 1 − 0) | −1 | 2 |
| 4 | 2 | 1 | **3** | 2 |

Answer: max(sold, rest) = **3**.

## Code
```java
--8<-- "src/main/java/com/playbook/dp/StockWithCooldown.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Recursion | O(2ⁿ) | O(n) |
| Memo over (day, holding) | O(n) | O(n) |
| State machine | **O(n)** | **O(1)** |

## Edge cases & pitfalls
- Updating `hold` from the *new* `rest` lets you buy on the day right after a sale. Save the previous values first, as the code does.
- Initialise `sold` to a very negative number (you can't have sold on day 0). Use `MIN_VALUE / 2` so adding a price can't overflow.

## The whole stock family, one framework
| Variant | Change to the state machine |
|---------|-----------------------------|
| [One transaction](../02-arrays/buy-sell-stock.md) | buy only from a profit of 0 |
| Unlimited | no `sold` state, so `hold' = max(hold, rest − p)` |
| **Cooldown** | buy only from `rest` (this page) |
| Transaction fee | `sold' = hold + p − fee` |
| At most k transactions | add a transaction count to the state |

## Related
[House robber](house-robber.md) (the same "skip a day" structure) · [Climbing stairs](climbing-stairs.md).
