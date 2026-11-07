# 15 · Bit Manipulation

## Concept primer

- Java `int` is a 32-bit **two's complement** number: `−x = ~x + 1`, and the top bit is the sign.
- `>>` is an arithmetic shift (copies the sign bit). `>>>` is a logical shift (fills with 0). Use `>>>` when looping over the bits
  of a possibly negative number, or the loop never ends.
- Bit tricks turn O(n) loops into O(1) expressions, and let you store a set of up to 32/64 flags in one integer.

## The cheat sheet

| Goal | Expression |
|------|------------|
| Is bit k set? | `(n >> k & 1) == 1` |
| Set / clear / toggle bit k | `n \| 1<<k` · `n & ~(1<<k)` · `n ^ 1<<k` |
| Lowest set bit | `n & -n` |
| Clear the lowest set bit | `n & (n − 1)` |
| Power of two? | `n > 0 && (n & (n−1)) == 0` |
| Odd? (also for negatives) | `(n & 1) == 1` |
| x ^ x, x ^ 0 | `0`, `x` (the basis of every "single number" trick) |
| All subsets of n items | `for (mask = 0; mask < 1<<n; mask++)` |

## Problems (Easy → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [Check / set / clear / toggle a bit](bit-basics.md) | <span class="badge easy">Easy</span> | Masks |
| 2 | [Power of two (and four)](power-of-two.md) | <span class="badge easy">Easy</span> | `n & (n−1)` |
| 3 | [Count set bits](count-set-bits.md) | <span class="badge easy">Easy</span> | Brian Kernighan |
| 4 | [Single number I, II, III](single-number.md) | <span class="badge medium">Medium</span> | XOR / bit counting mod 3 |
| 5 | [XOR tricks: swap, odd/even, add without +](xor-tricks.md) | <span class="badge medium">Medium</span> | XOR identities |
| 6 | [Subsets via bitmask, submasks, Gray code](bitmask-subsets.md) | <span class="badge hard">Hard</span> | Enumerate masks |
| 7 | [Maximum XOR of two numbers](maximum-xor.md) | <span class="badge expert">Expert</span> | Bitwise trie / prefix greedy |

Also bit-based elsewhere: [Missing number (XOR)](../02-arrays/missing-number.md) · [Subsets](../04-recursion/subsets.md) ·
[N-Queens bitmask count](../04-recursion/n-queens.md) · [Fenwick tree (`i & -i`)](../planned/16-tries-advanced.md).
