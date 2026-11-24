# Stack using queues

<span class="badge easy">Easy</span> <span class="badge tag">Design</span> <span class="badge tag">Amortisation trade-off</span>

## Problem
Implement a LIFO stack (`push`, `pop`, `top`, `isEmpty`) using **only queue operations**: `offer` to the back,
`poll` / `peek` from the front, `size`, `isEmpty`.

## Example
```
push(1), push(2)  → top() = 2, pop() = 2, isEmpty() = false, pop() = 1, isEmpty() = true
```

## Clarifying questions
- Which operation should be cheap? You can make **push** O(1) or **pop** O(1), but not both. Ask which one the caller uses more.
- What happens on an empty pop? Here it throws `NoSuchElementException`, like `ArrayDeque.pop()`.

## Thinking process
<div class="steps" markdown>

1. **Mismatch**: a queue hands back the *oldest* element and a stack the *newest*. We have to reverse the order somewhere.
2. **Option A, push-costly (one queue)**: after `offer(x)`, rotate the n − 1 older elements from the front to the back.
   Now x is at the front, so `pop` and `top` are plain `poll` and `peek`.
3. **Option B, pop-costly (two queues)**: `push` is a plain `offer`. To pop, move all but the last element to a helper queue,
   take the last one, then **swap the two queue references** (no copying back). Cache the top so `top()` stays O(1).
4. **Compare with [queue using stacks](queue-using-stacks.md)**: there, the two-stack version is *amortised* O(1) because each element moves at most once.
   Here, elements move on every expensive operation, so the cost is really O(n). That asymmetry is a good thing to point out.

</div>

## Dry run (one queue)
| op | queue (front → back) |
|----|----------------------|
| push 1 | `1` |
| push 2 | offer → `1 2`, rotate 1 → `2 1` |
| push 3 | offer → `2 1 3`, rotate 2 → `3 2 1` |
| pop | **3**, queue `2 1` |

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/StackUsingQueues.java"
```

## Complexity
| Variant | push | pop | top | Space |
|---------|------|-----|-----|-------|
| One queue (rotate on push) | O(n) | O(1) | O(1) | O(n) |
| Two queues (drain on pop) | O(1) | O(n) | O(1) with a cached top | O(n) |

## Edge cases & pitfalls
- In the two-queue version, popping the last element leaves the cached `top` stale. Guard `top()` with `isEmpty()`.
- Copying the helper back into the main queue doubles the work. Swap the references instead.

## Related
[Queue using stacks](queue-using-stacks.md) · [Stack with an array](array-stack.md) · [Min stack](min-stack.md).
