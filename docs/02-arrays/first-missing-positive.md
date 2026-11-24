# First missing positive

<span class="badge expert">Expert</span> <span class="badge tag">Cyclic sort</span> <span class="badge tag">Index as hash</span> <span class="badge tag">O(1) space</span>

## Problem
Return the smallest positive integer that does **not** appear in an unsorted array. You must use **O(n) time
and O(1) extra space**.

## Examples
| Input | Output |
|-------|--------|
| `[1, 2, 0]` | 3 |
| `[3, 4, -1, 1]` | 2 |
| `[7, 8, 9, 11, 12]` | 1 |
| `[]` | 1 |

## Clarifying questions
- May I modify the input? The O(1)-space solutions need to (or you copy it, which costs O(n)).
- Duplicates and negatives? Both allowed, and both must be ignored.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try 1, 2, 3, … and scan for each one. O(n²).
2. **Hash set**: O(n) time, but O(n) space. **Sorting**: O(n log n). Neither meets the bound.
3. **Key bound**: with n numbers, the answer is in **[1, n + 1]**. Values ≤ 0 or > n can never be the answer, so ignore them.
4. **Use the array as its own hash table**: value v "belongs" at index v − 1. Swap each in-range value to its home
   (**cyclic sort**). Afterwards, the first index i holding something other than i + 1 means i + 1 is missing.
5. **Why O(n)**: every swap puts one value in its final home, and it never moves again. So there are at most n swaps in total, even with the inner `while`.

</div>

## Dry run (cyclic sort): `[3, 4, -1, 1]`
| i | before | action | after |
|---|--------|--------|-------|
| 0 | `3 4 -1 1` | 3 → index 2 | `-1 4 3 1` |
| 0 | `-1 4 3 1` | −1 out of range, next i | |
| 1 | `-1 4 3 1` | 4 → index 3 | `-1 1 3 4` |
| 1 | `-1 1 3 4` | 1 → index 0 | `1 -1 3 4` |
| 1 | `1 -1 3 4` | −1 out of range | |
| 2, 3 | | already home | |
| scan | `1 -1 3 4` | index 1 holds −1 | answer **2** |

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/FirstMissingPositive.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n²) | O(1) |
| Hash set | O(n) | O(n) |
| Sorting | O(n log n) | O(n) for the copy |
| Cyclic sort | **O(n)** | **O(1)** |
| Sign marking | **O(n)** | **O(1)** |

## Edge cases & pitfalls
- **Duplicates loop forever** if the swap condition is `a[i] != i + 1`. Check `a[a[i] − 1] != a[i]` (the home already has this value) instead.
- In sign marking, read `Math.abs(a[i])`: the slot may already have been negated.
- All of 1..n present → the answer is n + 1.

## Why it's "Expert"
The code is short, but it needs two insights at once: bounding the answer to [1, n + 1], and reusing the input as a hash table without losing information.

## Related
[Missing number](missing-number.md) · [Contains duplicate](../08-hashing/contains-duplicate.md) (the same "index as hash" trick finds duplicates in O(1) space).
