# 10 · Trees & BST

## Concept primer

- A binary tree node has at most two children. **Height** h ranges from log n (balanced) to n (a skewed "linked list").
  Recursive solutions use **O(h)** stack, so state that explicitly.
- **BST property**: *every* node in the left subtree < node < *every* node in the right subtree. That makes an **in-order
  traversal sorted**, and a balanced BST gives O(log n) search. Java's `TreeMap` is a red-black BST.
- Almost every tree problem is a **traversal plus one decision**: what each node returns to its parent, and what it records globally.

```java
--8<-- "src/main/java/com/playbook/trees/TreeNode.java"
```

## The universal recursive template

```java
ReturnType solve(TreeNode node) {
    if (node == null) return BASE;                 // 1. base case
    ReturnType l = solve(node.left);               // 2. trust the recursion
    ReturnType r = solve(node.right);
    updateGlobalAnswer(node, l, r);                // 3. answer that "bends" at this node (optional)
    return combineForParent(node, l, r);           // 4. what the parent can use
}
```
[Diameter](diameter.md), [balanced check](tree-height.md), [LCA](lowest-common-ancestor.md) and
[max path sum](max-path-sum.md) are all this template.

## DFS or BFS?

| Use **DFS** (recursion / stack) when… | Use **BFS** (queue) when… |
|---------------------------------------|---------------------------|
| the answer combines subtree results | the answer is **per level** (level order, views, zig-zag) |
| you need root-to-leaf paths | you need the **shallowest** something (min depth) |
| the tree is wide and shallow | the tree is deep and skewed (avoids stack overflow) |

## Problems (Easy → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [Pre/in/post-order, recursive + iterative + Morris](traversals.md) | <span class="badge easy">Easy</span> | Explicit stack |
| 2 | [Height, min depth, balanced check](tree-height.md) | <span class="badge easy">Easy</span> | Post-order |
| 3 | [Level-order traversal](level-order.md) | <span class="badge easy">Easy</span> | BFS with a level-size snapshot |
| 4 | [Diameter of a binary tree](diameter.md) | <span class="badge medium">Medium</span> | Height + global best |
| 5 | [Validate a BST](validate-bst.md) | <span class="badge medium">Medium</span> | Range bounds |
| 6 | [Lowest common ancestor](lowest-common-ancestor.md) | <span class="badge medium">Medium</span> | Return found node |
| 7 | [Left / right / top / bottom view](tree-views.md) | <span class="badge medium">Medium</span> | Level + horizontal distance |
| 8 | [Build a tree from traversals](build-tree.md) | <span class="badge hard">Hard</span> | Root splits in-order |
| 9 | [Zig-zag level order](zigzag-level-order.md) | <span class="badge hard">Hard</span> | Deque / two stacks |
| 10 | [Serialize & deserialize](serialize-tree.md) | <span class="badge expert">Expert</span> | Pre-order with null markers |
| 11 | [Maximum path sum](max-path-sum.md) | <span class="badge expert">Expert</span> | Gain function |
