# Task scheduler

<span class="badge hard">Hard</span> <span class="badge tag">Greedy</span> <span class="badge tag">Max-heap</span> <span class="badge tag">Math</span>

## Problem
Tasks are letters, and each takes one time unit. Two runs of the **same** task need at least `n` units in between, and the
CPU may idle. Return the minimum total time.
`[A,A,A,B,B,B]`, n = 2 → **8** (`A B _ A B _ A B`).

## Thinking process
<div class="steps" markdown>

1. **Greedy choice**: at every time unit, run the available task with the **most remaining copies**. It's the hardest one to
   fit, and postponing it only creates idle time later. "Most remaining" means a **max-heap**.
2. A task that just ran goes into a **cooldown queue** with the time it becomes available again (`now + n`). When that time
   comes, it returns to the heap.
3. **Simulation**: one loop per time unit. O(T log 26), which is effectively O(T).
4. **Formula insight**: the most frequent task (count `maxF`) dictates a skeleton of `maxF − 1` frames of length `n + 1`,
   plus a final frame containing every task that ties for `maxF`:
   `(maxF − 1) × (n + 1) + tiedAtMax`. If there are more tasks than slots, the frames just stretch and nobody idles,
   so the answer is `max(that, tasks.length)`.

</div>

## Skeleton for A×3, B×3, n = 2
```
| A B _ | A B _ | A B |
  frame   frame   last frame (A and B tie at maxF)
(3 − 1) × (2 + 1) + 2 = 8
```

## Code
```java
--8<-- "src/main/java/com/playbook/heap/TaskScheduler.java"
```

## Complexity
O(T) for both (the heap holds at most 26 entries). O(1) space.

## Testing note
The formula and the simulation are compared on 300 random task lists. The simulation explains the result, and the formula
is what you'd present as the optimised answer.

## Related
Reorganize string (no two adjacent letters equal: the same max-heap, with n = 1) · Rearrange a string with distance k.
