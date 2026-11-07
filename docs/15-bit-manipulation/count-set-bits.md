# Count set bits (Hamming weight)

<span class="badge easy">Easy</span> <span class="badge tag">Brian Kernighan</span> <span class="badge tag">DP</span>

## Problem
Count the 1 bits of an `int`, treated as unsigned, so −1 → 32. **Follow-up**: return the bit counts of every number from 0 to n
in O(n) (LeetCode 338).

## Thinking process
<div class="steps" markdown>

1. **Shift loop**: add `n & 1`, then shift right. Use **`>>>`**: `>>` copies the sign bit, so a negative n never reaches 0
   and the loop runs forever.
2. **Brian Kernighan**: `n &= n − 1` removes **one** set bit per iteration, so the loop runs only as many times as there are 1s.
3. **Library**: `Integer.bitCount` compiles to a single `POPCNT` CPU instruction.
4. **All numbers 0..n**: `bits(i) = bits(i >> 1) + (i & 1)`. Dropping the last bit gives a smaller number you've already solved.

</div>

## Kernighan on 44 (101100)
| n | n − 1 | n & (n−1) | count |
|---|-------|-----------|-------|
| 101100 | 101011 | 101000 | 1 |
| 101000 | 100111 | 100000 | 2 |
| 100000 | 011111 | 000000 | 3 |

## Code
```java
--8<-- "src/main/java/com/playbook/bits/CountSetBits.java"
```

## Complexity
Shift loop O(32) · Kernighan O(number of set bits) · all-n DP O(n).

## Related
Hamming distance (`bitCount(a ^ b)`) · total Hamming distance (count per bit position: ones × zeros).
