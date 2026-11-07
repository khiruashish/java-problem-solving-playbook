# XOR tricks: swap, odd/even, signs, add without +

<span class="badge medium">Medium</span> <span class="badge tag">XOR identities</span>

## Problem
A set of warm-up questions interviewers ask to check that you're comfortable with bits:

| Question | Answer |
|----------|--------|
| Swap two variables without a temp | `a ^= b; b ^= a; a ^= b;` (but guard `a` and `b` being the **same** memory location!) |
| Is n odd? | `(n & 1) == 1`. Note `n % 2 == 1` is **wrong** for negatives (`−3 % 2 == −1`) |
| Do x and y have opposite signs? | `(x ^ y) < 0`: the sign bits differ |
| XOR of 1..n in O(1) | `n, 1, n+1, 0` repeating with period 4 |
| Reverse the 32 bits of n | shift the lowest bit of n into the result 32 times |
| Add two ints without `+` | XOR is addition without carry, and `(a & b) << 1` is the carry. Repeat until the carry is 0 |

## Thinking process (add without +)
<div class="steps" markdown>

1. Single-bit addition: `0+0=0`, `0+1=1`, `1+1=10`. The **sum bit** is XOR and the **carry bit** is AND.
2. For whole numbers: `sum = a ^ b`, `carry = (a & b) << 1`. Then add `sum + carry` the same way.
3. The carry moves left each round, so it reaches 0 within 32 rounds, even for negative numbers in two's complement.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/bits/XorTricks.java"
```

## Complexity
O(1) each, at most 32 iterations for reverse and add.

## Senior caveat
The XOR swap is a **puzzle answer, not production code**. The JIT optimises a plain temp swap just as well, and the XOR
version silently zeroes the value when both references point to the same cell. The code guards that case, and a test proves it.
