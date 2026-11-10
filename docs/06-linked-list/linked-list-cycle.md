# Detect a cycle & find its start

<span class="badge medium">Medium</span> <span class="badge tag">Floyd's tortoise & hare</span> <span class="badge tag">Hashing</span>

## Problem
1. Does the list contain a cycle?
2. If so, which node does the cycle **start** at (LeetCode 142)?
3. How long is the cycle?

## Thinking process
<div class="steps" markdown>

1. **Brute force**: remember every node you visit in a `HashSet`. The first repeat is the cycle start. O(n) space.
2. **O(1) insight**: on a circular track, a runner going 2 steps per tick gains 1 step per tick on a runner going 1,
   so it **must** land on it. It can't jump over, because the gap shrinks by exactly 1.
3. **Finding the start**: let `F` = distance from head to the start, and `a` = distance from the start to the meeting point.
   Slow walked `F + a` and fast walked `2(F + a)`. The difference `F + a` is a whole number of loops, so
   `F ≡ −a (mod loop)`. A pointer from the head and a pointer from the meeting point, both moving 1 step, therefore
   meet **exactly at the start**.

</div>

## Picture
```
head → 1 → 2 → 3 → 4
               ↑       ↓
               7 ← 6 ← 5        F = 2 (head..3), loop length 5
```

## Approach 1 — HashSet
O(n) time, O(n) space. The first node that `add` rejects is the start.

## Approach 2 — Floyd (optimal)
<div class="steps" markdown>

1. `slow` and `fast` both start at `head`. Loop while `fast` and `fast.next` exist.
2. If `slow == fast`, there's a cycle.
3. To find the start, set `p = head` and step `p` and `slow` together until they meet.
4. For the length, walk around once from the start.

</div>

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/linked-list-cycle.html"

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/LinkedListCycle.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| HashSet | O(n) | O(n) |
| Floyd | O(n) | O(1) |

## Same idea elsewhere
Find the duplicate number in an array (LeetCode 287: treat `i → a[i]` as a list) · Happy number (a cycle of digit-square sums).
