# 16 · Tries & Advanced Data Structures

## Concept primer

| Structure | Answers | Operation cost | Reach for it when… |
|-----------|---------|----------------|--------------------|
| **Trie** | "words with this prefix?" | O(L) per word or prefix | many strings share prefixes: autocomplete, word games, IP routing |
| **Segment tree** | range sum/min/max/gcd + point (or range) update | O(log n) | ranges **and** updates are mixed, with any associative operation |
| **Fenwick tree (BIT)** | prefix sums + point update | O(log n), tiny code | only prefix sums (or other invertible operations) are needed |
| **LFU cache** | evict the least-frequently-used key | O(1) | a cache with frequency-aware eviction |

!!! tip "Static array? Then you don't need any of these"
    If the array never changes, a **prefix-sum array** answers range sums in O(1). Segment and Fenwick trees exist for the
    case where **updates and queries are interleaved**, where a prefix array would need O(n) work per update.

## Problems (Medium → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [Implement a trie](trie.md) | <span class="badge medium">Medium</span> | Children array + end-of-word flag |
| 2 | [Longest common prefix via a trie](trie-longest-common-prefix.md) | <span class="badge medium">Medium</span> | Single-child walk |
| 3 | [Word search II](word-search-ii.md) | <span class="badge hard">Hard</span> | Trie + board DFS together |
| 4 | [Autocomplete / search suggestions](autocomplete.md) | <span class="badge hard">Hard</span> | Top-3 stored per node |
| 5 | [Segment tree](segment-tree.md) | <span class="badge expert">Expert</span> | Recursive build, query and update |
| 6 | [Fenwick tree](fenwick-tree.md) | <span class="badge expert">Expert</span> | Lowbit `i & −i` |
| 7 | [LFU cache](lfu-cache.md) | <span class="badge expert">Expert</span> | Frequency buckets |

Also in this playbook: [LRU cache](../06-linked-list/lru-cache.md) · [HashMap from scratch](../08-hashing/my-hash-map.md) ·
[Binary heap from scratch](../11-heap/binary-heap.md) · [Union-Find](../12-graphs/union-find.md) · [Maximum XOR (bitwise trie)](../15-bit-manipulation/maximum-xor.md).
