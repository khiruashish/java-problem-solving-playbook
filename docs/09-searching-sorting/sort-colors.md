# Sort 0s, 1s and 2s (Dutch National Flag)

<span class="badge medium">Medium</span> <span class="badge tag">Three pointers</span> <span class="badge tag">In place</span>

## Problem
Sort an array containing only 0, 1 and 2, in place, **in one pass**. `[2,0,2,1,1,0]` → `[0,0,1,1,2,2]`.

## Thinking process
<div class="steps" markdown>

1. A general sort is O(n log n), which is overkill with only three distinct values.
2. **Counting**: count the 0s, 1s and 2s, then overwrite. O(n), but it takes two passes.
3. **One pass**: keep **four regions** with three pointers:
   `[0, low)` = 0s · `[low, mid)` = 1s · `[mid, high]` = unknown · `(high, n)` = 2s.
4. Look at `a[mid]`:
    - **0**: swap it to `low`. The value coming back from `low` is a known 1, so advance **both** `low` and `mid`.
    - **1**: it's already in the right region, so `mid++`.
    - **2**: swap it to `high` and `high--`. The value coming back is **unknown**, so **don't** advance `mid`.
5. Stop when `mid > high`, because the unknown region is empty.

</div>

## Dry run
| a | low | mid | high | a[mid] |
|---|-----|-----|------|--------|
| 2 0 2 1 1 0 | 0 | 0 | 5 | 2: swap with high |
| 0 0 2 1 1 2 | 0 | 0 | 4 | 0: swap with low |
| 0 0 2 1 1 2 | 1 | 1 | 4 | 0: swap with low |
| 0 0 2 1 1 2 | 2 | 2 | 4 | 2: swap with high |
| 0 0 1 1 2 2 | 2 | 2 | 3 | 1 → mid 3, 1 → mid 4 > high, done |

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/SortColors.java"
```

## Complexity
O(n) time in one pass, O(1) space.

## Where else
This is quicksort's **3-way partition** (`< pivot`, `= pivot`, `> pivot`), which makes quicksort fast on many duplicates.
The same technique segregates negatives and positives, or even and odd numbers.
