# Subarray sums divisible by K

<span class="badge hard">Hard</span> <span class="badge tag">Prefix sum</span> <span class="badge tag">Remainder buckets</span> <span class="badge tag">Negative modulo</span>

## Problem
Count the non-empty **contiguous** subarrays whose sum is divisible by k (k ≥ 1). Values can be **negative**.

## Examples
| a | k | Output |
|---|---|--------|
| `[4, 5, 0, -2, -3, 1]` | 5 | 7 |
| `[5]` | 9 | 0 |
| `[-5]` | 5 | 1 |

The 7 subarrays for the first example: `[4,5,0,-2,-3,1]`, `[5]`, `[5,0]`, `[5,0,-2,-3]`, `[0]`, `[0,-2,-3]`, `[-2,-3]`.

## Clarifying questions
- Negative values? Yes. This is exactly where most solutions break.
- Count or list? Count. Listing them could be O(n²) output.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: every (i, j) with a running sum. O(n²).
2. **Prefix sums**: `sum(i..j) = P[j+1] − P[i]`. That is divisible by k exactly when **`P[j+1] mod k == P[i] mod k`**.
3. **So it is a counting problem**: walking left to right, each new prefix pairs with every **earlier** prefix that has the same remainder.
   Keep a count per remainder. Seed `remainder 0 → 1` for the empty prefix, so subarrays starting at index 0 are counted.
4. **The trap**: in Java, `-1 % 5 == -1`, not 4. Two prefixes with remainders −1 and 4 are congruent but would land in different buckets.
   Normalise with `((p % k) + k) % k` or `Math.floorMod(p, k)`.
5. **Closed form**: a bucket holding c prefixes contributes C(c, 2) pairs, so sum `c·(c−1)/2` over the buckets.

</div>

## Dry run: `[4, 5, 0, -2, -3, 1]`, k = 5
| x | prefix | floorMod | earlier prefixes with that remainder | count |
|---|--------|----------|--------------------------------------|-------|
| — | 0 | 0 | (seed) | 0 |
| 4 | 4 | 4 | 0 | 0 |
| 5 | 9 | 4 | 1 | 1 |
| 0 | 9 | 4 | 2 | 3 |
| −2 | 7 | 2 | 0 | 3 |
| −3 | 4 | 4 | 3 | 6 |
| 1 | 5 | 0 | 1 (the seed) | **7** |

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/SubarraysDivisibleByK.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n²) | O(1) |
| Prefix remainders (array) | **O(n)** | O(k) |
| Prefix remainders (map) | **O(n)** | O(min(n, k)) |
| C(c, 2) per bucket | O(n + k) | O(k) |

## Edge cases & pitfalls
- Raw `%` with negative prefixes. The random tests use values in −10..10 to catch this.
- Forgetting the seed `seen[0] = 1` misses every subarray that starts at index 0.
- Sum overflow on long arrays: keep the prefix in a `long`.

## Related
[Subarray sum equals K](subarray-sum-equals-k.md) (look up `prefix − k` instead of the same remainder) · [Longest zero-sum subarray](longest-zero-sum-subarray.md). **Continuous subarray sum** (length ≥ 2) is the same idea, storing the first index of each remainder instead of a count.
