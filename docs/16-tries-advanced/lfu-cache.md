# LFU cache

<span class="badge expert">Expert</span> <span class="badge tag">HashMap</span> <span class="badge tag">LinkedHashSet buckets</span> <span class="badge tag">Design</span>

## Problem
A fixed-capacity cache with O(1) `get` and `put`. When full, evict the key with the **lowest use count**. Among ties, evict the
**least recently used**. Both `get` and `put` count as a use (LeetCode 460).

## Thinking process
<div class="steps" markdown>

1. [LRU](../06-linked-list/lru-cache.md) needs one ordering (recency). LFU needs **two**: frequency first, then recency.
2. **Frequency buckets**: `freq → LinkedHashSet<key>`. A `LinkedHashSet` gives O(1) removal **and** remembers insertion order, so
   the first key in a bucket is that bucket's least recently used.
3. **Touching a key** (on `get` or an update): move it from bucket `f` to bucket `f+1`. If bucket `f` becomes empty and `f` was
   `minFreq`, then `minFreq` becomes `f+1`. That's the only way the minimum can change during a touch.
4. **Evicting**: take the first key of the `minFreq` bucket.
5. **Inserting a new key**: frequency 1, so `minFreq = 1`. A brand-new key always has the lowest possible count.
6. Together with `values` and `counts` maps, every step is O(1).

</div>

## Trace (capacity 2)
| Op | Buckets (freq: keys, oldest first) | minFreq | Result |
|----|------------------------------------|---------|--------|
| put 1 | 1: [1] | 1 | |
| put 2 | 1: [1, 2] | 1 | |
| get 1 | 1: [2] · 2: [1] | 1 | 1 |
| put 3 | evict 2 → 1: [3] · 2: [1] | 1 | |
| get 3 | 2: [1, 3] | 2 | 3 |
| put 4 | evict 1 (oldest at freq 2) → 1: [4] · 2: [3] | 1 | |

## Code
```java
--8<-- "src/main/java/com/playbook/advanced/LfuCache.java"
```

## Complexity
O(1) for `get` and `put`. O(capacity) space.

## Testing note
A naive reference LFU (a linear scan for the minimum `(count, lastUsedTick)`) is compared with the O(1) version on 50 random
workloads × 400 operations, including capacity 0.

## Why it's "Expert"
Two orderings have to be kept consistent at the same time, and `minFreq` maintenance has subtle cases. Many candidates get
`get`/`put` right but break the tie-breaking or `minFreq` after an update.
