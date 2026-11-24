# Koko eating bananas

<span class="badge hard">Hard</span> <span class="badge tag">Binary search on the answer</span> <span class="badge tag">Monotonic predicate</span>

## Problem
There are `piles[i]` bananas in each pile. Each hour Koko picks one pile and eats up to `speed` bananas from it. If the pile has fewer,
she finishes it and waits out the rest of the hour. Return the **minimum integer speed** that lets her finish everything within `h` hours.

## Examples
| piles | h | answer |
|-------|---|--------|
| `[3, 6, 7, 11]` | 8 | 4 |
| `[30, 11, 23, 4, 20]` | 5 | 30 |
| `[30, 11, 23, 4, 20]` | 6 | 23 |

## Clarifying questions
- Is h ≥ the number of piles? It must be, since each pile takes at least one hour. Otherwise there is no answer (the code throws).
- Pile sizes up to 10⁹? Then the hour total can exceed `int`. Sum in a `long`.

## Thinking process
<div class="steps" markdown>

1. **Restate**: find the smallest speed s where `hours(s) = Σ ceil(pile / s) ≤ h`.
2. **Brute force**: try s = 1, 2, 3, … until it fits. Up to `max(pile)` tries of O(n) each, so 10⁹ · n is far too slow.
3. **Monotonic**: a faster speed never takes *more* hours. So the speeds split into `too slow … too slow | ok … ok`, and we want the **first ok**.
4. **That is binary search on the answer.** Search `[1, max pile]` (max pile always works: one hour per pile). If `mid` works, the answer is ≤ mid,
   so set `hi = mid`. Otherwise set `lo = mid + 1`. About 30 iterations of O(n) each.
5. **Tighter bounds**: you can't beat `ceil(total / h)`, so start `lo` there.

</div>

## Dry run: `[3, 6, 7, 11]`, h = 8
| lo | hi | mid | hours(mid) | ≤ 8? |
|----|----|-----|------------|------|
| 1 | 11 | 6 | 1 + 1 + 2 + 2 = 6 | yes → hi = 6 |
| 1 | 6 | 3 | 1 + 2 + 3 + 4 = 10 | no → lo = 4 |
| 4 | 6 | 5 | 1 + 2 + 2 + 3 = 8 | yes → hi = 5 |
| 4 | 5 | 4 | 1 + 2 + 2 + 3 = 8 | yes → hi = 4 |
| 4 | 4 | — | — | answer **4** |

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/KokoEatingBananas.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Try every speed | O(n · max) | O(1) |
| Binary search on the answer | **O(n · log max)** | O(1) |

## The template (reuse it)
```text
lo = smallest possible answer, hi = an answer known to work
while lo < hi:
    mid = lo + (hi - lo) / 2
    if feasible(mid): hi = mid      # mid might be the answer
    else:             lo = mid + 1  # mid is definitely too small
return lo
```

## Edge cases & pitfalls
- Ceil division with `Math.ceil(p / (double) s)` risks floating-point error. Use `(p + s − 1) / s` in `long`.
- `while (lo <= hi)` with `hi = mid` loops forever. Pair `lo < hi` with `hi = mid`.
- Summing hours in `int` overflows for 10⁴ piles of 10⁹ at speed 1.

## Related
[Split array largest sum](split-array-largest-sum.md) (the same template, a different feasibility check) · [Linear & binary search](binary-search.md) · [Constraints → complexity](../00-foundations/constraints-to-complexity.md).
