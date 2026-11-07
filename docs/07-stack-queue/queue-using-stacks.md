# Queue using two stacks

<span class="badge easy">Easy</span> <span class="badge tag">Design</span> <span class="badge tag">Amortised O(1)</span>

## Problem
Implement a FIFO queue (`enqueue`, `dequeue`, `peek`, `isEmpty`) using only stacks. **Reverse variant**: a LIFO stack using only a queue.

## Thinking process
<div class="steps" markdown>

1. A stack reverses order. **Two reversals cancel out**, so pouring stack A into stack B puts the oldest element on top of B.
2. Use `in` for every new element and `out` to serve the oldest.
3. **The key rule**: refill `out` only when it is **empty**. If you pour while `out` still holds elements, newer elements
   land on top of older ones and FIFO order breaks.
4. Each element is pushed to `in` once, moved once, and popped from `out` once. That's 3 operations, so dequeue is **amortised O(1)**,
   even though a single dequeue can cost O(n).

</div>

## Trace
| Op | in (top on the right) | out (top on the right) | Returns |
|----|------------------------|------------------------|---------|
| enqueue 1 | 1 | | |
| enqueue 2 | 1 2 | | |
| dequeue | | 2 1 → pop 1 | 1 |
| enqueue 3 | 3 | 2 | |
| dequeue | 3 | pop 2 | 2 (out not empty, no pour) |
| dequeue | | 3 → pop 3 | 3 |

## Stack using one queue
After `offer(x)`, rotate the queue `size − 1` times (poll and re-offer) so x moves to the front. Push is O(n) and pop is O(1).

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/QueueUsingStacks.java"
```

## Complexity
| Structure | enqueue / push | dequeue / pop |
|-----------|----------------|---------------|
| Queue from 2 stacks | O(1) | O(1) amortised |
| Stack from 1 queue | O(n) | O(1) |
