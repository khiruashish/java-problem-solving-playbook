# Recursion mental model

## The three questions

<div class="steps" markdown>

1. **What is the smallest input I can answer directly?** That is the **base case**.
2. **If someone hands me the answer for a smaller input, how do I build mine?** That is the **recursive case**.
3. **Does every call move toward the base case?** If not, you get infinite recursion and a `StackOverflowError`.

</div>

!!! tip "Leap of faith"
    Don't trace every call in your head. Assume `f(n-1)` is correct and only check that
    `f(n)` uses it correctly. This is proof by induction.

## Recursion tree, and why memoization matters

`fib(5)` recomputes `fib(3)` twice and `fib(2)` three times. That makes it exponential:

```
                fib(5)
             /         \
         fib(4)        fib(3)
        /     \        /    \
    fib(3)  fib(2)  fib(2) fib(1)
    /   \
 fib(2) fib(1)
```

Cache each result once and the tree collapses to a line, so the cost drops to **O(n)**. See
[Fibonacci](../04-recursion/fibonacci.md).

## Types of recursion

| Type | Shape | Example |
|------|-------|---------|
| Linear | one self-call | factorial, sum of digits |
| Tail | self-call is the last action | `tail(n-1, acc*n)` |
| Binary / tree | two self-calls | fibonacci, merge sort |
| Divide & conquer | split, solve halves, combine | max/min, merge sort, LCP |
| Backtracking | choose → explore → un-choose | subsets, permutations, N-Queens |

## Converting recursion to iteration ("normal solution")

- **Tail recursion** becomes a `while` loop: the accumulator turns into a local variable.
- **Linear recursion** becomes a loop that runs from the base case upwards (bottom-up).
- **Tree recursion with overlapping subproblems** becomes a DP table filled bottom-up.
- **General recursion** becomes an explicit `Deque` used as a stack.

Java does **not** optimise tail calls, so for large n prefer the iterative form.
