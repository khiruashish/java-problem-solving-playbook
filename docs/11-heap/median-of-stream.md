# Median of a data stream

<span class="badge expert">Expert</span> <span class="badge tag">Two heaps</span> <span class="badge tag">Design</span>

## Problem
Support `addNum(x)` and `findMedian()` over a stream that keeps growing. After 1, 2 the median is 1.5. After 1, 2, 3 it's 2.

## Thinking process
<div class="steps" markdown>

1. **Sorted list**: binary-search the insert position. `addNum` is O(n), because the elements shift, and `findMedian` is O(1).
2. **Insight**: the median only needs the **middle**. Split the numbers into a **lower half** and an **upper half**:
    - lower half in a **max-heap**, so its largest element is on top;
    - upper half in a **min-heap**, so its smallest element is on top.

    The median is one of the two tops, or their average.
3. **Two invariants**:
    - **order**: `max(lower) ≤ min(upper)`;
    - **size**: `lower.size() == upper.size()` or `upper.size() + 1`.
4. **Insert recipe**: push x into `lower`, move `lower`'s top to `upper` (this restores order), and if `upper` became bigger,
   move its top back to `lower` (this restores size). That's three heap operations, so O(log n).

</div>

## Trace
| add | lower (max-heap) | upper (min-heap) | Median |
|-----|------------------|------------------|--------|
| 5 | 5 | | 5 |
| 2 | 2 | 5 | 3.5 |
| 8 | 2 5 | 8 | 5 |
| 1 | 1 2 | 5 8 | 3.5 |

## Code
```java
--8<-- "src/main/java/com/playbook/heap/MedianOfStream.java"
```

## Complexity
| Approach | addNum | findMedian |
|----------|--------|------------|
| Sorted list | O(n) | O(1) |
| Two heaps | O(log n) | O(1) |

## Follow-ups
- **All values in 0..100?** Use counting buckets: O(1) add and O(100) median.
- **Sliding window median?** You also need deletion, so use two `TreeMap`s (multisets) or heaps with lazy deletion.

## Testing note
2,000 random inserts, comparing the two-heap median with the sorted-list reference after every step.
