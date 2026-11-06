# Armstrong number

<span class="badge easy">Easy</span> <span class="badge tag">Digits</span> <span class="badge tag">Recursion</span> <span class="badge tag">Streams</span>

## Problem
An Armstrong (narcissistic) number equals the sum of its digits, each raised to the number of digits.
`153 = 1³ + 5³ + 3³`, `9474 = 9⁴ + 4⁴ + 7⁴ + 4⁴`. Also: list every Armstrong number up to a limit.

## Thinking process
<div class="steps" markdown>

1. Count the digits `d` once.
2. Extract each digit with `% 10` and `/ 10`, and add `digit^d`.
3. Compare the sum with the original. Keep a copy, because the loop destroys `n`.
4. **Pitfall**: `Math.pow` returns a `double`. Use an integer power loop to avoid rounding errors.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/logical/ArmstrongNumber.java"
```

## Complexity
O(d²) per number with the naive power, or O(d log d) with fast power. Since d ≤ 10, that's effectively O(1).

## Related
Perfect number (sum of proper divisors) · Strong number (sum of digit factorials) · Harshad number (divisible by its digit sum).
