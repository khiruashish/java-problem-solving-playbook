# Unique paths in a grid

<span class="badge medium">Medium</span> <span class="badge tag">Grid DP</span> <span class="badge tag">Combinatorics</span>

## Problem
A robot at the top-left of an m×n grid moves only **right** or **down**. How many paths reach the bottom-right? 3×7 → 28.
**Variant**: some cells are blocked.

## Thinking process
<div class="steps" markdown>

1. **Last move** into (r, c) came from above or from the left, so `f(r,c) = f(r−1,c) + f(r,c−1)`. The first row and first column are 1.
2. Recursion → memo → **2D table**, filled row by row.
3. **1D rolling row**: before you update `row[c]`, it still holds the value from the row **above**. Add `row[c−1]` (from the
   left, already updated). That's O(n) space.
4. **Maths shortcut**: every path is a sequence of (m−1) downs and (n−1) rights, so the count is `C(m+n−2, m−1)`. Compute it
   incrementally so it stays an integer at every step.
5. **Obstacles**: a blocked cell has 0 paths. The same rolling row handles it.

</div>

## Table for 3×4
```
1  1  1  1
1  2  3  4
1  3  6 10
```

## Code
```java
--8<-- "src/main/java/com/playbook/dp/UniquePaths.java"
```

## Complexity
| Version | Time | Space |
|---------|------|-------|
| Recursion | O(2^(m+n)) | O(m+n) |
| Memo / table | O(m·n) | O(m·n) |
| Rolling row | O(m·n) | O(n) |
| Combinatorics | O(min(m,n)) | O(1) |

## Family
Minimum path sum (min instead of +) · Dungeon game (fill backwards from the bottom-right) · Cherry pickup (3D state).
