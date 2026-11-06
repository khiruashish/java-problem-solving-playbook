# Move zeros to end

<span class="badge easy">Easy</span> <span class="badge tag">Two pointers</span> <span class="badge tag">In place</span>

## Problem
Move all `0`s to the end **in place**, keeping the order of the non-zero elements. `[0,1,0,3,12]` → `[1,3,12,0,0]`.

## Thinking process
<div class="steps" markdown>

1. **Extra array**: copy the non-zeros, and the remaining slots are zeros by default. That's O(n) space.
2. **In place**: a `write` pointer marks where the next non-zero goes. A `read` pointer scans.
3. When `a[read] != 0`, swap it into `write` and advance `write`. The relative order is preserved because `write ≤ read`.

</div>

## Dry run
| read | a | write |
|------|---|-------|
| 0 | **0** 1 0 3 12 | 0 |
| 1 | 1 **0** 0 3 12 | 1 |
| 3 | 1 3 **0** 0 12 | 2 |
| 4 | 1 3 12 0 0 | 3 |

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/MoveZeros.java"
```

## Complexity
O(n) time, O(1) space.

## Variations
Segregate 0s and 1s. Even before odd. **Dutch National Flag** (sort 0/1/2 with three pointers).
