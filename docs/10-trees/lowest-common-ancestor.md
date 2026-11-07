# Lowest common ancestor

<span class="badge medium">Medium</span> <span class="badge tag">Post-order</span> <span class="badge tag">BST</span>

## Problem
Return the deepest node that has both p and q as descendants (a node counts as its own descendant).

```
            3
          /   \
         5     1          LCA(5, 1) = 3
        / \   / \         LCA(5, 4) = 5   (a node can be its own ancestor)
       6   2 0   8        LCA(7, 4) = 2
          / \
         7   4
```

## Thinking process
<div class="steps" markdown>

1. **Path approach**: record root → p and root → q with backtracking DFS. The LCA is the **last node the two paths share**.
   It takes two traversals and O(h) memory.
2. **One-pass insight**: ask each subtree "did you find p or q?"
    - If the current node *is* p or q, return it. Anything below it can't be lower than it.
    - If the left subtree found one **and** the right subtree found the other, this node is where the paths **split**, so it's the LCA.
    - If only one side found something, pass that result upward.
3. **BST**: values tell you the direction. If both are smaller, go left. If both are larger, go right. Otherwise you're at the
   split point. That's O(h) with no recursion.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/trees/LowestCommonAncestor.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Paths | O(n) | O(h) |
| One-pass recursive | O(n) | O(h) |
| BST walk | O(h) | O(1) |

## Follow-ups
p or q might be missing (track whether **both** were found) · LCA with parent pointers (this is
[intersection of two lists](../06-linked-list/intersection-of-two-lists.md)!) · Distance between two nodes
(`depth(p) + depth(q) − 2·depth(lca)`).
