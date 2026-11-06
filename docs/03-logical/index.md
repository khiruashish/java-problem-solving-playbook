# 03 · Logical & Math

Number-crunching questions show up in almost every screening round. Interviewers use them to check
**loop control, integer overflow awareness and digit manipulation**.

## Core tricks

| Trick | Code |
|-------|------|
| Last digit | `n % 10` |
| Drop last digit | `n / 10` |
| Append digit | `r = r * 10 + d` |
| Count digits | `String.valueOf(n).length()` or `(int) Math.log10(n) + 1` (n > 0) |
| Overflow guard before `r*10` | `r > Integer.MAX_VALUE / 10` |
| Integer power | multiply in a loop. Avoid `Math.pow`, whose doubles round |

## Problems (Easy → Hard)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [FizzBuzz](fizzbuzz.md) | <span class="badge easy">Easy</span> | Check the most specific rule first |
| 2 | [Reverse number & palindrome number](reverse-number.md) | <span class="badge easy">Easy</span> | Pop/push digits, overflow guard |
| 3 | [Armstrong number](armstrong-number.md) | <span class="badge easy">Easy</span> | Digit-power sum |
| 4 | [GCD & LCM](gcd-lcm.md) | <span class="badge medium">Medium</span> | Euclid |
| 5 | [Prime check & Sieve of Eratosthenes](prime-numbers.md) | <span class="badge medium">Medium</span> | √n, 6k±1, sieve |
