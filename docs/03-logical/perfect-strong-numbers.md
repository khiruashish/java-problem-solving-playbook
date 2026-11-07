# Perfect & strong numbers

<span class="badge easy">Easy</span> <span class="badge tag">Divisors</span> <span class="badge tag">Digits</span>

## Problem
- **Perfect**: n equals the sum of its proper divisors. `6 = 1+2+3`, and 28, 496, 8128 are perfect.
- **Strong**: n equals the sum of the factorials of its digits. `145 = 1! + 4! + 5!`, and 40585 is strong.

## Thinking process
<div class="steps" markdown>

1. **Perfect, brute force**: test every d from 1 to n−1. O(n).
2. **Divisor pairs**: if d divides n, so does n/d, so you only scan to √n and add **both** d and n/d. Don't add √n twice
   for perfect squares. This makes it O(√n).
3. **Strong**: digits only go up to 9, so **precompute** 0!…9! in a table once instead of recomputing factorials.
4. **Recursive strong**: `digitFactorialSum(n) = FACT[n % 10] + digitFactorialSum(n / 10)`.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/logical/PerfectStrongNumbers.java"
```

## Complexity
Perfect: O(n) brute force, O(√n) with pairs. Strong: O(digits).

## Testing note
Every n up to 10,000 is checked against the brute force. 33,550,336 (the fifth perfect number) is checked with the √n version.
