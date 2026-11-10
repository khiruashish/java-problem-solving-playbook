# Edit distance (Levenshtein)

<span class="badge hard">Hard</span> <span class="badge tag">Two-string DP</span>

## Problem
The minimum number of single-character **inserts, deletes and replacements** to turn a into b.
`"horse"` → `"ros"` = 3 (replace h→r, delete r, delete e).

## Thinking process
<div class="steps" markdown>

1. **State** = prefixes `(i, j)`. **Base cases**: turning i characters into an empty string takes i deletes, and
   turning an empty string into j characters takes j inserts.
2. **Last characters equal** cost nothing: `f(i−1, j−1)`.
3. **Different**: try all three operations on the last position and take the cheapest:
    - **replace** a's last character with b's: `1 + f(i−1, j−1)`;
    - **delete** a's last character: `1 + f(i−1, j)`;
    - **insert** b's last character at the end of a: `1 + f(i, j−1)`.
4. **One row**: each cell needs its left, upper and **upper-left** neighbours. Save the upper-left value in a `diag`
   variable before it's overwritten.

</div>

## Table (`horse` → `ros`)
| | ∅ | r | o | s |
|-|---|---|---|---|
| ∅ | 0 | 1 | 2 | 3 |
| h | 1 | 1 | 2 | 3 |
| o | 2 | 2 | 1 | 2 |
| r | 3 | 2 | 2 | 2 |
| s | 4 | 3 | 3 | 2 |
| e | 5 | 4 | 4 | **3** |

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/edit-distance.html"

## Code
```java
--8<-- "src/main/java/com/playbook/dp/EditDistance.java"
```

## Complexity
Recursion O(3^(m+n)) · table O(m·n) time and space · one row O(m·n) time, O(n) space.

## Real-world uses
Spell checkers ("did you mean…"), DNA alignment, fuzzy search. Related: one edit distance (O(n) two pointers) ·
delete operation for two strings (LCS-based).
