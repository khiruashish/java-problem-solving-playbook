# Huffman coding

<span class="badge expert">Expert</span> <span class="badge tag">Greedy</span> <span class="badge tag">Min-heap</span> <span class="badge tag">Prefix-free codes</span>

## Problem
Given symbol frequencies, build **prefix-free** binary codes (no code is a prefix of another) that minimise the total
encoded length `Σ freq × codeLength`.
`a:5 b:9 c:12 d:13 e:16 f:45` → **224** bits.

## Thinking process
<div class="steps" markdown>

1. Frequent symbols should get **short** codes and rare symbols **long** ones.
2. Codes are root-to-leaf paths in a binary tree (left = 0, right = 1). Leaves-only symbols guarantee the codes are
   **prefix-free**, so decoding is unambiguous.
3. **Greedy**: the two **rarest** symbols should be the deepest siblings. Merge them into one node whose weight is their sum,
   and repeat with a **min-heap** until one tree remains.
4. Each merge adds its combined weight to the total cost, because every symbol under it gets one more bit. So the total
   bits = the sum of all merge weights. That's the same calculation as the GfG "**minimum cost of connecting ropes**" problem.

</div>

## Merges for the example
| Merge | New node | Running cost |
|-------|----------|--------------|
| 5 + 9 | 14 | 14 |
| 12 + 13 | 25 | 39 |
| 14 + 16 | 30 | 69 |
| 25 + 30 | 55 | 124 |
| 45 + 55 | 100 | **224** |

## Code
```java
--8<-- "src/main/java/com/playbook/greedy/HuffmanCoding.java"
```

## Complexity
O(n log n) with a heap of n symbols. Encoding and decoding take O(total bits).

## Testing note
For 200 random alphabets, the tests check that:
- the codes are prefix-free;
- the code length times frequency equals the merge cost;
- encoding then decoding random text gives the original back.

A single-symbol alphabet still gets a 1-bit code, which is a classic edge case.
