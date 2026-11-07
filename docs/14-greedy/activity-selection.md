# Activity selection / non-overlapping intervals

<span class="badge medium">Medium</span> <span class="badge tag">Sort by end</span> <span class="badge tag">Exchange argument</span>

## Problem
Pick the **maximum number** of non-overlapping intervals `[start, end)` (touching is allowed).
**Twin** (LeetCode 435): the minimum number to **remove** so the rest don't overlap, which is `n − max kept`.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try every subset, 2ⁿ of them.
2. **DP** O(n²): sort by end, then `dp[i] = 1 + max dp[j]` over compatible j.
3. **Which greedy?** Try the candidates against counter-examples:
    - *shortest first* fails: `[0,5) [4,6) [5,10)` keeps 1 instead of 2.
    - *earliest start* fails: `[0,100) [1,2) [2,3)` keeps 1 instead of 2.
    - **earliest finish** works.
4. **Why earliest finish is safe**: the activity that ends first leaves the most time for everything else. Swap it into any
   optimal solution in place of that solution's first activity, and nothing else conflicts.

</div>

## Dry run (sorted by end)
| Interval | Start ≥ lastEnd? | Kept |
|----------|------------------|------|
| [1,2) | ✅ | 1 |
| [3,4) | ✅ | 2 |
| [0,6) | ❌ | |
| [5,7) | ✅ | 3 |
| [8,9) | ✅ | **4** |
| [5,9) | ❌ | |

## Code
```java
--8<-- "src/main/java/com/playbook/greedy/ActivitySelection.java"
```

## Complexity
Brute force O(2ⁿ·n²) · DP O(n²) · greedy O(n log n).

## Related
Minimum arrows to burst balloons (the same greedy, with closed intervals) · [Meeting rooms / minimum platforms](minimum-platforms.md) ·
[Merge intervals](../02-arrays/merge-intervals.md) (sort by **start** instead).
