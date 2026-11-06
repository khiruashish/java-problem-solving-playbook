# GCD & LCM

<span class="badge medium">Medium</span> <span class="badge tag">Euclid</span> <span class="badge tag">Recursion</span>

## Problem
Compute `gcd(a, b)` and `lcm(a, b)`. `gcd(12, 18) = 6`, `lcm(12, 18) = 36`. Also find the GCD of a whole array.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try every d from `min(a,b)` down to 1. That's O(min(a, b)).
2. **Euclid's insight**: any common divisor of a and b also divides `a − b`, and therefore `a mod b`.
   So `gcd(a, b) = gcd(b, a mod b)`, with base case `gcd(a, 0) = a`.
3. The arguments shrink at least as fast as Fibonacci numbers, so the cost is **O(log min(a,b))**.
4. **LCM**: `a / gcd · b`. Divide **first** to avoid overflow, and use `long`.
5. **Array**: `reduce(0, gcd)`, because `gcd(0, x) = x`.

</div>

## Dry run (gcd(48, 18))
| a | b | a mod b |
|---|---|---------|
| 48 | 18 | 12 |
| 18 | 12 | 6 |
| 12 | 6 | 0 |
| 6 | 0 | → **6** |

## Code
```java
--8<-- "src/main/java/com/playbook/logical/GcdLcm.java"
```

## Follow-ups
Extended Euclid (`ax + by = gcd`, modular inverse) · Simplify a fraction · LCM of 1..n.
