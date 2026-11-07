# Kth largest element (array & stream)

<span class="badge easy">Easy</span> <span class="badge tag">Min-heap of size k</span>

## Problem
1. Kth largest in an array: `[3,2,1,5,6,4]`, k = 2 → 5.
2. **Stream** (LeetCode 703): `KthLargest(k, initial)`, then each `add(x)` returns the current kth largest.

## Thinking process
<div class="steps" markdown>

1. **Sort**: O(n log n). That's fine once, but in a stream you'd re-sort after every `add`.
2. **Insight**: you only care about the **k largest** values seen so far. Of those, the kth largest is the **smallest**,
   which is exactly what a **min-heap's root** gives you.
3. Push each value. If the size goes above k, pop the root, which is the smallest and can't be in the top k.
4. Each `add` is O(log k), and the answer is just `peek()`.

</div>

## Trace (k = 3, start `[4,5,8,2]`)
| Event | Heap (min on the left) | Answer |
|-------|------------------------|--------|
| init | 4 5 8 (2 evicted) | |
| add 3 | 4 5 8 (3 evicted) | **4** |
| add 5 | 5 5 8 | **5** |
| add 10 | 5 8 10 | **5** |
| add 9 | 8 9 10 | **8** |

## Code
```java
--8<-- "src/main/java/com/playbook/heap/KthLargest.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Sort | O(n log n) | O(n) |
| Min-heap of size k | O(n log k), O(log k) per add | O(k) |
| [Quickselect](../09-searching-sorting/kth-smallest.md) | O(n) expected | O(1), but no streams |

## Pitfall I made (and the tests caught)
Calling `add()` from the constructor seems tidy, but `add()` rightly refuses to answer while fewer than k numbers have
arrived, and that's exactly the state during construction. The constructor now fills the heap directly.
