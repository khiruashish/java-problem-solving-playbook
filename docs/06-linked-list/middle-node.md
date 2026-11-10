# Middle of the linked list

<span class="badge easy">Easy</span> <span class="badge tag">Slow / fast pointers</span>

## Problem
Return the middle node. For an even length, return the **second** middle: `1→2→3→4→5` → 3, `1→2→3→4→5→6` → 4.

## Clarifying questions
For an even length, which middle? Merge sort's split needs the **first** middle, so the code has both.

## Thinking process
<div class="steps" markdown>

1. **Two passes**: count n, then walk n/2 steps. That's O(n) and simple.
2. **One pass**: if `fast` moves twice as fast as `slow`, then when `fast` reaches the end `slow` has gone half as far.
3. The loop condition `fast != null && fast.next != null` decides which middle you get. Starting `fast` at
   `head.next` instead shifts the answer to the first middle.

</div>

## Dry run (`1→2→3→4→5→6`)
| Step | slow | fast |
|------|------|------|
| 0 | 1 | 1 |
| 1 | 2 | 3 |
| 2 | 3 | 5 |
| 3 | **4** | null → stop |

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/middle-node.html"

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/MiddleNode.java"
```

## Complexity
O(n) time and O(1) space for both approaches. The slow/fast version takes a single pass.

## Used by
[Palindrome list](palindrome-linked-list.md) (split point) · merge sort on a list · [cycle detection](linked-list-cycle.md) (same two speeds).
