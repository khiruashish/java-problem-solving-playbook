# Maximum XOR of two numbers

<span class="badge expert">Expert</span> <span class="badge tag">Bitwise trie</span> <span class="badge tag">Greedy by bit</span>

## Problem
Return the maximum `a[i] XOR a[j]` for non-negative ints. `[3,10,5,25,2,8]` → **28** (`5 ^ 25 = 00101 ^ 11001 = 11100`).

## Thinking process
<div class="steps" markdown>

1. **Brute force**: every pair. O(n²).
2. **Greedy insight**: a 1 in a **higher** bit beats anything the lower bits can add. So decide the answer **from the top bit
   down**, always trying to make the current bit 1.
3. **Bitwise trie**: insert each number's 31 bits, from the top, into a binary trie. To find the best partner for x, walk down
   preferring the **opposite** bit at each level, since that makes the XOR bit 1. O(31) per number.
4. **Prefix-set alternative**: build the answer bit by bit. Guess that the next bit is 1 (`candidate`). That's achievable if two
   prefixes p and q satisfy `p ^ q == candidate`, which is the same as `(candidate ^ p)` being in the prefix set. It's the
   [two-sum](../02-arrays/two-sum.md) trick, with XOR instead of +.

</div>

## Trie walk for x = 5 (00101), best partner 25 (11001)
| Bit | x has | Want | Exists? | XOR bit |
|-----|-------|------|---------|---------|
| 4 | 0 | 1 | ✅ (25) | 1 |
| 3 | 0 | 1 | ✅ | 1 |
| 2 | 1 | 0 | ✅ | 1 |
| 1 | 0 | 1 | ❌ → take 0 | 0 |
| 0 | 1 | 0 | ❌ → take 1 | 0 → **11100 = 28** |

## Code
```java
--8<-- "src/main/java/com/playbook/bits/MaximumXor.java"
```

## Complexity
Brute force O(n²) · trie O(31·n) time and O(31·n) nodes · prefix set O(31·n) time, O(n) space.

## Related
Maximum XOR with an element from the array, given limits (offline queries plus a trie) · Maximum XOR subarray (a trie over prefix XORs).
[Tries](../16-tries-advanced/index.md) are covered in depth in the next topic.
