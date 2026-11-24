# Symmetric tree

<span class="badge easy">Easy</span> <span class="badge tag">Mirror recursion</span> <span class="badge tag">Pair queue</span>

## Problem
Return whether a binary tree is a **mirror image of itself** around its centre.

## Examples
```
      1                 1
    /   \             /   \
   2     2           2     2
  / \   / \           \     \
 3   4 4   3           3     3
 symmetric          not symmetric
```
Level order: `[1,2,2,3,4,4,3]` → true; `[1,2,2,null,3,null,3]` → false.

## Clarifying questions
- Is an empty tree symmetric? Yes.
- Do values matter, or only shape? Both.

## Thinking process
<div class="steps" markdown>

1. **Restate**: the left subtree must be the mirror of the right subtree.
2. **Define "mirror" for two trees `a`, `b`**: their roots are equal, **a's left mirrors b's right**, and **a's right mirrors b's left**.
   That definition is already the recursion.
3. **Base cases**: both null → true. Exactly one null → false.
4. **Iterative**: put **pairs** that must mirror each other into a queue, and push the children in crossed order (outer pair, then inner pair).
5. **Level view**: every level, gaps included, must read as a palindrome. Correct, but it uses O(width) memory per level.

</div>

## Dry run: `[1,2,2,3,4,4,3]`
| pair (a, b) | equal? | next pairs |
|-------------|--------|------------|
| (2, 2) | yes | (3, 3) outer, (4, 4) inner |
| (3, 3) | yes | (null, null) × 2 |
| (4, 4) | yes | (null, null) × 2 |
| all remaining pairs null | | **true** |

## Code
```java
--8<-- "src/main/java/com/playbook/trees/SymmetricTree.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Recursive mirror | O(n) | O(h) recursion |
| Pair queue | O(n) | O(w), the tree's width |
| Level palindromes | O(n) | O(w) |
| Mirror copy + compare (tests only) | O(n) | O(n) |

## Edge cases & pitfalls
- `ArrayDeque` rejects `null` elements. The pair queue needs `LinkedList` (or a sentinel node).
- Comparing `a.left` with `b.left` checks **equality**, not mirroring. The crossed order is the whole point.

## Related
[Traversals](traversals.md) · [Tree views](tree-views.md) · [Height & balance](tree-height.md) (the same "combine two subtree answers" recursion).
