# Lazy segment tree (range update, range query)

<span class="badge expert">Expert</span> <span class="badge tag">Lazy propagation</span> <span class="badge tag">Range add</span> <span class="badge tag">O(log n)</span>

## Problem
Support two operations on an array, both in O(log n):

- `add(l, r, v)`: add v to **every** element of `a[l..r]`,
- `sum(l, r)`: return the sum of `a[l..r]`.

## Example
```
a = [1, 2, 3, 4, 5]
sum(0, 4) = 15
add(1, 3, 10)   → [1, 12, 13, 14, 5]
sum(1, 3) = 39
add(0, 4, -1)   → [0, 11, 12, 13, 4]
sum(0, 4) = 40
```

## Clarifying questions
- Range assignment ("set all to v") instead of add? The same structure works, but the lazy tag stores "assign v", and combining tags is different.
- How many operations? 10⁵ updates and queries means both must be O(log n).

## Thinking process
<div class="steps" markdown>

1. **A plain [segment tree](segment-tree.md)** does point updates in O(log n). A range update would mean updating every leaf in the range, which is **O(n)**.
2. **Be lazy**: when an update **fully covers** a node's segment, don't go deeper. Update that node's sum by `v × length` and leave a
   **tag** (`lazy[node] += v`) that says "my children still owe this add".
3. **Push before you look down**: whenever an operation must visit a node's children (partial overlap), first **push** the tag:
   apply it to both children and clear it. The children are then correct before anyone reads them.
4. **Why O(log n)**: just like a range query, a range update touches at most ~4 nodes per level. The pushes happen only along those paths.

</div>

## Trace: `add(1, 3, 10)` on `[1, 2, 3, 4, 5]`
```
              [0..4] 15 → 45
             /              \
     [0..2] 6 → 26        [3..4] 9 → 19
      /        \            /        \
 [0..1] 3→13  [2] 3→13*  [3] 4→14*   [4] 5
  /     \
[0] 1  [1] 2→12*
```
`*` marks nodes fully inside [1, 3]. They get a tag and the descent stops there. `[2]`, `[3]` and `[1]` are leaves here,
but on a bigger array the tag would sit on an inner node and only move down when a later query or update needs it.

## Code
```java
--8<-- "src/main/java/com/playbook/advanced/LazySegmentTree.java"
```

## Complexity
| Structure | Range add | Range sum | Memory |
|-----------|-----------|-----------|--------|
| Plain array | O(n) | O(n) | O(n) |
| Segment tree without laziness | O(n) | O(log n) | O(4n) |
| **Lazy segment tree** | **O(log n)** | **O(log n)** | O(8n) for sums + tags |
| Fenwick tree with two BITs (range add + range sum) | O(log n) | O(log n) | O(2n) |

## Edge cases & pitfalls
- **Forgetting to push** before recursing into children: they then return stale sums.
- Multiply the tag by the **segment length** when applying it to a sum. That's `v × (hi − lo + 1)`, not just v.
- Store sums and tags as `long`. 10⁵ adds of 10⁴ on 10⁵ elements overflows an `int`.
- The tests run 30,000 random add and sum operations against a naive array.

## Related
[Segment tree](segment-tree.md) · [Fenwick tree](fenwick-tree.md) · [Sparse table](sparse-table.md) (static data, O(1) min).
