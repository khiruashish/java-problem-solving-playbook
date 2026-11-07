# Reverse a linked list

<span class="badge easy">Easy</span> <span class="badge tag">In-place reversal</span> <span class="badge tag">Recursion</span>

## Problem
Reverse a singly linked list and return the new head. `1→2→3→4→5` → `5→4→3→2→1`.
**Follow-up**: reverse only positions `left..right` (LeetCode 92).

## Examples
| Input | Output |
|-------|--------|
| `1→2→3→4→5` | `5→4→3→2→1` |
| `1` | `1` |
| empty | empty |

## Clarifying questions
May I modify the input, or should I build a new list? (In place is expected.) Is the list possibly cyclic? (Assume not.)

## Thinking process
<div class="steps" markdown>

1. Reversing means every arrow `a → b` becomes `b → a`, and the old tail becomes the head.
2. **Brute force**: push every node on a stack and pop them to relink. That's correct but uses O(n) memory.
3. **Insight**: you only need the **previous** node to flip the current arrow. The danger is losing the rest of
   the list when you overwrite `curr.next`, so **save it first**.
4. **Pattern**: three pointers `prev`, `curr` and `next`, flipping one arrow per step.

</div>

## Approach 1 — Stack
Push all nodes, then pop to rebuild the chain. Remember `tail.next = null`, or the old head still points
forward and you get a cycle. O(n) time, O(n) space.

## Approach 2 — Iterative three pointers (optimal)
<div class="steps" markdown>

1. `prev = null`, `curr = head`.
2. While `curr != null`: `next = curr.next`, `curr.next = prev`, `prev = curr`, `curr = next`.
3. Return `prev`.

</div>

## Approach 3 — Recursive
- **Base case**: 0 or 1 node, so return it.
- **Trust the call**: `newHead = reverse(head.next)` reverses everything after me. My old `next` is now the
  **tail** of that reversed part.
- **My job**: `head.next.next = head` (that tail points back to me), then `head.next = null`.

## Dry run (iterative, `1→2→3`)
| Step | prev | curr | Links after the step |
|------|------|------|----------------------|
| 0 | null | 1 | 1→2→3 |
| 1 | 1 | 2 | null←1  2→3 |
| 2 | 2 | 3 | null←1←2  3 |
| 3 | 3 | null | null←1←2←3 → return 3 |

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/ReverseLinkedList.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Stack | O(n) | O(n) |
| Iterative | O(n) | O(1) |
| Recursive | O(n) | O(n) stack |
| Reverse between | O(n) | O(1) |

## Edge cases & pitfalls
Empty list. One node. Forgetting `head.next = null` in the recursive version, which creates a 2-node cycle.

## Follow-ups
[Palindrome list](palindrome-linked-list.md) · [Reverse k-group](reverse-k-group.md) · [Add two numbers II](add-two-numbers.md).
