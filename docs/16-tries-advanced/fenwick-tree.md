# Fenwick tree (Binary Indexed Tree)

<span class="badge expert">Expert</span> <span class="badge tag">Lowbit</span> <span class="badge tag">Prefix sums</span>

## Problem
Support `add(i, delta)` and `prefixSum(i)`, and therefore `rangeSum(l, r)`, in O(log n), using one array and about 10 lines of code.
**Application**: count inversions in O(n log M).

## Thinking process
<div class="steps" markdown>

1. **Idea**: each slot `tree[i]` (1-based) stores the sum of a block of `lowbit(i) = i & −i` elements **ending at i**.
   For example, `tree[12]` covers `a[9..12]` (lowbit 4), and `tree[8]` covers `a[1..8]`.
2. **prefixSum(i)**: add `tree[i]`, then jump to `i − lowbit(i)` (the block just before), until 0. Each jump removes one set bit,
   so there are at most log n steps.
3. **add(i, delta)**: update every block that **contains** i. Those are found at `i + lowbit(i)`, repeatedly. Also O(log n).
4. **O(n) build**: each node adds its total into its parent `i + lowbit(i)`, once.
5. **Inversions**: walk from the right. For each x, `prefixSum(x − 1)` counts the values already seen that are smaller than x,
   which are exactly the inversions involving x. Then `add(x, 1)`.

</div>

## Which blocks does prefixSum(13) read?
```
13 = 1101₂ → tree[13] covers a[13]
12 = 1100₂ → tree[12] covers a[9..12]
 8 = 1000₂ → tree[8]  covers a[1..8]
 0         → stop: 3 reads instead of 13
```

## Code
```java
--8<-- "src/main/java/com/playbook/advanced/FenwickTree.java"
```

## Complexity
Build O(n) · add O(log n) · prefixSum/rangeSum O(log n) · space O(n).

## Fenwick vs. segment tree
| | Fenwick | [Segment tree](segment-tree.md) |
|-|---------|---------------------------------|
| Code size | ~10 lines | ~50 lines |
| Memory | n + 1 | 4n |
| Operations | invertible ones (sum, XOR) | any associative one (min, max, gcd…) |
| Range update | with tricks | with lazy propagation |

## Testing note
Random interleaved adds and range sums against direct loops, built both with the O(n) builder and with n point updates.
Inversion counts are checked against the [Searching & Sorting](../09-searching-sorting/count-inversions.md) brute force.
