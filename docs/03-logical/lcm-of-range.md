# LCM of 1..n (smallest multiple)

<span class="badge medium">Medium</span> <span class="badge tag">GCD/LCM</span> <span class="badge tag">Primes</span>

## Problem
The smallest positive number divisible by every integer from 1 to n. n = 10 → 2520 · n = 20 → 232,792,560 (Project Euler 5).

## Thinking process
<div class="steps" markdown>

1. **Brute force**: test multiples of n until every d ≤ n divides one. Fine for n ≤ 20, hopeless beyond that.
2. **Fold the LCM**: `lcm(1..n) = lcm(lcm(1..n−1), n)`, using `lcm(a, b) = a / gcd(a, b) × b`.
   **Divide before multiplying** to delay overflow. A `long` still overflows past n = 42.
3. **Prime-power insight**: the answer must contain each prime p raised to the **largest power ≤ n**. For n = 10 that's
   2³ · 3² · 5 · 7 = 2520. Combine this with the [sieve](prime-numbers.md) and `BigInteger` and it's exact for any n.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/logical/LcmOfRange.java"
```

## Complexity
Brute force: grows like the answer itself. LCM fold: O(n log n). Prime powers: O(n log log n) for the sieve, plus big-number multiplication.

## Testing note
The three methods are cross-checked for n ≤ 40, and the prime-power version is checked for n = 100 (a 41-digit answer).
