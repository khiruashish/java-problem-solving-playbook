# 09 · Searching & Sorting

## Concept primer

- **Binary search** works on anything **monotone**, not just sorted arrays: a rotated array, a slope, or a yes/no
  function of the answer ("can we do it with limit L?"). If you can discard half the candidates with one check, use it.
- **Sorting** is often step 1 of a better algorithm: duplicates become neighbours, intervals can be swept, and two pointers work.

## The two binary-search templates (pick one and never mix them)

```java
// Closed [lo, hi] — "find an exact match"
int lo = 0, hi = n - 1;
while (lo <= hi) { int mid = (lo + hi) >>> 1; if (ok) return mid; if (tooSmall) lo = mid + 1; else hi = mid - 1; }

// Half-open [lo, hi) — "find the first index where condition becomes true" (lower bound)
int lo = 0, hi = n;
while (lo < hi)  { int mid = (lo + hi) >>> 1; if (condition(mid)) hi = mid; else lo = mid + 1; }
return lo;   // first true, or n if none
```

!!! warning "The classic bug"
    `(lo + hi) / 2` overflows when `lo + hi > Integer.MAX_VALUE`. That bug sat in the JDK's own `Arrays.binarySearch`
    for nine years. Use `lo + (hi - lo) / 2` or `(lo + hi) >>> 1`.

## Sorting algorithms at a glance

| Algorithm | Best | Average | Worst | Space | Stable | Notes |
|-----------|------|---------|-------|-------|--------|-------|
| Bubble | O(n) | O(n²) | O(n²) | O(1) | ✅ | early exit when no swaps |
| Selection | O(n²) | O(n²) | O(n²) | O(1) | ❌ | fewest swaps (n − 1) |
| Insertion | O(n) | O(n²) | O(n²) | O(1) | ✅ | best for small or nearly sorted input |
| Merge | O(n log n) | O(n log n) | O(n log n) | O(n) | ✅ | linked lists, external sort |
| Quick | O(n log n) | O(n log n) | O(n²) | O(log n) | ❌ | fastest in practice, random pivot |
| Heap | O(n log n) | O(n log n) | O(n log n) | O(1) | ❌ | see [Heap](../11-heap/index.md) |
| Counting | O(n + k) | O(n + k) | O(n + k) | O(k) | ✅ | small integer range |

**What Java uses**: `Arrays.sort(int[])` is a dual-pivot quicksort, since primitives don't need stability.
`Arrays.sort(Object[])` and `Collections.sort` use TimSort, a stable merge/insertion hybrid.

## Problems (Easy → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [Linear & binary search](binary-search.md) | <span class="badge easy">Easy</span> | Halve the range |
| 2 | [Bubble, selection, insertion, counting sort](elementary-sorts.md) | <span class="badge easy">Easy</span> | Invariants & stability |
| 3 | [First & last occurrence](first-last-occurrence.md) | <span class="badge medium">Medium</span> | Lower/upper bound |
| 4 | [Search in rotated sorted array](rotated-sorted-array.md) | <span class="badge medium">Medium</span> | One half is always sorted |
| 5 | [Merge sort & quick sort](merge-quick-sort.md) | <span class="badge medium">Medium</span> | Divide & conquer |
| 6 | [Sort 0s, 1s, 2s](sort-colors.md) | <span class="badge medium">Medium</span> | Dutch national flag |
| 7 | [Find a peak element](find-peak-element.md) | <span class="badge hard">Hard</span> | Binary search on the slope |
| 8 | [Kth smallest: Quickselect](kth-smallest.md) | <span class="badge hard">Hard</span> | Partition one side |
| 9 | [Count inversions](count-inversions.md) | <span class="badge hard">Hard</span> | Count while merging |
| 10 | [Median of two sorted arrays](median-two-sorted-arrays.md) | <span class="badge expert">Expert</span> | Partition binary search |
| 11 | [Split array largest sum / allocate pages](split-array-largest-sum.md) | <span class="badge expert">Expert</span> | Binary search on the answer |
