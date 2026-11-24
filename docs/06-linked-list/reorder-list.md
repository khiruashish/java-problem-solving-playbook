# Reorder list

<span class="badge medium">Medium</span> <span class="badge tag">Middle + reverse + merge</span> <span class="badge tag">O(1) space</span>

## Problem
Reorder `L0 → L1 → … → Ln` into `L0 → Ln → L1 → Ln−1 → L2 → …` **in place**, by relinking nodes (not by swapping values).

## Examples
| Input | Output |
|-------|--------|
| `1→2→3→4` | `1→4→2→3` |
| `1→2→3→4→5` | `1→5→2→4→3` |
| `1` | `1` |

## Clarifying questions
- Must I move nodes, or can I rewrite values? Move nodes. The tests check that the same node objects are relinked.
- Return a new head? No, the head stays the same, so the method returns `void`.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: put the nodes in an `ArrayList`, then link from both ends with two indexes. O(n) extra space.
2. **What do we really need?** The second half **in reverse**, interleaved with the first half.
3. **Three classic sub-problems, chained**:
   1. find the **middle** with slow/fast pointers,
   2. **reverse** the second half in place,
   3. **merge** the two halves alternately.
4. Each step is O(n) time and O(1) space. Combining known building blocks like this is exactly what the interviewer is checking.

</div>

## Dry run: `1→2→3→4→5`
| step | first half | second half |
|------|------------|-------------|
| find middle (slow stops at 3) | `1→2→3` | `4→5` |
| reverse second | `1→2→3` | `5→4` |
| weave | `1→5→2→4→3` | |

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/ReorderList.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Array of nodes | O(n) | O(n) |
| Stack of nodes | O(n) | O(n) |
| Middle + reverse + merge | O(n) | **O(1)** |

## Edge cases & pitfalls
- **Not cutting** the first half (`slow.next = null`) leaves a cycle. The merged list then never ends.
- Stop the slow/fast loop with `fast.next != null && fast.next.next != null`, so the first half is the longer one for odd n.
- 0, 1 and 2 nodes are all unchanged.

## Related
[Middle node](middle-node.md) · [Reverse a linked list](reverse-linked-list.md) · [Merge two sorted lists](merge-two-sorted-lists.md) · [Palindrome list](palindrome-linked-list.md) (also uses middle + reverse).
