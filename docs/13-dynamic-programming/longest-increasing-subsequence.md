# Longest increasing subsequence

<span class="badge medium">Medium</span> <span class="badge tag">1D DP</span> <span class="badge tag">Binary search</span> <span class="badge tag">Patience sorting</span>

## Problem
Return the length of the longest **strictly** increasing subsequence (not necessarily contiguous).
`[10,9,2,5,3,7,101,18]` → 4 (`2,3,7,18`).

## Thinking process
<div class="steps" markdown>

1. **Recursion**: for each element, skip it or take it (if it's bigger than the last one taken). O(2ⁿ).
2. **DP O(n²)**: `dp[i]` = the length of the LIS **ending exactly at i** = `1 + max(dp[j])` over j < i with `a[j] < a[i]`.
   The answer is `max(dp)`.
3. **O(n log n) insight**: for each length k, only the **smallest possible tail** matters, because a smaller tail is easier to extend.
   Keep `tails[k]`, which is always **sorted**. For each x, binary-search the first tail ≥ x and **replace** it with x.
   If x is bigger than every tail, **append** it.
4. Careful: `tails` is **not** an actual LIS. Only its **length** is the answer. (Rebuilding the sequence needs parent pointers.)

</div>

## Patience trace
| x | tails | |
|---|-------|-|
| 10 | [10] | |
| 9 | [9] | replace |
| 2 | [2] | replace |
| 5 | [2, 5] | append |
| 3 | [2, 3] | replace |
| 7 | [2, 3, 7] | append |
| 101 | [2, 3, 7, 101] | append |
| 18 | [2, 3, 7, 18] | replace → length **4** |

## Code
```java
--8<-- "src/main/java/com/playbook/dp/LongestIncreasingSubsequence.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Recursion | O(2ⁿ) | O(n) |
| DP | O(n²) | O(n) |
| Patience | O(n log n) | O(n) |

## Family
Russian doll envelopes (sort by width ascending and height descending, then LIS on height) · Longest chain of pairs ·
Number of LIS · Minimum deletions to make an array sorted (`n − LIS`).
