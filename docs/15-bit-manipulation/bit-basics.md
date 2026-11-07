# Check, set, clear & toggle a bit

<span class="badge easy">Easy</span> <span class="badge tag">Masks</span>

## Problem
Given n and a bit position k (0 = least significant), check, set, clear or toggle that bit. Also isolate and clear the
**lowest** set bit.

## Thinking process
<div class="steps" markdown>

1. Build a **mask** with only bit k set: `1 << k`.
2. **Check**: AND keeps only that bit, then compare with 0.
3. **Set**: OR forces it to 1. **Clear**: AND with the inverted mask forces it to 0. **Toggle**: XOR flips it.
4. **Lowest set bit**: `n & −n`. In two's complement, `−n = ~n + 1`, which flips every bit above the lowest 1 and keeps that 1.
5. **Clear the lowest set bit**: `n & (n − 1)`. Subtracting 1 turns the lowest 1 into 0 and the zeros below it into 1s, and
   AND removes them all.

</div>

## Worked example (n = 1010₂ = 10)
| Operation | Expression | Result |
|-----------|------------|--------|
| check bit 1 | `10 & 0010` | 0010 ≠ 0 → set |
| set bit 0 | `10 \| 0001` | 1011 |
| clear bit 1 | `10 & 1101` | 1000 |
| toggle bit 3 | `10 ^ 1000` | 0010 |
| lowest set bit of 12 (1100) | `12 & −12` | 0100 = 4 |

## Code
```java
--8<-- "src/main/java/com/playbook/bits/BitBasics.java"
```

## Complexity
O(1) for every operation.

## Where these show up
Permission flags (`READ | WRITE`), `EnumSet` internals, chess bitboards, compact visited sets in [bitmask DP](bitmask-subsets.md).
