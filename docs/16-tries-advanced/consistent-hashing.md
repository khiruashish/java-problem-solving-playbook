# Consistent hashing

<span class="badge expert">Expert</span> <span class="badge tag">Hash ring</span> <span class="badge tag">TreeMap.ceilingEntry</span> <span class="badge tag">System design</span>

## Problem
Spread keys (cache entries, user ids) across N servers so that:

1. the load is roughly even, and
2. **adding or removing a server moves as few keys as possible**.

## Why `hash(key) % N` fails
Going from 4 to 5 servers changes `hash % N` for about **80%** of keys, so almost the whole cache is invalidated at once.
The tests measure this directly: well over 70% of 20,000 keys move.

## Thinking process
<div class="steps" markdown>

1. **Put everything on a ring**: hash both servers and keys into the same 32-bit space and treat it as a circle.
2. **Ownership rule**: a key belongs to the **first server clockwise** from its position. In Java that is
   `TreeMap.ceilingEntry(hash(key))`, wrapping to `firstEntry()` past the end.
3. **Adding a server** only takes over the arc between it and its counter-clockwise neighbour. Only keys in that arc move,
   about **K / N** of them. Every other key keeps its server.
4. **Virtual nodes**: with one point per server, the arcs have very uneven lengths. Put each server on the ring **100–200 times**
   (`"cache-a#0"`, `"cache-a#1"`, …). The arcs average out, so the load evens out, and a removed server's keys spread over *all* the others.
5. **Hash quality matters**: similar strings must land far apart. The code uses FNV-1a plus a murmur3 finaliser, which is deterministic across JVMs.

</div>

## Picture
```
              0 / 2³²
           ●  cache-b#7
      ○ key "user:42"  ──clockwise──▶ ● cache-a#3   (owner)
   ● cache-c#1
                       ● cache-b#2
```

## What the tests prove (4 servers × 150 virtual nodes, 20,000 keys)
| check | result |
|-------|--------|
| every server's share | between 15% and 35% (25% is ideal) |
| keys that move when a 5th server is added | 10–30% (ideal 20%), and **every** moved key moves *to* the new server |
| keys that move with `% N`, going 4 → 5 | over 70% |
| removing the 5th server again | every key returns to its original server |

## Code
```java
--8<-- "src/main/java/com/playbook/advanced/ConsistentHashing.java"
```

## Complexity
| Operation | Time |
|-----------|------|
| Lookup (`ceilingEntry`) | O(log (N · V)) |
| Add / remove a server | O(V · log (N · V)) |
| Memory | O(N · V) ring entries |

N = servers, V = virtual nodes per server.

## Edge cases & pitfalls
- Forgetting the wrap-around: a key hashing past the last point belongs to the **first** point.
- Using `String.hashCode()` directly: short keys like `"s#1"`, `"s#2"` land next to each other and the load skews.
- Too few virtual nodes gives an uneven load. Too many wastes memory and slows down membership changes.

## Where it is used
Distributed caches (Memcached clients), Amazon Dynamo / Cassandra partitioning, CDN request routing, sharded queues.

## Related
[HashMap from scratch](../08-hashing/my-hash-map.md) · [LRU cache](../06-linked-list/lru-cache.md) · [LFU cache](lfu-cache.md).
