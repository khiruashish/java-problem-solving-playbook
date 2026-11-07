# Kth smallest element (Quickselect)

<span class="badge hard">Hard</span> <span class="badge tag">Quickselect</span> <span class="badge tag">Heap</span>

## Problem
Return the k-th smallest element (1-based) of an unsorted array. `[7,10,4,3,20,15]`, k = 3 → 7.
**Twin**: the k-th largest is the (n − k + 1)-th smallest.

## Thinking process
<div class="steps" markdown>

1. **Sort**: O(n log n). Simple, but it sorts far more than needed.
2. **Max-heap of size k**: keep the k smallest seen so far, with the largest of them on top. The top is the answer. That's
   O(n log k), and it works on a **stream** where you can't hold everything.
3. **Quickselect**: partition around a pivot, exactly like quicksort. The pivot lands at its **final sorted index p**.
    - `p == k − 1`: done.
    - `p < k − 1`: the answer is on the right. **Ignore the left completely.**
    - `p > k − 1`: the answer is on the left.

    Since only one side is ever processed, the expected work is n + n/2 + n/4 + … ≈ **2n = O(n)**.
4. Use a **random pivot**. A fixed one makes sorted input O(n²).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/KthSmallest.java"
```

## Complexity
| Approach | Time | Space | Works on a stream |
|----------|------|-------|-------------------|
| Sort | O(n log n) | O(n) | ❌ |
| Max-heap of size k | O(n log k) | O(k) | ✅ |
| Quickselect | O(n) expected, O(n²) worst | O(1) | ❌ |
| Median of medians | O(n) worst | O(log n) | ❌ (complex, rarely asked) |

## Related
[Top K frequent](../08-hashing/top-k-frequent.md) · K closest points to the origin · Kth largest in a stream ([Heap](../11-heap/index.md)).
