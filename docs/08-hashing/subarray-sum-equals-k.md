# Subarray sum equals K

<span class="badge medium">Medium</span> <span class="badge tag">Prefix sum</span> <span class="badge tag">HashMap</span>

## Problem
Count the contiguous subarrays whose sum is exactly k. **Values can be negative.**
`[1,1,1]`, k = 2 → 2 · `[3,4,7,2,−3,1,4,2]`, k = 7 → 4.

## Clarifying questions
Negative numbers? (Yes, which rules out a plain sliding window.) Count or list them? Overflow? (Use `long` sums.)

## Thinking process
<div class="steps" markdown>

1. **Brute force**: every start i, extending j with a running sum. O(n²).
2. **Why not a sliding window?** A window works only if growing it always increases the sum. With negative numbers,
   shrinking might *increase* the sum, so the window logic breaks.
3. **Prefix-sum insight**: `sum(i+1..j) = prefix[j] − prefix[i]`. So a subarray **ending at j** sums to k exactly when an
   **earlier** prefix equals `prefix[j] − k`.
4. Keep a map `prefix value → how many times seen`. At each j, add `map[prefix − k]` to the count, then record `prefix`.
5. **Seed** the map with `{0: 1}` (the empty prefix), so subarrays starting at index 0 are counted.

</div>

## Dry run (`[1,1,1]`, k = 2)
| j | a[j] | prefix | Need prefix − k | Map before | count |
|---|------|--------|-----------------|------------|-------|
| 0 | 1 | 1 | −1 | {0:1} | 0 |
| 1 | 1 | 2 | 0 → 1 | {0:1, 1:1} | 1 |
| 2 | 1 | 3 | 1 → 1 | {0:1, 1:1, 2:1} | **2** |

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/subarray-sum-equals-k.html"

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/SubarraySumEqualsK.java"
```

## Complexity
| Approach | Time | Space | Works with negatives? |
|----------|------|-------|-----------------------|
| Brute force | O(n²) | O(1) | ✅ |
| Prefix sum + map | O(n) | O(n) | ✅ |
| Sliding window | O(n) | O(1) | ❌ (non-negative only) |

## Pitfalls
Inserting the current prefix *before* the lookup counts the empty subarray when k = 0. Forgetting the `{0: 1}` seed.
`int` prefix overflow on large inputs.

## Same pattern
[Longest subarray with sum k](longest-zero-sum-subarray.md) · Subarray sums divisible by k (key = `prefix mod k`, normalised
to be non-negative) · Count of subarrays with XOR = k (prefix XOR).
