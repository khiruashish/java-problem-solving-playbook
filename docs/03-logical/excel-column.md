# Excel column title ↔ number

<span class="badge medium">Medium</span> <span class="badge tag">Bijective base 26</span> <span class="badge tag">Horner's rule</span>

## Problem
Excel names columns A, B, …, Z, AA, AB, …, AZ, BA, …, ZZ, AAA, … Convert a title to its column number, and a number back to its title.

## Examples
| Title | Number |
|-------|--------|
| A | 1 |
| Z | 26 |
| AA | 27 |
| AZ | 52 |
| ZZ | 702 |
| AAA | 703 |
| FXSHRXW | 2147483647 |

## Clarifying questions
- Uppercase only? Here, yes: other characters throw.
- Largest input? Return a `long` so large titles don't overflow.

## Thinking process
<div class="steps" markdown>

1. **Title → number** is base conversion with Horner's rule: `n = n * 26 + digit`, where A = 1 … Z = 26.
2. **Number → title**: the trap. Plain base 26 uses digits 0..25, but here the digits are **1..26 with no zero**.
   Doing `n % 26` on 26 gives 0, which has no letter.
3. **Fix**: subtract 1 before each step. `(n − 1) % 26` picks the letter (0 → A, 25 → Z), and `(n − 1) / 26` is the rest. This system is called **bijective base 26**.
4. The recursive form writes the same idea top-down: `title(n) = title((n − 1) / 26) + letter((n − 1) % 26)`.

</div>

## Dry run: 702 → title
| n | n − 1 | (n − 1) % 26 | letter | (n − 1) / 26 |
|---|-------|--------------|--------|--------------|
| 702 | 701 | 25 | Z | 26 |
| 26 | 25 | 25 | Z | 0 |

Read in reverse: **ZZ**. Without the −1, 702 % 26 = 0 has no letter.

## Code
```java
--8<-- "src/main/java/com/playbook/logical/ExcelColumn.java"
```

## Complexity
| Direction | Time | Space |
|-----------|------|-------|
| Title → number | O(L) | O(1) |
| Number → title | O(log₂₆ n) | O(log₂₆ n) |
| Odometer brute force (tests only) | O(n · L) | O(L) |

## Edge cases & pitfalls
- 26 → "Z" and 27 → "AA". These are the boundary cases that break a naive `% 26`.
- Building the title with `insert(0, c)` is O(L²). Append, then reverse once.

## Related
[Number base conversion](base-conversion.md) · [Roman numerals](roman-numerals.md).
