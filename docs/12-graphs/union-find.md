# Union-Find (Disjoint Set Union) / number of provinces

<span class="badge hard">Hard</span> <span class="badge tag">DSU</span> <span class="badge tag">Path compression</span> <span class="badge tag">Union by rank</span>

## Problem
Maintain a collection of disjoint sets with two operations: `find(x)` (which set is x in?) and `union(a, b)` (merge two sets).
Then count the **provinces** (connected groups of cities) from an adjacency matrix (LeetCode 547).

## Thinking process
<div class="steps" markdown>

1. Represent each set as a **tree**, where every element points to a parent and the root names the set.
2. `find` follows parents up to the root. `union` hangs one root under the other.
3. **Problem**: trees can degenerate into long chains, making `find` O(n).
4. **Fix 1: union by rank**: hang the **shorter** tree under the taller one, so height stays O(log n).
5. **Fix 2: path compression**: while finding, point nodes **closer to the root** (path halving: `parent[x] = parent[parent[x]]`).
   Together these give amortised **O(α(n))** per operation, where α is the inverse Ackermann function (≤ 4 for any realistic n).
6. Have `union` return `false` when both elements are already in the same set. That one boolean powers
   [cycle detection](cycle-detection.md) and [Kruskal's MST](minimum-spanning-tree.md).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/graphs/UnionFind.java"
```

## Complexity
| Operation | Naive | With rank + compression |
|-----------|-------|-------------------------|
| find / union | O(n) | O(α(n)), effectively O(1) |

## When DSU beats BFS/DFS
When edges **arrive over time** and you're asked "are a and b connected now?" between arrivals. A BFS per query would be
O(V + E) each. Examples: number of islands II, accounts merge, redundant connection, Kruskal.

## Testing note
On 200 random graphs, the number of DSU sets must equal the number of BFS components.
