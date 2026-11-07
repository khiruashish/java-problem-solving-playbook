# Merge K sorted lists

<span class="badge expert">Expert</span> <span class="badge tag">Min-heap</span> <span class="badge tag">Divide & conquer</span>

## Problem
Merge k sorted lists into one sorted list. `[1→4→5, 1→3→4, 2→6]` → `1→1→2→3→4→4→5→6`.
Let N be the total number of nodes.

## Thinking process
<div class="steps" markdown>

1. **Collect and sort**: O(N log N). It ignores the fact that the input is already sorted.
2. **Merge one by one**: merge list 1 with 2, the result with 3, and so on. The growing result is re-walked k times,
   which gives **O(k·N)**.
3. **Heap insight**: at any moment the next output node is the **smallest of the k current heads**. A min-heap of
   size k gives that in O(log k), so the total is **O(N log k)**.
4. **Divide & conquer**: merge lists in pairs, like merge sort. There are log k rounds and each touches every node once,
   so it's also **O(N log k)**, with no heap.

</div>

## Heap trace (first steps)
| Heap (heads) | Pop | Push |
|--------------|-----|------|
| 1a, 1b, 2 | 1a | 4a |
| 1b, 2, 4a | 1b | 3b |
| 2, 3b, 4a | 2 | 6 |
| 3b, 4a, 6 | 3b | 4b |

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/MergeKSortedLists.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Collect + sort | O(N log N) | O(N) |
| Sequential merge | O(k·N) | O(1) |
| Min-heap | O(N log k) | O(k) |
| Divide & conquer | O(N log k) | O(log k) stack |

## Pitfall
`PriorityQueue<ListNode>` needs a comparator, because `ListNode` isn't `Comparable`. Use
`Integer.compare(a.val, b.val)` rather than `a.val - b.val`, which overflows for extreme values.

## Related
Kth smallest element in a sorted matrix · Smallest range covering elements from K lists. Both use the same "heap of k heads" idea.
