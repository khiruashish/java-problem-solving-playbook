# Search in a rotated sorted array

<span class="badge medium">Medium</span> <span class="badge tag">Binary search</span>

## Problem
A sorted array of **distinct** values was rotated at an unknown pivot: `[0,1,2,4,5,6,7]` → `[4,5,6,7,0,1,2]`.
Find `target` in O(log n). `0` → 4, `3` → −1. **Bonus**: find the minimum (the rotation point).

## Thinking process
<div class="steps" markdown>

1. A linear search is O(n), and the problem explicitly asks for O(log n).
2. **Key observation**: cut the range at `mid`, and **at least one half is perfectly sorted**. If `a[lo] ≤ a[mid]`,
   the left half is sorted; otherwise the right half is.
3. In the sorted half, a simple range check (`a[lo] ≤ target < a[mid]`) tells you whether the target is there. If it is,
   go there. If not, it must be in the other half.
4. **Minimum**: compare `a[mid]` with `a[hi]`. If `a[mid] > a[hi]`, the drop (the minimum) is strictly to the right.
   Otherwise mid could be the minimum, so keep it.
5. **Alternative**: find the pivot, then run a normal binary search with indexes shifted by the pivot (`(mid + pivot) % n`).

</div>

## Dry run (target 0 in `[4,5,6,7,0,1,2]`)
| lo | hi | mid | a[mid] | Sorted half | Target inside? | Action |
|----|----|-----|--------|-------------|----------------|--------|
| 0 | 6 | 3 | 7 | left [4..7] | no | lo = 4 |
| 4 | 6 | 5 | 1 | left [0..1] | yes | hi = 4 |
| 4 | 4 | 4 | 0 | found ✅ | | |

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/RotatedSortedArray.java"
```

## Complexity
O(log n) time, O(1) space.

## Follow-up: duplicates (LeetCode 81)
With `[1,0,1,1,1]`, `a[lo] == a[mid] == a[hi]` and you can't tell which half is sorted. Shrink both ends by one
(`lo++`, `hi--`). The worst case degrades to O(n), and you should say so.
