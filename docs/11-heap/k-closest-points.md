# K closest points to the origin

<span class="badge medium">Medium</span> <span class="badge tag">Max-heap of size k</span> <span class="badge tag">Quickselect</span>

## Problem
Return the k points closest to (0, 0), in any order. `[[3,3],[5,−1],[−2,4]]`, k = 2 → `[[3,3],[−2,4]]`.

## Thinking process
<div class="steps" markdown>

1. **Compare squared distances** (`x² + y²`). Taking the square root costs time and brings floating point in, and it doesn't change the order.
   Use `long`, because squares of large coordinates overflow an `int`.
2. **Sort** by distance and take k. O(n log n).
3. **Max-heap of size k**: to keep the k **closest**, evict the **farthest**, which is the root of a max-heap. O(n log k).
4. **Quickselect** on distance: partition until the pivot lands at index k − 1. The first k are then the answer, unordered.
   O(n) expected.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/heap/KClosestPoints.java"
```

## Complexity
| Approach | Time | Space | Streaming? |
|----------|------|-------|------------|
| Sort | O(n log n) | O(n) | ❌ |
| Max-heap of size k | O(n log k) | O(k) | ✅ |
| Quickselect | O(n) expected | O(1) | ❌ |

## Testing note
Several points can share a distance, so different correct answers may differ. The tests compare the **sorted list of
distances** returned, not the points themselves.
