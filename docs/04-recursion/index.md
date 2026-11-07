# 04 · Recursion & Backtracking

Read [the recursion mental model](../00-foundations/recursion-mental-model.md) first. Every problem here shows **both**
the recursive solution and the iterative ("normal") one, and explains when to prefer each.

## The backtracking template

```java
void backtrack(State s, List<Answer> out) {
    if (isComplete(s)) { out.add(copyOf(s)); return; }   // base case
    for (Choice c : choices(s)) {
        if (!isValid(s, c)) continue;                     // prune
        apply(s, c);                                      // choose
        backtrack(s, out);                                // explore
        undo(s, c);                                       // un-choose
    }
}
```

## Problems (Easy → Expert)

| # | Problem | Level | Recursion type |
|---|---------|-------|----------------|
| 1 | [Factorial](factorial.md) | <span class="badge easy">Easy</span> | Linear / tail |
| 2 | [Sum of digits & digital root](sum-of-digits.md) | <span class="badge easy">Easy</span> | Linear |
| 3 | [Fibonacci: naive → memo → iterative → matrix](fibonacci.md) | <span class="badge medium">Medium</span> | Tree → DP |
| 4 | [Power (fast exponentiation)](power.md) | <span class="badge medium">Medium</span> | Divide & conquer |
| 5 | [Tower of Hanoi](tower-of-hanoi.md) | <span class="badge medium">Medium</span> | Tree (2 calls) |
| 6 | [Generate parentheses](generate-parentheses.md) | <span class="badge medium">Medium</span> | Backtracking with open/close counts |
| 7 | [Letter combinations of a phone number](letter-combinations.md) | <span class="badge medium">Medium</span> | Cartesian product |
| 8 | [Subsets / power set](subsets.md) | <span class="badge hard">Hard</span> | Backtracking |
| 9 | [Combination sum I & II](combination-sum.md) | <span class="badge hard">Hard</span> | Start index + duplicate skip |
| 10 | [Permutations II (with duplicates)](permutations-ii.md) | <span class="badge hard">Hard</span> | Used-twin rule |
| 11 | [Word search](word-search.md) | <span class="badge hard">Hard</span> | Grid backtracking |
| 12 | [N-Queens](n-queens.md) | <span class="badge expert">Expert</span> | Backtracking + pruning |
| 13 | [Sudoku solver](sudoku-solver.md) | <span class="badge expert">Expert</span> | Bitmasks + most-constrained cell |

Also see: [String permutations](../01-strings/string-permutations.md) · [Longest common prefix (divide & conquer)](../01-strings/longest-common-prefix.md) · [Max subarray (divide & conquer)](../02-arrays/max-subarray-sum.md).
