# Sum of digits & digital root

<span class="badge easy">Easy</span> <span class="badge tag">Linear recursion</span> <span class="badge tag">Math</span>

## Problem
Sum the digits of n (12345 → 15). The **digital root** repeats this until a single digit remains (12345 → 15 → 6).

## Thinking process
<div class="steps" markdown>

1. **Base case**: `n == 0` gives 0.
2. **Recursive case**: `n % 10 + sum(n / 10)`.
3. **Digital root**: recurse on the sum until it's < 10.
4. **O(1) insight**: n ≡ digitSum(n) (mod 9), so `root = 1 + (n − 1) % 9` for n > 0.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/SumOfDigits.java"
```

## Complexity
O(d) for the digit sum and O(1) for the digital-root formula.
