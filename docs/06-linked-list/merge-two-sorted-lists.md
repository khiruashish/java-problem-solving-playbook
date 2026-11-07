# Merge two sorted lists

<span class="badge easy">Easy</span> <span class="badge tag">Dummy head</span> <span class="badge tag">Recursion</span>

## Problem
Merge two sorted lists into one sorted list by **splicing the existing nodes**. `1→2→4` + `1→3→4` → `1→1→2→3→4→4`.

## Thinking process
<div class="steps" markdown>

1. The smallest overall node is one of the two heads, so take it and repeat. This is the merge step of merge sort.
2. **Dummy head**: start with a fake node, so "append to result" is always `tail.next = x`, with no special case
   for the first node.
3. When one list runs out, the rest of the other is already sorted, so attach it **in one assignment**.
4. Use `<=` to take from `a` on ties. That keeps the merge **stable**.

</div>

## Approach 1 — Iterative with a dummy head (optimal)
O(n + m) time and O(1) extra space.

## Approach 2 — Recursive
`merge(a, b)`: whichever head is smaller becomes the answer, and its `next = merge(rest, other)`.
The base case is when either list is empty. It's elegant, but uses O(n + m) stack.

## Dry run
| a | b | Take | Result |
|---|---|------|--------|
| 1→2→4 | 1→3→4 | a:1 | 1 |
| 2→4 | 1→3→4 | b:1 | 1→1 |
| 2→4 | 3→4 | a:2 | 1→1→2 |
| 4 | 3→4 | b:3 | …→3 |
| 4 | 4 | a:4 | …→4 |
| null | 4 | attach b | …→4 ✅ |

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/MergeTwoSortedLists.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Iterative | O(n+m) | O(1) |
| Recursive | O(n+m) | O(n+m) stack |

## Follow-up
[Merge K sorted lists](merge-k-sorted-lists.md) · Sort a list (merge sort: [first middle](middle-node.md), then merge).
