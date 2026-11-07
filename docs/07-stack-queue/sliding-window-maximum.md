# Sliding window maximum

<span class="badge hard">Hard</span> <span class="badge tag">Monotonic deque</span> <span class="badge tag">Heap</span>

## Problem
Return the maximum of every contiguous window of size k.
`[1,3,-1,-3,5,3,6,7]`, k = 3 → `[3,3,5,5,6,7]`.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: compute the max of each window. That's O(n·k).
2. **Heap**: a max-heap of `(value, index)`. Leave expired elements in the heap and discard the top only when its index
   has left the window (lazy deletion). That's O(n log n).
3. **Insight**: when a new element x arrives, every older element **smaller than x** can never be a maximum again. x is bigger
   *and* stays in the window longer. Throw them away.
4. What's left is **decreasing from front to back**, so the **front is the max**. Keep it in a deque of indexes:
    - pop the **front** if it slid out of the window (`index ≤ i − k`);
    - pop from the **back** while `a[back] ≤ x`;
    - push x at the back; once `i ≥ k − 1`, record `a[front]`.

</div>

## Dry run (k = 3), deque shown as values
| i | x | Deque after | Window max |
|---|---|-------------|------------|
| 0 | 1 | 1 | |
| 1 | 3 | 3 | |
| 2 | −1 | 3 −1 | **3** |
| 3 | −3 | 3 −1 −3 | **3** |
| 4 | 5 | 5 | **5** |
| 5 | 3 | 5 3 | **5** |
| 6 | 6 | 6 | **6** |
| 7 | 7 | 7 | **7** |

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/SlidingWindowMaximum.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n·k) | O(1) |
| Heap with lazy deletion | O(n log n) | O(n) |
| Monotonic deque | O(n) | O(k) |

## Related
Sliding window minimum (flip the comparison) · Shortest subarray with sum ≥ K (monotonic deque on prefix sums) ·
Jump game VI (DP + monotonic deque).
