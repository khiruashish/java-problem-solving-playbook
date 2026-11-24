# Egg drop

<span class="badge expert">Expert</span> <span class="badge tag">Minimax DP</span> <span class="badge tag">Binary search in DP</span> <span class="badge tag">Invert the question</span>

## Problem
You have **k eggs** and a building with **n floors**. There is a highest floor `f` (0 ≤ f ≤ n) from which an egg survives a drop.
An egg that survives can be reused, and a broken one can't. Find the **minimum number of drops that guarantees** you find `f`, whatever it is.

## Examples
| k | n | answer |
|---|---|--------|
| 1 | 2 | 2 (floor by floor) |
| 2 | 6 | 3 |
| 2 | 100 | **14**, the classic puzzle |
| 3 | 14 | 4 |

## Clarifying questions
- "Guarantee" means the **worst case** over all possible f. That is a minimax.
- Size? n up to 10⁴ (or 10⁹ for the moves formulation), k up to 100.

## Thinking process
<div class="steps" markdown>

1. **One egg**: you can't risk skipping floors, so you need n drops.
2. **Recurrence**: drop from floor x. If it **breaks**, search floors below x with e − 1 eggs. If it **survives**, search floors above x with e eggs.
   You pay for the worse case, and you choose the best x:
   `dp[e][f] = 1 + min over x of max(dp[e−1][x−1], dp[e][f−x])`. That is O(k · n²).
3. **Binary search the x**: `dp[e−1][x−1]` grows with x and `dp[e][f−x]` shrinks with x. The max of the two is smallest where they cross, so
   binary search for that crossing: O(k · n log n).
4. **Invert the question**: instead of "the fewest moves for n floors", ask "**the most floors I can cover with m moves and e eggs**":
   `cover(m, e) = cover(m−1, e−1) + 1 + cover(m−1, e)`. The floors below if it breaks, plus this floor, plus the floors above if it survives.
   Increase m until `cover(m, k) ≥ n`. That is O(k · m), and m is tiny (14 for 2 eggs and 100 floors, 30 for 10⁹ floors with plenty of eggs).

</div>

## The moves table: cover[m][e]
| moves m | 1 egg | 2 eggs | 3 eggs |
|---------|-------|--------|--------|
| 1 | 1 | 1 | 1 |
| 2 | 2 | 3 | 3 |
| 3 | 3 | 6 | 7 |
| 4 | 4 | 10 | **14** |
| … | | | |
| 14 | 14 | **105** ≥ 100 | |

With 2 eggs, 13 moves cover 91 floors and 14 moves cover 105, so 100 floors need **14** drops.

## Code
```java
--8<-- "src/main/java/com/playbook/dp/EggDrop.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Classic minimax DP | O(k · n²) | O(k · n) |
| DP + binary search on x | O(k · n log n) | O(k · n) |
| Moves DP (inverted) | **O(k · m)**, m ≤ n | **O(k)** |

## Edge cases & pitfalls
- n = 0 needs 0 drops. k = 1 needs n drops.
- `cover` grows fast. Keep it in a `long`.
- In the inverted DP, update `e` from **high to low** so `cover[e−1]` still holds the m − 1 value.

## Why it's "Expert"
The first DP is natural but too slow. Seeing that the max of an increasing and a decreasing function can be binary-searched, or
**flipping what the DP computes**, is the kind of leap that separates expert answers.

## Related
[Koko eating bananas](../09-searching-sorting/koko-eating-bananas.md) (another "flip the question" binary search) · [Matrix chain multiplication](matrix-chain-multiplication.md) (minimax over a split point) · [Constraints → complexity](../00-foundations/constraints-to-complexity.md).
