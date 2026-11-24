# Divide two integers (no *, /, %)

<span class="badge hard">Hard</span> <span class="badge tag">Binary long division</span> <span class="badge tag">Doubling</span> <span class="badge tag">Overflow</span>

## Problem
Divide two 32-bit integers **without** `*`, `/` or `%`. Truncate toward zero, as Java's `/` does.
The one result that doesn't fit, `MIN_VALUE / −1`, must return `MAX_VALUE`.

## Examples
| dividend | divisor | result |
|----------|---------|--------|
| 10 | 3 | 3 |
| 7 | −3 | −2 (truncate toward zero) |
| −2147483648 | 1 | −2147483648 |
| −2147483648 | −1 | 2147483647 (clamped) |

## Clarifying questions
- Is `long` allowed? If not, work in the **negative** range (see below).
- Divisor 0? Throw `ArithmeticException`, like Java does.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: subtract the divisor until you can't. Correct, but `MAX_VALUE / 1` takes 2³¹ subtractions.
2. **Subtract big chunks**: instead of b, subtract the **largest b · 2^k that still fits**. That is long division in binary:
   for k from 31 down to 0, if `b << k ≤ a`, subtract it and set bit k of the quotient. Only 32 steps.
3. **Signs**: work with absolute values and fix the sign at the end with `negative = (a < 0) ^ (b < 0)`.
4. **The overflow corner**: `|MIN_VALUE|` doesn't fit in an `int`. Either use `long`, or, if `long` is forbidden, convert both numbers to
   **negative** (the negative range is one larger) and do all comparisons with the inequalities reversed.

</div>

## Dry run (shifts): 10 / 3
| k | 3 << k | fits in a? | a after | quotient bits |
|---|--------|------------|---------|---------------|
| 2 | 12 | no (a = 10) | 10 | |
| 1 | 6 | yes | 4 | `10` |
| 0 | 3 | yes | 1 | `11` = **3** |

## Code
```java
--8<-- "src/main/java/com/playbook/bits/DivideTwoIntegers.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Repeated subtraction | O(quotient), up to 2³¹ | O(1) |
| Binary long division (shifts) | **O(32)** | O(1) |
| Negatives only, repeated doubling | O(log² quotient) | O(1) |

## Edge cases & pitfalls
- `Math.abs(Integer.MIN_VALUE)` is still `MIN_VALUE`. Cast to `long` **before** taking the absolute value.
- In the negatives-only version, stop doubling at `MIN_VALUE >> 1`, because the next `chunk + chunk` would overflow.
- The tests compare against Java's own `/` on 50,000 random pairs, including every MIN/MAX combination.

## Related
[Bit basics](bit-basics.md) · [Fast power](../04-recursion/power.md) (the same "process the exponent's bits" idea) · [String to integer](../01-strings/string-to-integer.md) (another overflow-guard problem).
