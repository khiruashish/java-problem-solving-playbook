# Distinct elements in every window

<span class="badge medium">Medium</span> <span class="badge tag">Sliding window</span> <span class="badge tag">Frequency map</span>

## Problem
For every window of size k, count how many **distinct** values it contains.

## Examples
| a | k | Output |
|---|---|--------|
| `[1, 2, 1, 3, 4, 2, 3]` | 4 | `[3, 4, 4, 3]` |
| `[7, 7, 7]` | 1 | `[1, 1, 1]` |
| `[1, 2]` | 3 | `[]` (no full window) |

## Clarifying questions
- k larger than n? Then there are no windows, so return an empty array.
- Value range? If values are small and non-negative, a counts array beats a map.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: a fresh `HashSet` for each window. O(n · k).
2. **Waste**: consecutive windows share k − 1 elements. Only one element **enters** and one **leaves**.
3. **Keep a frequency map of the current window**: increment the entering value and decrement the leaving one.
   The number of distinct values is the number of keys **with a count above zero**, so delete a key when its count hits 0. Then `map.size()` *is* the answer.
4. **Map-free version**: with a counts array, keep a `distinct` counter. A count going 0 → 1 means +1, and 1 → 0 means −1.

</div>

## Dry run: `[1, 2, 1, 3, 4, 2, 3]`, k = 4
| i | enters | leaves | window | map | distinct |
|---|--------|--------|--------|-----|----------|
| 3 | 3 | — | 1 2 1 3 | {1:2, 2:1, 3:1} | **3** |
| 4 | 4 | 1 | 2 1 3 4 | {1:1, 2:1, 3:1, 4:1} | **4** |
| 5 | 2 | 2 | 1 3 4 2 | {1:1, 2:1, 3:1, 4:1} | **4** |
| 6 | 3 | 1 | 3 4 2 3 | {2:1, 3:2, 4:1}, key 1 removed | **3** |

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/DistinctInWindow.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Set per window | O(n · k) | O(k) |
| Sliding frequency map | **O(n)** | O(k) |
| Counts array + counter | **O(n)** | O(max value) |

## Edge cases & pitfalls
- Forgetting to **remove** zero-count keys makes `map.size()` count values that have already left.
- `map.merge(key, -1, (old, d) -> old + d == 0 ? null : old + d)`: a merge function that returns `null` deletes the key. That is the neatest way to do it.
- Remove the leaving element only once `i ≥ k`.

## Related
[Longest substring without repeating characters](../01-strings/longest-substring-without-repeating.md) · [Sliding window maximum](../07-stack-queue/sliding-window-maximum.md) · [Minimum window substring](../01-strings/minimum-window-substring.md).
