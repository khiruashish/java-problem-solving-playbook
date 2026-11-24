# Sort a linked list

<span class="badge hard">Hard</span> <span class="badge tag">Merge sort</span> <span class="badge tag">Bottom-up</span> <span class="badge tag">O(1) space</span>

## Problem
Sort a singly linked list in **O(n log n)** time. Follow-up: use **O(1)** extra space.

## Examples
| Input | Output |
|-------|--------|
| `4→2→1→3` | `1→2→3→4` |
| `-1→5→3→4→0` | `-1→0→3→4→5` |
| empty | empty |

## Clarifying questions
- Stable? The merge sorts here are stable: equal values keep their original order (the tests check this with node identity).
- Can I copy values into an array? It is allowed as a baseline, but it is O(n) space.

## Thinking process
<div class="steps" markdown>

1. **Baseline**: copy the values out, `sort`, write them back. O(n log n), but O(n) space and arguably cheating.
2. **Which sort suits a list?** Quicksort needs random access for good pivots. Heapsort needs indexes. **Merge sort** only needs
   "split" and "merge", and merging two lists is just relinking with no extra array. Linked lists are merge sort's best case.
3. **Top-down**: find the middle (slow/fast), cut, sort both halves recursively, merge. O(log n) recursion stack.
4. **Bottom-up for O(1)**: skip the recursion. Merge runs of size 1, then 2, then 4…, walking the list each round and
   relinking merged runs behind a tail pointer.

</div>

## Bottom-up rounds: `4→2→1→3`
| size | runs merged | list after the round |
|------|-------------|----------------------|
| 1 | (4)(2) → 2,4 · (1)(3) → 1,3 | `2→4→1→3` |
| 2 | (2,4)(1,3) → 1,2,3,4 | `1→2→3→4` |

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/SortList.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Copy to array | O(n log n) | O(n) |
| Insertion sort (relink) | O(n²) | O(1) |
| Merge sort, top-down | O(n log n) | O(log n) stack |
| Merge sort, bottom-up | O(n log n) | **O(1)** |

## Edge cases & pitfalls
- In top-down, start `fast` at `head.next`. With `fast = head`, a 2-node list never splits and recursion never ends.
- Forgetting to cut (`slow.next = null`) before recursing.
- Use `a.val <= b.val` in the merge (not `<`) to keep the sort stable.

## Related
[Merge two sorted lists](merge-two-sorted-lists.md) · [Merge K sorted lists](merge-k-sorted-lists.md) · [Merge & quick sort](../09-searching-sorting/merge-quick-sort.md).
