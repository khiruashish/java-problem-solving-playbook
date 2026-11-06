# Power: fast exponentiation

<span class="badge medium">Medium</span> <span class="badge tag">Divide & conquer</span> <span class="badge tag">Bit manipulation</span>

## Problem
Compute `xⁿ`, where n can be negative (LeetCode 50). Also compute `aᵇ mod m`.

## Thinking process
<div class="steps" markdown>

1. **Naive**: `x · x^(n−1)`. That's O(n), and n = 2³¹ overflows the stack.
2. **Halving insight**: `xⁿ = (x^(n/2))²`, multiplied by x again if n is odd. That's **O(log n)**.
3. Compute `half` **once**. Calling `pow(x, n/2) * pow(x, n/2)` makes the cost O(n) again.
4. **Negative n**: use `x = 1/x` and `n = −n`. Store n in a `long`, because `−Integer.MIN_VALUE` overflows an `int`.
5. **Iterative**: walk the bits of n. Square x at every step, and multiply it into the result when the current bit is 1.

</div>

## Dry run (2¹⁰, iterative)
| N (binary) | bit | result | x |
|------------|-----|--------|---|
| 1010 | 0 | 1 | 2 → 4 |
| 101 | 1 | 4 | 4 → 16 |
| 10 | 0 | 4 | 16 → 256 |
| 1 | 1 | 4·256 = **1024** | |

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/Power.java"
```

## Complexity
Naive O(n). Fast exponentiation O(log n), using O(log n) stack recursively or O(1) iteratively.
