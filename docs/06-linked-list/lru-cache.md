# LRU cache

<span class="badge expert">Expert</span> <span class="badge tag">HashMap</span> <span class="badge tag">Doubly linked list</span> <span class="badge tag">Design</span>

## Problem
Design a cache of fixed capacity with **O(1)** `get(key)` and `put(key, value)`. When it's full, `put` evicts the
**least recently used** key. Both `get` and `put` count as a use.

## Clarifying questions
What does `get` return for a missing key? (−1.) Does updating an existing key count as a use? (Yes.) Thread safety? (Not
required here. Mention `synchronized` or a `ConcurrentHashMap` + lock as a follow-up.)

## Thinking process
<div class="steps" markdown>

1. **O(1) lookup** needs a `HashMap`.
2. **O(1) "who is the oldest?"** needs an **order**. An array costs O(n) to shift, so use a **list**.
3. **O(1) "move this entry to the front"** means you must unlink a node from the middle in O(1), which needs
   a **doubly** linked list (you need `prev`).
4. The map stores `key → node`, so the node is found instantly and moved in O(1).
5. **Sentinel** head and tail nodes remove every null check.
6. Each node must store its **key** too: on eviction you have the tail node and must remove its key from the map.

</div>

## Structure
```
map: { 1 → ●, 3 → ●, 4 → ● }
head ⇄ [4] ⇄ [3] ⇄ [1] ⇄ tail
       most recent      least recent (evicted next)
```

## Operation trace (capacity 2)
| Op | List (MRU → LRU) | Result |
|----|------------------|--------|
| put(1,1) | 1 | |
| put(2,2) | 2, 1 | |
| get(1) | 1, 2 | 1 |
| put(3,3) | 3, 1 (evict 2) | |
| get(2) | 3, 1 | −1 |

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/LruCache.java"
```

## Complexity
O(1) for `get` and `put`. O(capacity) space.

## Production note
`new LinkedHashMap<>(16, 0.75f, true)` (access order) plus `removeEldestEntry` is a two-line LRU, and the class
shows it. Interviewers usually ask you to build the structure yourself, so know both.

## Follow-ups
LFU cache (frequency buckets, each holding its own LRU list) · TTL expiry · thread-safe LRU.
