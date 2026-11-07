# Binary heap from scratch + heapsort

<span class="badge expert">Expert</span> <span class="badge tag">Design</span> <span class="badge tag">Sift up / down</span> <span class="badge tag">Heapsort</span>

## Problem
Implement a min-heap with `push`, `pop`, `peek` and `size`, **without** `PriorityQueue`. Then show O(n) **heapify** and in-place **heapsort**.

## Array layout
```
index:   0  1  2  3  4  5
value:   1  3  2  7  4  5            parent(i) = (i−1)/2
                                     left(i)   = 2i+1
tree:        1                       right(i)  = 2i+2
           /   \
          3     2
         / \   /
        7   4 5
```
The tree is **complete** (every level is full except the last, which fills from the left), so an array stores it with no gaps
and no pointers.

## Thinking process
<div class="steps" markdown>

1. **push**: put the new value in the only free slot, the end, which may break the heap property with its parent.
   **Sift up**: while it's smaller than its parent, swap them. Height is log n, so O(log n).
2. **pop**: the minimum is at the root. Move the **last** element to the root (to keep the tree complete), then
   **sift down**: swap with the **smaller** child while that child is smaller. O(log n).
3. **heapify in O(n)**: don't push n times (O(n log n)). Sift down every non-leaf, starting from the bottom
   (`n/2 − 1` down to 0). Most nodes are near the bottom and barely move, so the total sums to O(n).
4. **Heapsort**: build a **max**-heap in place, then repeatedly swap the root (the maximum) with the last element and sift down
   within the shrinking prefix. That's O(n log n) time with O(1) extra space, but it's **not stable**.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/heap/BinaryHeap.java"
```

## Complexity
| Operation | Time |
|-----------|------|
| push / pop | O(log n) |
| peek / size | O(1) |
| heapify | **O(n)** |
| heapsort | O(n log n), O(1) extra |

## Why heapify is O(n), not O(n log n)
About n/2 nodes are leaves (0 swaps), n/4 are one level up (≤ 1 swap), n/8 ≤ 2 swaps, and so on.
Σ (n / 2^(h+1)) · h converges to **n**. This is a classic follow-up question.

## Testing note
5,000 random push/pop operations against `java.util.PriorityQueue`, plus heapify and heapsort compared with `Arrays.sort`
on 200 random arrays.
