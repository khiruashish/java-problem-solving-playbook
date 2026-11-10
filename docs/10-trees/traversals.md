# Pre-, in-, post-order traversals

<span class="badge easy">Easy</span> <span class="badge tag">DFS</span> <span class="badge tag">Explicit stack</span> <span class="badge tag">Morris</span>

## Problem
Return the node values in pre-order (root, left, right), in-order (left, root, right) and post-order (left, right, root),
**recursively and iteratively**. **Expert bonus**: in-order with O(1) extra space.

```
        1
      /   \
     2     3          pre:  1 2 4 5 3 6
    / \     \         in:   4 2 5 1 3 6
   4   5     6        post: 4 5 2 6 3 1
```

## Thinking process
<div class="steps" markdown>

1. **Recursive**: the position of the "visit" line relative to the two recursive calls *is* the traversal. That's three lines each.
2. **Iterative pre-order**: pop and visit, then push **right before left**, because a stack is LIFO and left must come out first.
3. **Iterative in-order**: you can't visit a node until its whole left subtree is done. Run down the left spine pushing nodes;
   pop one, visit it, then move to its right child and repeat.
4. **Iterative post-order trick**: root → right → left is just pre-order with the pushes swapped. **Reverse** that and you
   get left → right → root.
5. **Morris (O(1) space)**: before going left, make the in-order **predecessor** (the rightmost node of the left subtree)
   point back to the current node. That temporary "thread" replaces the stack. On the second arrival, remove the thread and visit.

</div>

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/inorder-iterative.html"

## Code
```java
--8<-- "src/main/java/com/playbook/trees/Traversals.java"
```

## Complexity
| Approach | Time | Extra space |
|----------|------|-------------|
| Recursive | O(n) | O(h) call stack |
| Iterative stack | O(n) | O(h) |
| Morris | O(n) (each edge walked ≤ 3 times) | **O(1)** |

## Which traversal for which job?
| Traversal | Typical use |
|-----------|-------------|
| Pre-order | copy or serialize a tree, print a directory structure |
| In-order | a BST in sorted order, [validate a BST](validate-bst.md), kth smallest in a BST |
| Post-order | delete or free a tree, compute sizes and heights (children before parent) |
| Level-order | [per-level questions](level-order.md) |

## Testing note
The tests compare every iterative version with its recursive one on 300 random trees, and check that Morris **restores
the tree** exactly.
