# Generate parentheses

<span class="badge medium">Medium</span> <span class="badge tag">Backtracking</span> <span class="badge tag">Pruning</span>

## Problem
All well-formed strings of n pairs of parentheses. n = 3 → `((()))  (()())  (())()  ()(())  ()()()`.
The count is the **Catalan number**: 1, 1, 2, 5, 14, 42…

## Thinking process
<div class="steps" markdown>

1. **Brute force**: generate all 2^(2n) strings of `(` and `)`, then keep the [valid](../01-strings/valid-parentheses.md) ones.
   Most of the work is wasted.
2. **Prune while building**. A prefix can still become valid **iff**:
    - `open ≤ n`, so you may add `(` while `open < n`;
    - `close ≤ open`, so you may add `)` only while `close < open`.
3. With those two rules, **every leaf is valid**. Nothing gets generated and then thrown away.
4. Use a `StringBuilder` with append and delete (choose, explore, un-choose) instead of creating new strings.

</div>

## Decision tree (n = 2)
```
                ""
                 (
             /       \
           ((         ()
            )          (
           (()        ()(
            )          )
          (())        ()()
```

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/GenerateParentheses.java"
```

## Complexity
Backtracking: O(Catalan(n) · n) ≈ O(4ⁿ / √n). Brute force: O(4ⁿ · n).

## Testing note
The counts match the Catalan numbers up to n = 8, and the output matches the brute force exactly up to n = 6.
