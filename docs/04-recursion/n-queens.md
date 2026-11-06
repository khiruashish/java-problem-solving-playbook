# N-Queens

<span class="badge expert">Expert</span> <span class="badge tag">Backtracking</span> <span class="badge tag">Pruning</span> <span class="badge tag">Bitmask</span>

## Problem
Place n queens on an n×n board so that no two share a row, column or diagonal. Return every board, and also the count.
n = 4 has 2 solutions, and n = 8 has 92.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: choose n of the n² cells, which is C(n², n). Hopeless.
2. **One queen per row** reduces the search to placing a column for each row, n at a time: nⁿ.
3. **One per column** too reduces it to permutations: n!.
4. **Prune diagonals immediately**: cells on the same `\` diagonal share `row − col`, and cells on the same `/` diagonal share `row + col`.
   Keep three boolean arrays so each safety check is **O(1)**.
5. **Backtrack**: place a queen, recurse to the next row, then remove it.
6. **Expert speed-up**: store columns and diagonals as bitmasks. `free = ~(cols|diag|anti) & mask`, and
   `bit = free & −free` picks the lowest free cell.

</div>

## n = 4 solutions
```
. Q . .      . . Q .
. . . Q      Q . . .
Q . . .      . . . Q
. . Q .      . Q . .
```

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/NQueens.java"
```

## Complexity
O(n!) time in the worst case, but pruning makes it far faster in practice. O(n) space.

## Related
Sudoku solver (the same template, with row/column/box masks) · Word search in a grid · Knight's tour · Rat in a maze (GfG).
