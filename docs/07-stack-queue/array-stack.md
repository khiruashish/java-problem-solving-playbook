# Implement a stack with an array

<span class="badge easy">Easy</span> <span class="badge tag">Design</span> <span class="badge tag">Amortised analysis</span>

## Problem
Build an `int` stack with `push`, `pop`, `peek`, `isEmpty` and `size`, each in O(1). **Variant (GfG)**: implement
**two stacks in one array** without wasting space.

## Clarifying questions
Fixed capacity or growable? What should pop on an empty stack do (throw, or return a sentinel)? Generic or `int` only?

## Thinking process
<div class="steps" markdown>

1. A stack only ever touches **one end**, and the end of an array is O(1) to read and write.
2. Keep `top` = the index of the top element, with `-1` meaning empty. `push` is `data[++top] = x`, and `pop` is `data[top--]`.
3. **Growing**: when the array is full, copy it into one **twice** as big. A single resize costs O(n), but n pushes cost
   at most n + n/2 + n/4 + … < 2n copies in total, so push is **amortised O(1)**. Growing by a constant (+10) would make it O(n).
4. **Two stacks in one array**: grow stack 1 from the left and stack 2 from the right. Overflow happens only when the two tops meet,
   so no slot is wasted. Splitting the array in half would waste space whenever one stack is much smaller.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/ArrayStack.java"
```

## Complexity
| Operation | Time |
|-----------|------|
| push | O(1) amortised (O(n) on a resize) |
| pop / peek / isEmpty / size | O(1) |

## Pitfalls
Underflow (`pop` on empty) must be handled explicitly. With object stacks, null out the popped slot
(`data[top--] = null`) or the garbage collector can't free it. This is a classic memory leak from *Effective Java*.

## Follow-up
A linked-list stack (push and pop at the head) never needs a resize, but each node carries a pointer, so it uses more memory.
