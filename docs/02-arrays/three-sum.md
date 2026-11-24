# 3Sum

<span class="badge medium">Medium</span> <span class="badge tag">Sort + two pointers</span> <span class="badge tag">Dedup</span>

## Problem
Return all **unique** triplets of values `[a, b, c]` with `a + b + c = 0`. A triplet uses three different
indexes, but the *output* must not repeat the same set of values.

## Examples
| Input | Output |
|-------|--------|
| `[-1, 0, 1, 2, -1, -4]` | `[[-1, -1, 2], [-1, 0, 1]]` |
| `[0, 0, 0, 0]` | `[[0, 0, 0]]` |
| `[0, 1, 1]` | `[]` |

## Clarifying questions
- Unique by values or by indexes? By values.
- Does the order of the output matter? Here each triplet is ascending and the list is sorted, so all approaches can be compared directly.
- Can the sum overflow? With values near ±2³¹, yes. The code adds in `long`.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: three nested loops, sort each triplet, put it in a set to remove duplicates. O(n³).
2. **Reduce to two-sum**: fix `a[i]`. Now you need two numbers summing to `−a[i]`. A hash set solves that in O(n), giving O(n²) overall.
3. **Sort first**: on a sorted array, two-sum is a **two-pointer** sweep. If the sum is too small, move `lo` up. If too big, move `hi` down.
4. **Dedup without a set**: after sorting, equal values sit together. Skip an `i` equal to the previous `i`, and after a hit skip
   equal values at `lo` and `hi`. Every triplet is then produced exactly once.
5. **Early exit**: if `a[i] > 0`, every remaining number is positive, so stop.

</div>

## Dry run: sorted `[-4, -1, -1, 0, 1, 2]`
| i (a[i]) | lo | hi | sum | action |
|----------|----|----|-----|--------|
| 0 (−4) | −1 | 2 | −3 | lo++ |
| | … | | always < 0 | no triplet with −4 |
| 1 (−1) | −1 | 2 | 0 | **[-1, -1, 2]**, skip dups, lo++, hi-- |
| | 0 | 1 | 0 | **[-1, 0, 1]** |
| 2 (−1) | — | — | — | same as previous i → skip |
| 3 (0) | 1 | 2 | 3 | hi-- → lo = hi, stop |

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/ThreeSum.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force + set | O(n³) | O(k) for k triplets |
| Fix one + hash set | O(n²) | O(n) |
| Sort + two pointers | **O(n²)** | O(1) extra besides the sort |

O(n²) is essentially optimal here: there can be Θ(n²) triplets in the output.

## Edge cases & pitfalls
- Skipping duplicates **before** recording a hit loses `[-1, -1, 2]`. Skip `i` against `i − 1` (the previous value), never against `i + 1`.
- Forgetting the duplicate skips at `lo`/`hi` after a hit produces repeated triplets.
- Arrays shorter than 3.

## Follow-ups
**3Sum closest** (track the best |sum − target|) · **4Sum** (one more outer loop, O(n³)) · **k-Sum** (recursion down to two-sum).

## Related
[Two sum](two-sum.md) · [4Sum II](../08-hashing/four-sum-count.md) (count, not list, using meet in the middle).
