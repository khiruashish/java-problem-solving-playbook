# Top K frequent elements

<span class="badge medium">Medium</span> <span class="badge tag">HashMap</span> <span class="badge tag">Heap</span> <span class="badge tag">Bucket sort</span>

## Problem
Return the k most frequent values. `[1,1,1,2,2,3]`, k = 2 → `[1,2]`. (Here ties go to the smaller value, so results are
deterministic and testable.) Can you beat O(n log n)?

## Thinking process
<div class="steps" markdown>

1. **Step 1 is always counting**: `map.merge(x, 1, Integer::sum)`. O(n). Let d = the number of distinct values.
2. **Sort the entries** by frequency, descending. O(d log d), which is fine but sorts more than needed.
3. **Min-heap of size k**: push each entry and pop the weakest whenever the size exceeds k. The heap keeps the k strongest
   seen so far. O(d log k), which is much better when k ≪ d.
4. **Bucket sort**: a frequency is between 1 and n. Use `bucket[f]` = the values that occur f times, and read buckets from n down
   until you have k values. **O(n)**.

</div>

## Bucket sort for `[1,1,1,2,2,3]`
| Frequency | 6 | 5 | 4 | 3 | 2 | 1 |
|-----------|---|---|---|---|---|---|
| Values | | | | 1 | 2 | 3 |

Reading from the right gives 1, then 2, so k = 2 is satisfied.

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/TopKFrequent.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Sort entries | O(n + d log d) | O(d) |
| Min-heap of size k | O(n + d log k) | O(d + k) |
| Bucket sort | O(n) | O(n) |
| Quickselect on entries | O(n) average | O(d) |

## Why a **min**-heap for the top k *largest*?
You want to evict the weakest candidate quickly, and the weakest of the current top k sits at the root of a min-heap.

## Related
Top K frequent words (ties broken alphabetically) · Sort characters by frequency · [Heap topic](../planned/11-heap.md).
