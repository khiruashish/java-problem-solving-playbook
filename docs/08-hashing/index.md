# 08 · Hashing

## Concept primer

- A hash table turns a key into an array index (`hash(key) & (capacity − 1)`), so `put`, `get` and `contains` are
  **O(1) on average**. Collisions share a bucket. Java chains them in a list and turns a long chain into a red-black
  tree (8+ nodes), which caps the worst case at O(log n).
- **The trade**: you pay O(n) memory to turn "search" from O(n) into O(1). Most "brute force O(n²) → optimal O(n)"
  improvements in interviews are exactly this trade.
- **Contract**: if `a.equals(b)`, then `a.hashCode() == b.hashCode()`. Break it, and `HashMap` silently loses entries.

## Which container?

| Need | Use |
|------|-----|
| "Have I seen x?" | `HashSet` |
| Count of each x | `HashMap<K,Integer>` with `merge(x, 1, Integer::sum)` |
| First/last index of x | `HashMap<K,Integer>` with `putIfAbsent` (first) or `put` (last) |
| Keys in insertion order | `LinkedHashMap` |
| Keys sorted / range queries | `TreeMap` (O(log n), not hashing) |
| Small fixed alphabet (`a–z`) | `int[26]`: faster than any map |

## The prefix-sum + hash map pattern (memorise it)

```java
Map<Long, Integer> seen = new HashMap<>();
seen.put(0L, /* count 1, or index -1 */);     // the empty prefix
long prefix = 0;
for (int j = 0; j < n; j++) {
    prefix += a[j];
    // a subarray ending at j has sum k  ⇔  some earlier prefix == prefix - k
    use(seen.get(prefix - k));
    record(prefix, j);                         // AFTER the lookup
}
```
It solves *count* subarrays with sum k, the *longest* subarray with sum k, equal 0s and 1s, and subarray sums divisible by k.

## Problems (Easy → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [Contains duplicate](contains-duplicate.md) | <span class="badge easy">Easy</span> | `Set.add` returns false |
| 2 | [Intersection of two arrays](array-intersection.md) | <span class="badge easy">Easy</span> | Set / count map |
| 3 | [Subarray sum equals K](subarray-sum-equals-k.md) | <span class="badge medium">Medium</span> | Prefix sum + count map |
| 4 | [Longest consecutive sequence](longest-consecutive-sequence.md) | <span class="badge medium">Medium</span> | Start only at sequence starts |
| 5 | [Top K frequent elements](top-k-frequent.md) | <span class="badge medium">Medium</span> | Heap of size k / bucket sort |
| 6 | [Longest zero-sum subarray / equal 0s and 1s](longest-zero-sum-subarray.md) | <span class="badge hard">Hard</span> | Prefix sum + first index |
| 7 | [4Sum II](four-sum-count.md) | <span class="badge hard">Hard</span> | Meet in the middle |
| 8 | [Design a HashMap from scratch](my-hash-map.md) | <span class="badge expert">Expert</span> | Buckets, chaining, resize |

Also hashing-based elsewhere: [Two sum](../02-arrays/two-sum.md) · [Group anagrams](../01-strings/group-anagrams.md) ·
[First non-repeating char](../01-strings/first-non-repeating-char.md) · [LRU cache](../06-linked-list/lru-cache.md).
