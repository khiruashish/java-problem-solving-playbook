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

## Problems (Easy → Medium)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [FizzBuzz](fizzbuzz.md) | <span class="badge easy">Easy</span> | Check the most specific rule first |
| 2 | [Reverse number & palindrome number](reverse-number.md) | <span class="badge easy">Easy</span> | Pop/push digits, overflow guard |
| 3 | [Armstrong number](armstrong-number.md) | <span class="badge easy">Easy</span> | Digit-power sum |
| 4 | [Perfect & strong numbers](perfect-strong-numbers.md) | <span class="badge easy">Easy</span> | Divisor pairs / factorial table |
| 5 | [Leap year](leap-year.md) | <span class="badge easy">Easy</span> | Most specific rule first |
| 6 | [GCD & LCM](gcd-lcm.md) | <span class="badge medium">Medium</span> | Euclid |
| 7 | [Prime check & Sieve of Eratosthenes](prime-numbers.md) | <span class="badge medium">Medium</span> | √n, 6k±1, sieve |
| 8 | [Number base conversion](base-conversion.md) | <span class="badge medium">Medium</span> | Division / Horner's rule |
| 9 | [Pattern printing](pattern-printing.md) | <span class="badge medium">Medium</span> | Spaces + stars formulas per row |
| 10 | [LCM of 1..n](lcm-of-range.md) | <span class="badge medium">Medium</span> | Fold LCM / prime powers |

Also: swap two numbers without a temp variable, and odd/even with bits, in [XOR tricks](../15-bit-manipulation/xor-tricks.md).
