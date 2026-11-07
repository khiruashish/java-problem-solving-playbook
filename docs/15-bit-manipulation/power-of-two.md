# Power of two (and power of four)

<span class="badge easy">Easy</span> <span class="badge tag">n & (n−1)</span>

## Problem
Is n a power of two? 1, 2, 4, 8, … → yes. 0, negatives, 6 → no. **Follow-up**: is n a power of four?

## Thinking process
<div class="steps" markdown>

1. **Loop**: divide by 2 while n is even, and check you end at 1. O(log n).
2. **Binary insight**: a power of two has **exactly one** set bit (`1000…0`). `n − 1` turns it into `0111…1`, so
   `n & (n−1) == 0`. O(1). Guard against `n > 0`, because `0 & −1 == 0` and `Integer.MIN_VALUE` also has a single bit.
3. **Library**: `Integer.bitCount(n) == 1`.
4. **Power of four**: a power of two whose single bit is at an **even** position (1, 4, 16 → bits 0, 2, 4). Check with the mask
   `0x55555555` (`…0101`).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/bits/PowerOfTwo.java"
```

## Complexity
Loop O(log n) · bit trick O(1).

## Testing note
Every n from −5 to 5000, plus `Integer.MIN_VALUE`. All methods must agree.
