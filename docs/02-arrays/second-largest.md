# Second largest element

<span class="badge easy">Easy</span> <span class="badge tag">Linear scan</span>

## Problem
Return the second-largest **distinct** value. `[12, 35, 1, 10, 34, 1]` → 34. `[10, 5, 10]` → 5. `[10, 10]` → none.

## Clarifying questions
Distinct or not? (Here: distinct.) What should we return if there is none? (Here: `OptionalInt.empty()`, not −1, because −1 may be a valid value.)

## Thinking process
<div class="steps" markdown>

1. **Sort**, then walk back from the end until a value differs from the max. That's O(n log n).
2. **One pass**: keep `first` and `second`.
    - `x > first`: the old `first` becomes `second`, and `first = x`.
    - `first > x > second`: `second = x`. The strict `<` skips duplicates of the max.
3. Use `long` sentinels so that an input containing `Integer.MIN_VALUE` still works.

</div>

## Dry run (`[12, 35, 1, 10, 34, 1]`)
| x | first | second |
|---|-------|--------|
| 12 | 12 | −∞ |
| 35 | 35 | 12 |
| 1 | 35 | 12 |
| 10 | 35 | 12 |
| 34 | 35 | **34** |

## Code
```java
--8<-- "src/main/java/com/playbook/arrays/SecondLargest.java"
```

## Follow-up
The **Kth largest** element is a [heap](../planned/11-heap.md) of size k (O(n log k)) or Quickselect (O(n) on average).
