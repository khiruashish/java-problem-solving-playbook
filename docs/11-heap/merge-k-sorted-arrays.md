# Merge K sorted arrays

<span class="badge hard">Hard</span> <span class="badge tag">Min-heap</span> <span class="badge tag">Divide & conquer</span>

## Problem
Merge k sorted arrays into one sorted array. `[[1,4,7],[2,5,8],[0,9]]` → `[0,1,2,4,5,7,8,9]`. N = the total number of elements.

## Thinking process
<div class="steps" markdown>

1. **Concatenate and sort**: O(N log N). It ignores the fact that the inputs are already sorted.
2. **Heap of heads**: the next output element is always the smallest of the k current heads. Put the heads in a
   min-heap as `{value, whichArray, indexInArray}`. Poll the smallest, output it, and push the **next element from the same
   array**. The heap never holds more than k entries, so each step is O(log k).
3. **Divide & conquer**: merge the arrays in pairs (k → k/2 → … → 1). Each round touches all N elements, and there are log k rounds.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/heap/MergeKSortedArrays.java"
```

## Complexity
| Approach | Time | Extra space |
|----------|------|-------------|
| Concatenate + sort | O(N log N) | O(N) |
| Heap of heads | O(N log k) | O(k) |
| Divide & conquer | O(N log k) | O(N) per round |

## Same idea, different shape
[Merge K sorted lists](../06-linked-list/merge-k-sorted-lists.md) · Kth smallest in a sorted matrix (heap of row heads) ·
Smallest range covering K lists (heap plus the current max) · External merge sort of files that don't fit in memory.
