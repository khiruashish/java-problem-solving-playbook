# BST iterator

<span class="badge medium">Medium</span> <span class="badge tag">Controlled in-order</span> <span class="badge tag">Amortised O(1)</span> <span class="badge tag">Morris</span>

## Problem
Design an iterator over a binary search tree that returns values in **ascending order**:
`next()` returns the next smallest value, and `hasNext()` says whether one remains.
Target: `next()` in **amortised O(1)** time, using **O(h)** memory (h is the tree height).

## Example
```
        7
       / \        next() → 3, 7, 9, 15, 20, then hasNext() = false
      3   15
         /  \
        9    20
```

## Clarifying questions
- Can I copy all values up front? That works, but uses O(n) memory. The point of the question is to avoid it.
- Will the tree change during iteration? Assume not.

## Thinking process
<div class="steps" markdown>

1. **Flatten**: do an in-order traversal into a list and serve from it. Simple, but O(n) memory and O(n) work before the first `next()`.
2. **Pause the recursion.** Iterative in-order keeps a stack of the **left spine** still to visit. Keep that stack as the iterator's state:
   - constructor: push root, root.left, root.left.left, … (the smallest value ends up on top),
   - `next()`: pop node n (it is the smallest remaining), then push the left spine of **n.right**, which holds n's successor.
3. **Why amortised O(1)**: each node is pushed once and popped once over the whole iteration, so n calls do O(n) work in total.
   One call can push a long spine, but it averages out. The stack never holds more than the tree height.
4. **O(1) memory (Morris)**: thread each node's in-order predecessor's `right` pointer back to the node, use the threads to climb back up,
   and remove each thread on the second visit. The tree is fully restored once iteration ends.

</div>

## Dry run (stack iterator)
| call | pop | push left spine of right child | stack after (top first) |
|------|-----|-------------------------------|--------------------------|
| init | — | 7, 3 | `3 7` |
| next | 3 | — (no right child) | `7` |
| next | 7 | 15, 9 | `9 15` |
| next | 9 | — | `15` |
| next | 15 | 20 | `20` |
| next | 20 | — | empty → hasNext = false |

## Code
```java
--8<-- "src/main/java/com/playbook/trees/BstIterator.java"
```

## Complexity
| Variant | next() | hasNext() | Memory |
|---------|--------|-----------|--------|
| Flattened | O(1) | O(1) | O(n), plus O(n) setup |
| Stack (left spine) | **amortised O(1)**, worst case O(h) | O(1) | **O(h)** |
| Morris | amortised O(1) | O(1) | **O(1)**, but it modifies the tree temporarily |

## Edge cases & pitfalls
- Pushing `n.right` itself instead of **its whole left spine** returns the wrong order.
- Morris is not safe if anything else reads the tree mid-iteration, or if iteration is abandoned half-way (the threads stay). The tests check the tree is restored after a full drain.

## Related
[Traversals (incl. Morris)](traversals.md) · [Validate BST](validate-bst.md) · [Kth smallest (Quickselect)](../09-searching-sorting/kth-smallest.md).
