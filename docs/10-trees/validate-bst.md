# Validate a binary search tree

<span class="badge medium">Medium</span> <span class="badge tag">Range bounds</span> <span class="badge tag">In-order</span>

## Problem
Return true if every node is strictly greater than **all** nodes in its left subtree and strictly less than **all**
nodes in its right subtree.

## The trap
```
        5
       / \
      1   6
         / \
        3   7       every parent/child pair looks fine, but 3 < 5 sits in 5's RIGHT subtree ❌
```
Comparing a node only with its children passes this tree. The code keeps that wrong version (`naiveChildrenOnly`),
and the test shows it being fooled.

## Thinking process
<div class="steps" markdown>

1. **Range idea**: each node must fall inside an **allowed interval** inherited from its ancestors. The root gets
   (−∞, +∞). Going left, the upper bound becomes the parent's value. Going right, the lower bound becomes the parent's value.
2. Use `long` bounds, or nullable bounds, so nodes holding `Integer.MIN_VALUE` / `MAX_VALUE` are still valid.
3. **In-order idea**: a BST's in-order traversal is **strictly increasing**. Traverse and compare each value with the
   previous one. Stop at the first violation.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/trees/ValidateBst.java"
```

## Complexity
O(n) time and O(h) space for both correct approaches.

## Follow-ups
Kth smallest in a BST (in-order, stopping at k) · Recover a BST where two nodes were swapped (in-order, finding the two
inversions) · Convert a sorted array to a balanced BST (the middle element becomes the root).
