# Sparse table (range minimum in O(1))

<span class="badge hard">Hard</span> <span class="badge tag">RMQ</span> <span class="badge tag">Powers of two</span> <span class="badge tag">Idempotent overlap</span>

## Problem
For a **static** array (no updates), answer many queries "minimum of `a[l..r]`" as fast as possible.

## Example
```
a = [5, 2, 4, 7, 1, 3, 6]
min(0, 3) = 2     min(2, 3) = 4     min(0, 6) = 1
```

## Clarifying questions
- Are there updates? If yes, use a [segment tree](segment-tree.md). A sparse table must be rebuilt after any change.
- Which operation? min, max and gcd allow **O(1)** queries. Sum and product only get O(log n) (see below).

## Thinking process
<div class="steps" markdown>

1. **Brute force**: scan each range. O(n) per query, so 10⁵ queries on 10⁵ numbers is 10¹⁰ operations.
2. **Precompute every range**: O(n²) memory. Too much.
3. **Precompute only power-of-two lengths**: `t[k][i] = min(a[i .. i + 2^k − 1])`. A block of 2^k is two blocks of 2^(k−1):
   `t[k][i] = min(t[k−1][i], t[k−1][i + 2^(k−1)])`. That is n log n values, and building them takes n log n time.
4. **Query with two overlapping blocks**: let `k = floor(log2(r − l + 1))`. The blocks starting at `l` and ending at `r`, each of length 2^k,
   together cover `[l, r]`. They may **overlap**, but `min(x, x) = x`, so counting an element twice is harmless. That is two lookups: **O(1)**.
5. **Sum can't overlap** (it would double-count), so decompose the length into distinct powers of two instead: O(log n) per query.

</div>

## Table for `a = [5, 2, 4, 7, 1, 3, 6]`
| k (block = 2^k) | t[k][0..] |
|-----------------|-----------|
| 0 (1) | 5 2 4 7 1 3 6 |
| 1 (2) | 2 2 4 1 1 3 |
| 2 (4) | 2 1 1 1 |

Query `min(1, 5)`: length 5 → k = 2. `min(t[2][1], t[2][5 − 4 + 1 = 2]) = min(1, 1) = 1`.

## Code
```java
--8<-- "src/main/java/com/playbook/advanced/SparseTable.java"
```

## Complexity
| Structure | Build | Query | Update | Memory |
|-----------|-------|-------|--------|--------|
| Scan | — | O(n) | O(1) | O(1) |
| Sparse table (min/max/gcd) | O(n log n) | **O(1)** | rebuild | O(n log n) |
| Sparse table (sum) | O(n log n) | O(log n) | rebuild | O(n log n) |
| Prefix sums (sum only) | O(n) | O(1) | O(n) | O(n) |
| Segment tree | O(n) | O(log n) | O(log n) | O(n) |

## Edge cases & pitfalls
- Precompute `log2` in a table. `Math.log` is slow and can round `log2(8)` down to 2.99…
- A level-k row has only `n − 2^k + 1` entries. Allocate exactly that.

## Where it is used
LCA in O(1) (an RMQ over an Euler tour) · suffix-array LCP queries · any "many queries, no updates" situation.

## Related
[Segment tree](segment-tree.md) · [Fenwick tree](fenwick-tree.md) · [Lazy segment tree](lazy-segment-tree.md) · [Constraints → complexity](../00-foundations/constraints-to-complexity.md).
