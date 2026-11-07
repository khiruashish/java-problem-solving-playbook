# Build a tree from pre-order & in-order

<span class="badge hard">Hard</span> <span class="badge tag">Divide & conquer</span> <span class="badge tag">HashMap</span>

## Problem
Given the pre-order and in-order traversals of a tree with **distinct** values, rebuild the tree.
pre `[3,9,20,15,7]`, in `[9,3,15,20,7]` → `[3,9,20,null,null,15,7]`. **Variant**: post-order + in-order.

## Thinking process
<div class="steps" markdown>

1. In pre-order the **first** value is always the root: `3`.
2. Find `3` in the in-order array. Everything to its **left** is the left subtree (`[9]`) and everything to its **right** is the
   right subtree (`[15,20,7]`).
3. The left subtree's **size** (1) tells you where it ends in pre-order: `pre[1..1]` is the left subtree and `pre[2..4]` is the right.
4. Recurse on both parts with index ranges, not array copies.
5. **Speed-up**: searching in-order for the root at every step is O(n), which makes the whole thing O(n²) on a skewed tree.
   Pre-build a `value → index` map so each lookup is O(1), and the total is O(n).
6. **Post + in**: the root is the **last** element of the post-order array. Everything else is the same.

</div>

## Split, visualised
```
pre:  [3] [9] [20 15 7]          in:  [9] [3] [15 20 7]
       ↑   ↑      ↑                    ↑   ↑      ↑
     root left  right                left root  right
```

## Code
```java
--8<-- "src/main/java/com/playbook/trees/BuildTree.java"
```

## Complexity
O(n) time with the index map (O(n²) without it), O(n) space.

## Why do you need the in-order array?
Pre-order and post-order together **can't** distinguish a node with only a left child from one with only a right child.
In-order is the traversal that separates left from right. This is a frequent follow-up question.

## Testing note
The tests take 200 random tree **shapes**, relabel them with distinct values, generate their traversals, rebuild, and
compare the structure.
