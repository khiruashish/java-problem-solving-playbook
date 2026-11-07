# Reverse nodes in k-group

<span class="badge hard">Hard</span> <span class="badge tag">In-place reversal</span> <span class="badge tag">Recursion</span>

## Problem
Reverse the list k nodes at a time. A final group shorter than k stays as it is.
`1→2→3→4→5`: k = 2 gives `2→1→4→3→5`, and k = 3 gives `3→2→1→4→5`.

## Thinking process
<div class="steps" markdown>

1. It's [reverse a list](reverse-linked-list.md), repeated on segments, so the hard part is the **stitching**.
2. **Check first**: before reversing, make sure k nodes exist. Otherwise, leave the group alone.
3. **Recursive view**: solve the rest of the list first, then reverse my k nodes so the last of them points at that
   solved remainder. Start `prev` at the remainder instead of `null`, and the stitch happens automatically.
4. **Iterative view**: keep `groupPrev`, the node before the current group (initially a dummy). Reverse the group with
   `prev` starting at `groupNext`, point `groupPrev.next` at the old k-th node, and move `groupPrev` to the old first node,
   which is now the group's tail.

</div>

## Picture (k = 2, one group)
```
dummy → [1 → 2] → 3 → 4 → 5        groupPrev = dummy, kth = 2, groupNext = 3
dummy → [2 → 1] → 3 → 4 → 5        groupPrev = 1 (old first, now the tail)
```

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/ReverseKGroup.java"
```

## Complexity
O(n) time, since each node is reversed once. O(1) extra space iteratively, or O(n/k) stack recursively.

## Edge cases
k = 1 (no change). k = n (a full reversal). k > n (no change). The length is an exact multiple of k.
