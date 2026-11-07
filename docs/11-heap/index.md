# 11 · Heap / Priority Queue

## Concept primer

- A **binary heap** is a complete binary tree stored in an array (children of i are `2i+1` and `2i+2`). In a min-heap every
  parent ≤ its children, so the minimum is always at index 0.
- `peek` is **O(1)**, `offer`/`poll` are **O(log n)**, and building a heap from n items is **O(n)**. Searching for an arbitrary
  element is still O(n), because a heap is only *partially* ordered.
- Java: `PriorityQueue<>()` is a **min**-heap. Pass `Comparator.reverseOrder()` for a max-heap.

## When to reach for a heap

| The problem says… | Heap pattern |
|-------------------|--------------|
| "k largest / k smallest / top k" | heap of **size k** holding the *opposite* extreme: a min-heap for the k largest |
| "kth largest in a stream" | the root of a size-k min-heap |
| "merge k sorted …" | heap of the k current heads |
| "median of a stream" | two heaps (max-heap for the low half, min-heap for the high half) |
| "always process the most/least urgent next" | scheduling, [Dijkstra](../12-graphs/index.md) |

!!! tip "Why a min-heap for the k **largest**?"
    You need to evict the *weakest* of your current top-k quickly, and that's the minimum. Keeping the heap at size k
    makes every operation O(log k) instead of O(log n).

## Problems (Easy → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [Kth largest (array & stream)](kth-largest.md) | <span class="badge easy">Easy</span> | Min-heap of size k |
| 2 | [Top K frequent words](top-k-frequent-words.md) | <span class="badge medium">Medium</span> | Comparator with a tie-break |
| 3 | [K closest points to the origin](k-closest-points.md) | <span class="badge medium">Medium</span> | Max-heap of size k |
| 4 | [Merge K sorted arrays](merge-k-sorted-arrays.md) | <span class="badge hard">Hard</span> | Heap of (value, array, index) |
| 5 | [Task scheduler](task-scheduler.md) | <span class="badge hard">Hard</span> | Greedy max-heap + cooldown |
| 6 | [Median of a data stream](median-of-stream.md) | <span class="badge expert">Expert</span> | Two heaps |
| 7 | [Binary heap from scratch + heapsort](binary-heap.md) | <span class="badge expert">Expert</span> | Sift up / sift down |

Also heap-based: [Merge K sorted lists](../06-linked-list/merge-k-sorted-lists.md) · [Top K frequent elements](../08-hashing/top-k-frequent.md) ·
[Kth smallest / Quickselect](../09-searching-sorting/kth-smallest.md) · [Sliding window maximum](../07-stack-queue/sliding-window-maximum.md).
