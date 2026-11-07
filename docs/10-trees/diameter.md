# Diameter of a binary tree

<span class="badge medium">Medium</span> <span class="badge tag">Post-order</span> <span class="badge tag">Global best</span>

## Problem
Return the number of **edges** on the longest path between any two nodes. The path does **not** have to pass through the root.
`[1,2,3,4,5]` → 3 (`4→2→1→3` or `5→2→1→3`).

## Thinking process
<div class="steps" markdown>

1. Every path has a single **highest node**, where it "bends". Through that node, the length is `height(left) + height(right)`.
2. **Brute force**: try each node as the bend and compute both heights from scratch. O(n²) on a skewed tree.
3. **Waste**: the heights are recomputed over and over. A post-order traversal already computes every height once.
4. **Pattern**: the recursion **returns** the height (what the parent needs) and **records** `l + r` in a global best
   (the answer that can't be extended upward). It's one pass, O(n).

</div>

## Picture
```
          1
        /   \
       2     3        at node 2: l = 1, r = 1 → path 2 (4-2-5)
      / \             at node 1: l = 2, r = 1 → path 3 ✅
     4   5
```

## Code
```java
--8<-- "src/main/java/com/playbook/trees/Diameter.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n²) | O(h) |
| One pass | O(n) | O(h) |

## The same "return one thing, record another" shape
[Max path sum](max-path-sum.md) · Longest univalue path · Binary tree cameras.
