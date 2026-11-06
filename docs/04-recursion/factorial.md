# Factorial

<span class="badge easy">Easy</span> <span class="badge tag">Linear recursion</span> <span class="badge tag">Tail recursion</span> <span class="badge tag">BigInteger</span>

## Problem
Compute `n!` for n ≥ 0. `5! = 120`, `0! = 1`.

## Thinking process
<div class="steps" markdown>

1. **Base case**: `0! = 1! = 1`.
2. **Recursive case**: `n! = n × (n−1)!`.
3. **Stack**: n frames. Java does not optimise tail calls, so the iterative loop is the production answer.
4. **Overflow**: `long` holds up to 20!. Use `BigInteger` beyond that (100! has 158 digits).

</div>

## Call stack for `fact(4)`
```
fact(4) = 4 * fact(3)
              3 * fact(2)
                  2 * fact(1) = 1   ← base case
                  = 2
              = 6
        = 24
```

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/Factorial.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Recursive / tail | O(n) | O(n) stack |
| Iterative / streams | O(n) | O(1) |

## Follow-ups
Trailing zeros of n! (count the factors of 5: `n/5 + n/25 + …`) · n! mod 1e9+7 · nCr using factorials and a modular inverse.
