# Min stack in O(1)

<span class="badge medium">Medium</span> <span class="badge tag">Stack</span> <span class="badge tag">Design</span>

## Problem
Design a stack that supports `push`, `pop`, `top` and `getMin`, all in O(1).

## Thinking process
<div class="steps" markdown>

1. **Naive**: scan the stack in `getMin`. That's O(n).
2. **Tempting but wrong**: keep a single `min` variable. After you pop the minimum, you don't know the *previous* minimum.
3. **Insight**: a stack only changes at the top, so the minimum **below any entry never changes while that entry exists**.
   Store `minSoFar` alongside each value. `getMin` is then `peek().minSoFar`.
4. **Expert, O(1) extra space**: keep one `min` field. When pushing a new minimum x, store the encoded value `2x − oldMin`, which
   is always below x. On pop, a stored value below `min` means "this was a minimum push", and `oldMin = 2·min − stored`
   recovers the previous minimum. Use `long` so `2x − min` can't overflow.

</div>

## Trace (pairs version)
| Op | Stack (value, minSoFar), top on the right | getMin |
|----|-------------------------------------------|--------|
| push 5 | (5,5) | 5 |
| push 3 | (5,5) (3,3) | 3 |
| push 7 | (5,5) (3,3) (7,3) | 3 |
| pop | (5,5) (3,3) | 3 |
| pop | (5,5) | **5**, recovered automatically |

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/MinStack.java"
```

## Complexity
O(1) for every operation. The pairs version uses O(n) extra space; the encoded version uses O(1) extra.

## Testing note
The test runs **5,000 random operations** (including `Integer.MIN_VALUE`/`MAX_VALUE`) against a reference stack.
That's the senior way to test a data structure.

## Follow-ups
Max stack with `popMax` (needs a TreeMap plus a doubly linked list) · Min queue (two min stacks).
