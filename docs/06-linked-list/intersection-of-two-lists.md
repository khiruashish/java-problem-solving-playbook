# Intersection of two linked lists

<span class="badge medium">Medium</span> <span class="badge tag">Two pointers</span> <span class="badge tag">Hashing</span>

## Problem
Two lists may merge into a shared tail (the same **nodes**, not just equal values). Return the first shared node, or null.

```
A:      4 → 1 ↘
                8 → 4 → 5
B:  5 → 6 → 1 ↗
```

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for each node of A, scan B. That's O(m·n).
2. **HashSet**: store A's nodes, then walk B. O(m + n) time, O(m) space.
3. **Length insight**: after the merge point both lists are identical, so the only difference is the **head** part.
   Skip the extra `|m − n|` nodes of the longer list, then walk both together.
4. **Elegant version**: walk A then B with one pointer, and B then A with the other. Both travel `m + n`, so they meet at
   the intersection, or reach `null` at the same moment if there isn't one. You never need to count lengths.

</div>

## Why switching heads works
Pointer p travels `a + c + b` and pointer q travels `b + c + a`, where `c` is the shared tail. The totals are equal,
so they arrive at the start of `c` together.

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/IntersectionOfTwoLists.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| HashSet | O(m+n) | O(m) |
| Length difference | O(m+n) | O(1) |
| Switch heads | O(m+n) | O(1) |

## Pitfall
Comparing `val` instead of the node reference. `1→2` and `1→2` built separately do **not** intersect.
