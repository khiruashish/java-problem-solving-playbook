# Remove Nth node from end

<span class="badge medium">Medium</span> <span class="badge tag">Two pointers with a gap</span> <span class="badge tag">Dummy head</span>

## Problem
Delete the n-th node from the end and return the head. `1→2→3→4→5`, n = 2 → `1→2→3→5`. n is always valid.

## Thinking process
<div class="steps" markdown>

1. To delete a node, you need the node **before** it.
2. **Two passes**: count L, and the target's predecessor is node `L − n` counting from a dummy. O(n), but two passes.
3. **One pass**: move `fast` n + 1 steps ahead of `slow`, then advance both together. When `fast` falls off the end,
   `slow` sits right before the target.
4. **Dummy head** handles `n == L` (removing the head) without a special case.

</div>

## Dry run (n = 2)
| | slow | fast |
|-|------|------|
| after the gap of 3 | dummy | 3 |
| step | 1 | 4 |
| step | 2 | 5 |
| step | **3** | null → unlink 3.next (4) |

## Recursive
Count positions **on the way back up** the recursion. When the count equals n, return `node.next` to skip yourself.

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/RemoveNthFromEnd.java"
```

## Complexity
All versions take O(n) time. The iterative ones use O(1) space; the recursive one uses O(n) stack.

## Edge cases
A single node with n = 1 (the result is empty). Removing the head. Removing the tail.
