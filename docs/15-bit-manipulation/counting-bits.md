# Counting bits (0..n)

<span class="badge medium">Medium</span> <span class="badge tag">DP on bits</span> <span class="badge tag">i & (i − 1)</span> <span class="badge tag">i >> 1</span>

## Problem
Return an array `bits` where `bits[i]` is the number of 1s in the binary form of i, for every i from 0 to n.
Follow-up: O(n) total, without a popcount loop for each number.

## Example
| i | binary | bits[i] |
|---|--------|---------|
| 0 | 0 | 0 |
| 1 | 1 | 1 |
| 2 | 10 | 1 |
| 3 | 11 | 2 |
| 4 | 100 | 1 |
| 5 | 101 | 2 |

## Clarifying questions
- Is `Integer.bitCount` allowed? Usually not in the interview. It is a test oracle here.
- n up to 10⁵ or 10⁶: the answer array itself is the memory cost.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: count each number separately (Kernighan's `x &= x − 1` removes one set bit per step). O(n · bits).
2. **Reuse smaller answers (DP)**: every i is a smaller number plus one bit, and that smaller number's count is already known. Three ways to see it:
   - **drop the last bit**: `i >> 1` is i without its lowest bit, so `bits[i] = bits[i >> 1] + (i & 1)`,
   - **drop the lowest set bit**: `i & (i − 1)` is i with one fewer 1, so `bits[i] = bits[i & (i − 1)] + 1`,
   - **drop the highest bit**: `bits[i] = 1 + bits[i − 2^k]` for the largest power of two 2^k ≤ i.
3. All three are O(1) per number, so O(n) total.

</div>

## Dry run (`bits[i >> 1] + (i & 1)`)
| i | i >> 1 | bits[i >> 1] | i & 1 | bits[i] |
|---|--------|--------------|-------|---------|
| 1 | 0 | 0 | 1 | 1 |
| 2 | 1 | 1 | 0 | 1 |
| 3 | 1 | 1 | 1 | 2 |
| 4 | 2 | 1 | 0 | 1 |
| 5 | 2 | 1 | 1 | 2 |

## Code
```java
--8<-- "src/main/java/com/playbook/bits/CountingBits.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Kernighan per number | O(n · popcount) | O(n) output |
| `bits[i >> 1] + (i & 1)` | **O(n)** | O(n) output |
| `bits[i & (i − 1)] + 1` | **O(n)** | O(n) output |
| Highest power of two | **O(n)** | O(n) output |

## Edge cases & pitfalls
- n = 0 → `[0]`.
- In the highest-power version, move to the next block exactly when i reaches `2 · power`.

## Related
[Count set bits](count-set-bits.md) (one number, Kernighan) · [Power of two](power-of-two.md) (`n & (n − 1) == 0`) · [Climbing stairs](../13-dynamic-programming/climbing-stairs.md) (another "answer from a smaller index" DP).
