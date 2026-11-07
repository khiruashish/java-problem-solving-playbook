# Bubble, selection, insertion & counting sort

<span class="badge easy">Easy</span> <span class="badge tag">Sorting</span> <span class="badge tag">Stability</span>

## Problem
Implement the classic simple sorts, and be able to explain each one's **invariant**, **stability** and **best case**.

## The invariant behind each sort
| Sort | After pass i… | Inner action |
|------|---------------|--------------|
| **Bubble** | the last i elements are the largest, in final position | swap adjacent pairs that are out of order |
| **Selection** | the first i elements are the smallest, in final position | find the min of the rest and swap it to the front |
| **Insertion** | the first i elements are sorted *among themselves* | shift bigger ones right and drop the key in |

## Thinking process
<div class="steps" markdown>

1. **Bubble**: after each pass the largest remaining element is at the end. If a pass makes **no swaps**, the array is
   sorted, so stop. That makes the best case O(n).
2. **Selection**: always exactly n − 1 swaps, which is useful when writes are expensive. It is **not stable**, because the
   long-distance swap can jump an element over its equal twin.
3. **Insertion**: like sorting playing cards in your hand. **Shift** instead of swapping, which means fewer writes.
   On nearly sorted data it's nearly O(n). TimSort uses it for small runs.
4. **Counting sort**: if values are small integers in `[0, k]`, don't compare at all. Count them, take prefix sums to get
   each value's final position, and place elements right to left to keep it **stable**. That's O(n + k).

</div>

## Stability, illustrated
Sort `[(3,a), (1,b), (3,c)]` by number. A **stable** sort gives `(1,b) (3,a) (3,c)`. An unstable one may give
`(3,c) (3,a)`. Stability matters when you sort by one key after another (for example, by name, then by department).

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/ElementarySorts.java"
```

## Complexity
| Sort | Best | Worst | Space | Stable |
|------|------|-------|-------|--------|
| Bubble (early exit) | O(n) | O(n²) | O(1) | ✅ |
| Selection | O(n²) | O(n²) | O(1) | ❌ |
| Insertion | O(n) | O(n²) | O(1) | ✅ |
| Counting | O(n + k) | O(n + k) | O(n + k) | ✅ |
