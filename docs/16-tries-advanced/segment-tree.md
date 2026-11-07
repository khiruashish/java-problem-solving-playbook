# Segment tree

<span class="badge expert">Expert</span> <span class="badge tag">Divide & conquer</span> <span class="badge tag">Range queries</span>

## Problem
An array changes over time. Support `update(i, value)` and the range queries `sum(l, r)` and `min(l, r)`, all in O(log n).

## Thinking process
<div class="steps" markdown>

1. **Plain array**: update O(1), query O(n). **Prefix sums**: query O(1), update O(n). Interleaved operations make both too slow.
2. **Segment tree**: a binary tree where each node stores the answer (sum, min, …) for a **range**. The root covers `[0, n−1]`,
   and each node's two children cover its two halves. It's stored in an array: the children of node i are `2i` and `2i+1`,
   and `4n` slots are always enough.
3. **Query(l, r)** at a node covering `[lo, hi]`:
    - **no overlap**: return the neutral value (0 for a sum, +∞ for a min);
    - **total overlap**: return the stored value;
    - **partial overlap**: ask both children and combine.

    Any range splits into O(log n) fully covered nodes.
4. **Update(i)**: walk from the root to the leaf, then recompute every ancestor on the way back up (`pull`). O(log n).
5. It works for **any associative operation**: sum, min, max, gcd, XOR, matrix product.

</div>

## Tree for `[2, 1, 5, 3]` (sum / min)
```
              [0,3] 11/1
             /          \
      [0,1] 3/1        [2,3] 8/3
       /     \          /     \
    [0] 2  [1] 1     [2] 5  [3] 3
```

## Code
```java
--8<-- "src/main/java/com/playbook/advanced/SegmentTree.java"
```

## Complexity
Build O(n) · update O(log n) · query O(log n) · space O(4n).

## Beyond this page
**Lazy propagation** handles range *updates* ("add 5 to `a[l..r]`") in O(log n), by deferring work to the children until it's
needed. That's the next step after mastering this version.

## Testing note
100 random arrays × 200 interleaved updates and queries, checked against direct loops.
