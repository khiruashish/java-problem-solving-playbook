# Reverse a number & palindrome number

<span class="badge easy">Easy</span> <span class="badge tag">Digit manipulation</span> <span class="badge tag">Overflow</span>

## Problem
Reverse the digits of a 32-bit int, keeping the sign. Return 0 if the result overflows. `123` → 321, `-123` → −321, `120` → 21, `1534236469` → 0.
Also check whether a number is a palindrome **without converting it to a string**.

## Thinking process
<div class="steps" markdown>

1. **Pop** the last digit with `d = x % 10` and drop it with `x /= 10`.
2. **Push** it onto the result with `r = r * 10 + d`.
3. **Overflow**: before multiplying, check `r > MAX/10` or `r < MIN/10`.
4. **Negative numbers**: Java's `%` keeps the dividend's sign (`-123 % 10 == -3`), so one loop handles both signs.
5. **Palindrome**: reverse only **half** of the digits, stopping when `x <= half`, then compare. Overflow is impossible.
   Negatives and numbers ending in 0 (other than 0 itself) are never palindromes.

</div>

## Dry run (reverse 123)
| x | d | r |
|---|---|---|
| 123 | 3 | 3 |
| 12 | 2 | 32 |
| 1 | 1 | 321 |

## Dry run (palindrome 1221)
| x | half |
|---|------|
| 1221 | 0 |
| 122 | 1 |
| 12 | 12 → stop, `x == half` ✅ |

## Code
```java
--8<-- "src/main/java/com/playbook/logical/ReverseNumber.java"
```

## Complexity
O(log₁₀ n) time, O(1) space.
