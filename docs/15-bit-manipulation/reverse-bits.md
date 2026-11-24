# Reverse bits

<span class="badge easy">Easy</span> <span class="badge tag">Shifts</span> <span class="badge tag">Masks</span> <span class="badge tag">Lookup table</span>

## Problem
Reverse the 32 bits of an integer, treating it as unsigned: bit 0 swaps with bit 31, bit 1 with bit 30, and so on.

## Example
```
in : 00000010100101000001111010011100   (43261596)
out: 00111001011110000010100101000000   (964176192)
```
`1` → `10000000…0` (that is `Integer.MIN_VALUE` as a Java int).

## Clarifying questions
- Signed or unsigned? Java has no `uint`. The bits are what matter, so the result may print as negative.
- Will it be called millions of times? If so, a lookup table or the mask trick is worth it.

## Thinking process
<div class="steps" markdown>

1. **Bit by bit**: shift the result left, append n's lowest bit, and shift n right. Repeat 32 times.
   Use `>>>`, not `>>`: an arithmetic shift copies the sign bit and feeds 1s into a negative n.
2. **Divide and conquer**: reversing 32 bits = swap the two 16-bit halves, then the two bytes inside each half,
   then nibbles, pairs and single bits. Each step is one mask, two shifts and an OR. That is 5 steps with no loop, and it is exactly how `Integer.reverse` works.
3. **Lookup table**: precompute the reverse of every byte (256 entries). The answer is the 4 reversed bytes placed in mirrored slots.
   That makes 4 lookups per call.

</div>

## The mask steps on 8 bits `abcdefgh`
| step | mask | result |
|------|------|--------|
| swap nibbles | `F0` / `0F` | `efgh abcd` |
| swap pairs | `CC` / `33` | `ghef cdab` |
| swap bits | `AA` / `55` | `hgfe dcba` ✓ |

## Code
```java
--8<-- "src/main/java/com/playbook/bits/ReverseBits.java"
```

## Complexity
| Approach | Time per call | Extra space |
|----------|---------------|-------------|
| Loop over bits | O(32) | O(1) |
| Masks (divide & conquer) | O(log 32) = 5 steps | O(1) |
| Byte lookup table | 4 lookups | 256 ints |
| `Integer.reverse` | 5 steps (the mask version) | O(1) |

## Edge cases & pitfalls
- `n >> 1` on a negative n never reaches 0 and smears 1s. Always use `>>>` for bit-by-bit work.
- Hex literals like `0xF0F0F0F0` are negative `int`s in Java. That's fine for masking.
- The tests check every method against `Integer.reverse` on 20,000 random ints, and that reversing twice gives the input back.

## Related
[Bit basics](bit-basics.md) · [Count set bits](count-set-bits.md) · [Power of two](power-of-two.md).
