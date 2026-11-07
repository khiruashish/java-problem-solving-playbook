# Sudoku solver

<span class="badge expert">Expert</span> <span class="badge tag">Backtracking</span> <span class="badge tag">Bitmasks</span> <span class="badge tag">Heuristics</span>

## Problem
Fill a 9×9 Sudoku in place so that every row, column and 3×3 box contains 1–9 exactly once. Return false if that's impossible.

## Thinking process
<div class="steps" markdown>

1. **Plain backtracking**: find an empty cell, try digits 1–9 that don't conflict, recurse, and undo on failure. Correct,
   but the conflict check scans 27 cells for every try.
2. **O(1) checks with bitmasks**: keep `rows[r]`, `cols[c]` and `boxes[b]` as 9-bit masks of the digits used. The candidates for
   a cell are `~(rows[r] | cols[c] | boxes[b]) & 0x1FF`. Placing or removing a digit is a single OR or AND.
3. **Most-constrained cell first** (the MRV heuristic): always fill the empty cell with the **fewest** candidates. A cell with 0
   candidates means a dead end, so backtrack immediately. A cell with 1 candidate is forced. This collapses the search tree
   from billions of nodes to thousands.
4. Iterate over the candidates with `free & −free` (the lowest set bit), the same trick as in
   [N-Queens](n-queens.md) and [bit basics](../15-bit-manipulation/bit-basics.md).
5. **Validate the givens up front**: a puzzle that already breaks a rule has no solution.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/SudokuSolver.java"
```

## Complexity
Exponential in the worst case (Sudoku generalised to n×n is NP-complete). With bitmasks and MRV, real 9×9 puzzles take
milliseconds, including Arto Inkala's "world's hardest Sudoku", which is in the tests.

## Why it's "Expert"
It combines three ideas: backtracking, bitmask state, and a search-order heuristic. The same structure (constraint
propagation plus MRV) powers real constraint solvers.

## Testing note
The solved grids are validated in full (every row, column and box), and the tests confirm the givens are unchanged. An invalid
starting grid must return false.
