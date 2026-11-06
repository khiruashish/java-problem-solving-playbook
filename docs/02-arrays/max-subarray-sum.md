# Maximum subarray sum (Kadane's algorithm)

<span class="badge medium">Medium</span> <span class="badge tag">Kadane</span> <span class="badge tag">DP</span> <span class="badge tag">Divide & conquer</span>

## Problem
Return the largest sum of a non-empty contiguous subarray. `[-2,1,-3,4,-1,2,1,-5,4]` → 6 (`[4,-1,2,1]`).

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try every `(i, j)` with a running sum. That's O(n²).
2. **DP view**: let `best(i)` = the best sum of a subarray **ending at i**. Then
   `best(i) = max(a[i], best(i-1) + a[i])`. In words: **extend** the previous run, or **restart** here.
3. Restart exactly when the previous run is negative, because it can only hurt.
4. The answer is `max over i of best(i)`. Since you only need the previous value, space is O(1).

</div>

## Dry run
| i | a[i] | current = max(a[i], current + a[i]) | best |
|---|------|-------------------------------------|------|
| 0 | −2 | −2 | −2 |
| 1 | 1 | max(1, −1) = 1 (restart) | 1 |
| 2 | −3 | −2 | 1 |
| 3 | 4 | max(4, 2) = 4 (restart) | 4 |
| 4 | −1 | 3 | 4 |
| 5 | 2 | 5 | 5 |
| 6 | 1 | **6** | **6** |
| 7 | −5 | 1 | 6 |
| 8 | 4 | 5 | 6 |

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/MaxSubarraySum.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n²) | O(1) |
| Kadane | O(n) | O(1) |
| Divide & conquer | O(n log n) | O(log n) |

## Pitfalls
For an all-negative array the answer is the largest single element, **not 0**. Initialise with `a[0]`.

## Follow-ups
Maximum product subarray (track both min and max). Circular max subarray (`max(kadane, total − minSubarray)`). Max sum of a submatrix (Kadane on column sums).
