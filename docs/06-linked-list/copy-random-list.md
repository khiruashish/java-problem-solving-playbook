# Copy list with random pointer

<span class="badge hard">Hard</span> <span class="badge tag">Hashing</span> <span class="badge tag">Interleaving</span>

## Problem
Each node has `next` and a `random` pointer to any node or null. Return a **deep copy**. No pointer in the copy may
reference an original node.

## Thinking process
<div class="steps" markdown>

1. Copying `next` is easy. The trouble is that `random` may point **forward** to a node you haven't copied yet.
2. **Two passes with a map**: pass 1 creates every copy (`original → copy`). Pass 2 sets
   `copy.next = map.get(orig.next)` and `copy.random = map.get(orig.random)`. O(n) space.
3. **O(1) space insight**: the map exists only to find "the copy of X". Put each copy **right after** its original
   (`A → A' → B → B'`). Then the copy of X is simply `X.next`.
4. Three passes: insert the copies, set `copy.random = orig.random.next`, then unweave the two lists, restoring the original.

</div>

## Interleaving, visualised
```
original:     A → B → C
interleaved:  A → A' → B → B' → C → C'
random:       A'.random = A.random.next
split:        A → B → C      A' → B' → C'
```

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/CopyRandomList.java"
```

## Complexity
| Approach | Time | Extra space |
|----------|------|-------------|
| HashMap | O(n) | O(n) |
| Interleave | O(n) | O(1) |

## Related
Clone a graph (the same map idea, done with BFS/DFS) · Clone a binary tree with random pointers.
