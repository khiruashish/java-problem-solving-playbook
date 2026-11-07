# Design a HashMap from scratch

<span class="badge expert">Expert</span> <span class="badge tag">Design</span> <span class="badge tag">Separate chaining</span> <span class="badge tag">Resizing</span>

## Problem
Implement `put`, `get`, `remove`, `containsKey` and `size` with **average O(1)** operations, **without** using any built-in
hash table. Support any key type, including `null`.

## Clarifying questions
Generic keys or only `int`? Must it grow? (Yes.) Null keys? Thread safety? (No. Mention `ConcurrentHashMap` as a follow-up.)

## Thinking process
<div class="steps" markdown>

1. **Array of buckets**: the index is derived from `key.hashCode()`. Different keys can land in the same bucket, a
   **collision**, so each bucket holds a small **linked list** of entries (separate chaining).
2. **Index**: `hash & (capacity − 1)`. With a **power-of-two** capacity this equals `hash mod capacity`, is never
   negative, and costs one CPU instruction.
3. **Spread the bits**: `h ^ (h >>> 16)` mixes the high bits into the low bits. Without it, hash codes that differ only in
   their high bits would all collide, because the mask only keeps the low bits. This is the same trick `java.util.HashMap` uses.
4. **Lookup**: walk the chain comparing the **stored hash first** (a cheap `int` comparison), then `Objects.equals` (which is
   null-safe and possibly expensive).
5. **Load factor 0.75**: once `size > capacity × 0.75`, chains start growing. **Double** the table and re-bucket. A single resize is O(n),
   but doubling makes `put` amortised O(1), for the same reason as an [array stack](../07-stack-queue/array-stack.md).
6. Store the hash in each node, so a resize never calls `hashCode()` again.

</div>

## Structure
```
table (capacity 8)
[0] → null
[1] → ("b", 2) → ("j", 9) → null      ← collision chain
[2] → null
[3] → ("d", 4) → null
 …
```

## Code
```java
--8<-- "src/main/java/com/playbook/hashing/MyHashMap.java"
```

## Complexity
| Operation | Average | Worst case (everything collides) |
|-----------|---------|----------------------------------|
| put / get / remove | O(1) | O(n). Java 8 caps this at O(log n) by turning long chains into trees |
| resize | O(n), amortised O(1) per put | |

## How it's tested
The test runs a **differential check**: 20,000 random `put`/`remove`/`get` calls applied to both `MyHashMap` and
`java.util.HashMap`, asserting identical results and sizes through many resizes. It also uses a key class whose
`hashCode()` always returns 7, to stress the collision chains.

## What `java.util.HashMap` adds on top
- **Treeification**: a chain with 8 or more nodes becomes a red-black tree, when the table has 64 or more buckets.
- **Resize splitting**: after doubling, each node either keeps its index or moves to `index + oldCapacity`, decided by one bit.
- **Fail-fast iterators** using `modCount`.

## Alternative: open addressing
Store the entries in the array itself, and on a collision probe the next slot (linear or quadratic probing).
It's cache-friendly, but deletion needs **tombstones**, and performance drops sharply above about 70% load.
`ThreadLocal` and Python's `dict` use variants of it.
