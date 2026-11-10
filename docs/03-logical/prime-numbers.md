# Prime check & Sieve of Eratosthenes

<span class="badge medium">Medium</span> <span class="badge tag">Math</span> <span class="badge tag">Sieve</span>

## Problem
1. Is `n` prime?
2. List every prime ≤ n.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try every divisor from 2 to n−1. That's O(n).
2. **√n insight**: if `n = a·b` with `a ≤ b`, then `a ≤ √n`. So you only need to test up to √n, which is O(√n).
   Loop with `i * i <= n` (using a `long` i) instead of calling `Math.sqrt` on every iteration.
3. **6k ± 1**: every prime above 3 has the form 6k−1 or 6k+1. Rule out 2 and 3, then test only `i` and `i+2` with step 6.
   That's about 3× fewer checks.
4. **Many numbers**: run the sieve once. For each prime `p`, mark `p², p²+p, …` as composite. Start at p² because
   smaller multiples were already marked by smaller primes.

</div>

## Sieve for n = 30
```
2 3 ✗ 5 ✗ 7 ✗ ✗ ✗ 11 ✗ 13 ✗ ✗ ✗ 17 ✗ 19 ✗ ✗ ✗ 23 ✗ ✗ ✗ ✗ ✗ 29 ✗
p=2 marks 4,6,8…   p=3 marks 9,15,21,27   p=5 marks 25   (7² > 30 → stop)
```

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/sieve.html"

## Code
```java
--8<-- "src/main/java/com/playbook/logical/PrimeNumbers.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n) | O(1) |
| √n / 6k±1 | O(√n) | O(1) |
| Sieve (all ≤ n) | O(n log log n) | O(n) |

## Follow-ups
Count primes (LeetCode 204) · Prime factorisation (divide by p while it divides, up to √n) · Segmented sieve for very large ranges.
