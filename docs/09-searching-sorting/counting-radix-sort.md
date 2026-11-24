# Counting sort & radix sort

<span class="badge medium">Medium</span> <span class="badge tag">Non-comparison sort</span> <span class="badge tag">Stable</span> <span class="badge tag">O(n)</span>

## Problem
Sort integers faster than O(n log n) when you know something about the values:

- **Counting sort**: values lie in a small range (max − min is small).
- **Radix sort**: values are fixed-width (32-bit ints), so sort one **byte** at a time.

## Examples
| Input | Output |
|-------|--------|
| `[4, 2, 2, 8, 3, 3, 1]` | `[1, 2, 2, 3, 3, 4, 8]` |
| `[3, -1, 0, -5]` (radix) | `[-5, -1, 0, 3]` |
| `[MAX, 0, MIN, -1]` (radix) | `[MIN, -1, 0, MAX]` |

## Clarifying questions
- What is the value range? Counting sort's memory is O(range). Range 100 is perfect, range 2³² is impossible.
- Is stability needed? Radix sort **depends** on each pass being stable.
- Negative numbers? Shift counting sort by `min`. For radix sort, flip the sign bit.

## Thinking process
<div class="steps" markdown>

1. **The Ω(n log n) lower bound applies only to comparison sorts.** Use the value itself as an array index and you escape it.
2. **Counting sort**: count each value, then turn counts into positions with a **prefix sum** (`count[v]` becomes how many elements are ≤ v).
   Place elements from **right to left**, decrementing the slot each time. Equal values keep their order, so it is **stable**.
3. **Radix sort (LSD)**: counting sort on the lowest byte, then the next byte, and so on. Because each pass is stable, ties on the current byte keep
   the order established by the lower bytes. After 4 passes, the ints are fully sorted. That is 4 · (n + 256) work, which is linear.
4. **Negatives in radix sort**: in two's complement, negatives have the top bit set, so they sort *after* positives when treated as unsigned.
   XOR with `0x80000000` flips that bit and turns signed order into unsigned order. Flip it back at the end.

</div>

## Dry run (counting sort): `[4, 2, 2, 8, 3, 3, 1]`, min = 1
| value | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |
|-------|---|---|---|---|---|---|---|---|
| count | 1 | 2 | 2 | 1 | 0 | 0 | 0 | 1 |
| prefix (≤ v) | 1 | 3 | 5 | 6 | 6 | 6 | 6 | 7 |

Walking right to left: 1 goes to slot 0, the second 3 to slot 4, the first 3 to slot 3, and so on.

## Code
```java
--8<-- "src/main/java/com/playbook/searchsort/CountingRadixSort.java"
```

## Complexity
| Sort | Time | Extra space | Stable | Use when |
|------|------|-------------|--------|----------|
| Counting | O(n + k), k = range | O(n + k) | yes | small value range (ages, grades, letters) |
| Radix (LSD, base 256) | O(4 · (n + 256)) = O(n) | O(n) | yes | many fixed-width keys |
| `Arrays.sort` (dual-pivot quicksort) | O(n log n) | O(log n) | no (primitives) | the default |

## Edge cases & pitfalls
- Counting sort with a huge range (`[MIN, MAX]`) would allocate 4 billion slots. The code refuses ranges above 50 million.
- Iterating left to right in the placement loop of the "prefix = end position" version breaks stability.
- `x >>> shift` (unsigned shift), never `>>`, when extracting bytes.

## Related
[Sort 0s, 1s, 2s](sort-colors.md) (counting sort with k = 3, or one Dutch-flag pass) · [Merge & quick sort](merge-quick-sort.md) · [Group anagrams](../01-strings/group-anagrams.md) (counting-sort the letters to build the key).
