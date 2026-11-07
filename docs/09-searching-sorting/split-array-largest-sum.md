# Split array largest sum / allocate minimum pages

<span class="badge expert">Expert</span> <span class="badge tag">Binary search on the answer</span> <span class="badge tag">Greedy check</span>

## Problem
Split a non-negative array into **k contiguous** non-empty parts so that the **largest part sum is as small as possible**.
`[7,2,5,10,8]`, k = 2 → 18 (`[7,2,5] | [10,8]`).
The same problem appears as **Allocate minimum pages** (GfG, books → students), **Painter's partition**, and **Capacity to ship within D days**.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try every position for the first cut and recurse on the rest. Exponential.
2. **Flip the question**: instead of "what is the best split?", ask "**can it be done** with every part ≤ L?"
3. The check is **greedy** and O(n): fill the current part until adding the next element would exceed L, then start a new
   part. If the number of parts needed is ≤ k, then L is feasible.
4. Feasibility is **monotone**: if L works, every larger L also works. Monotone yes/no means you can binary search for the
   smallest feasible L.
5. **Bounds**: L ≥ the max element (a part must hold it) and L ≤ the total sum (everything in one part).

</div>

## Search trace for `[7,2,5,10,8]`, k = 2 (range 10..32)
| L | Greedy parts | Parts | ≤ 2? |
|---|--------------|-------|------|
| 21 | [7,2,5] [10,8] | 2 | ✅ go lower |
| 15 | [7,2,5] [10] [8] | 3 | ❌ go higher |
| 18 | [7,2,5] [10,8] | 2 | ✅ |
| 17 | [7,2,5] [10] [8] | 3 | ❌ → answer **18** |

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/SplitArrayLargestSum.java"
```

## Complexity
O(n · log(sum)) time and O(1) space, versus exponential for brute force. A DP alternative takes O(k·n²).

## Spot the pattern
"**Minimise the maximum**" or "**maximise the minimum**", with a monotone feasibility check, means binary search on the answer.
Other examples: Koko eating bananas · Aggressive cows (maximise the minimum distance) · Minimum days to make m bouquets.
