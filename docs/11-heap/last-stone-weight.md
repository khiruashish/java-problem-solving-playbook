# Last stone weight

<span class="badge easy">Easy</span> <span class="badge tag">Max-heap</span> <span class="badge tag">Simulation</span>

## Problem
Each turn, take the **two heaviest** stones, x ≤ y. If x == y both are destroyed. Otherwise x is destroyed and y becomes y − x.
Return the weight of the last remaining stone, or 0 if none remain.

## Examples
| stones | result |
|--------|--------|
| `[2, 7, 4, 1, 8, 1]` | 1 |
| `[3, 3]` | 0 |
| `[5]` | 5 |

## Clarifying questions
- An empty input? Return 0.
- Weights up to? Positive ints. The difference is always smaller than y, so nothing overflows.

## Thinking process
<div class="steps" markdown>

1. **Restate**: a simulation where every step needs "the two largest right now", and the set changes after every step.
2. **Brute force**: sort, take the last two, push back the difference, and sort again. O(n log n) per turn, O(n² log n) total.
3. **Repeated max with insertions** is exactly what a **max-heap** does: `poll` twice, `offer` once, O(log n) each.
4. In Java, `PriorityQueue` is a min-heap. Pass `Collections.reverseOrder()` to make it a max-heap.

</div>

## Dry run: `[2, 7, 4, 1, 8, 1]`
| heap (max first) | smash | push |
|------------------|-------|------|
| 8 7 4 2 1 1 | 8, 7 | 1 |
| 4 2 1 1 1 | 4, 2 | 2 |
| 2 1 1 1 | 2, 1 | 1 |
| 1 1 1 | 1, 1 | — |
| 1 | | answer **1** |

## Code
```java
--8<-- "src/main/java/com/playbook/heap/LastStoneWeight.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Sort every turn | O(n² log n) | O(n) |
| Max-heap | **O(n log n)** | O(n) |

## Edge cases & pitfalls
- `Comparator` written as `(a, b) -> b - a` overflows for extreme values. `Collections.reverseOrder()` is safe.
- `peek()` on an empty heap returns null, and unboxing that to `int` throws. Check `isEmpty()` first.

## Follow-up
**Last stone weight II** (you may smash *any* two stones): that is no longer a heap problem. It becomes "split into two groups with the
smallest difference of sums", which is the [partition DP](../13-dynamic-programming/partition-equal-subset.md).

## Related
[Kth largest](kth-largest.md) · [Task scheduler](task-scheduler.md) · [Binary heap from scratch](binary-heap.md).
