# Height, minimum depth & balanced check

<span class="badge easy">Easy</span> <span class="badge tag">Post-order</span> <span class="badge tag">BFS</span>

## Problem
1. **Max depth**: the number of nodes on the longest root-to-leaf path. `[3,9,20,null,null,15,7]` → 3.
2. **Min depth**: the nearest **leaf**.
3. **Balanced**: at every node, the subtree heights differ by at most 1.

## Thinking process
<div class="steps" markdown>

1. **Max depth, recursively**: `1 + max(depth(left), depth(right))`, with an empty tree at 0. It's post-order, because the children answer first.
2. **Max depth, iteratively**: BFS and count the levels. Snapshot `queue.size()` to know where each level ends.
3. **Min depth pitfall**: `1 + min(left, right)` is **wrong** for a node with one child. The null side returns 0, but a
   null side isn't a leaf. If one child is missing, recurse only into the other.
4. **Balanced, naive**: compute heights at every node, which is O(n²) on a skewed tree.
   **Better**: one post-order pass that returns the height, or **−1 meaning unbalanced**, and stops early. That's O(n).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/trees/TreeHeight.java"
```

## Complexity
O(n) time for every method. O(h) stack for the recursive versions, O(width) for BFS.

## Edge cases
An empty tree (depth 0). A single node (depth 1, balanced). A fully skewed tree (depth n, so recursion could overflow
the stack at about 10⁴ nodes).
