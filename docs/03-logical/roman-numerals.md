# Roman numerals (both directions)

<span class="badge medium">Medium</span> <span class="badge tag">Greedy</span> <span class="badge tag">Lookup table</span> <span class="badge tag">Parsing</span>

## Problem
1. **Integer → Roman** for 1..3999.
2. **Roman → integer**.

Symbols: I = 1, V = 5, X = 10, L = 50, C = 100, D = 500, M = 1000. A smaller symbol written **before** a larger one is
subtracted, and only six such pairs are allowed: IV, IX, XL, XC, CD, CM.

## Examples
| Integer | Roman |
|---------|-------|
| 3 | III |
| 58 | LVIII (50 + 5 + 3) |
| 1994 | MCMXCIV (1000 + 900 + 90 + 4) |
| 3999 | MMMCMXCIX |

## Clarifying questions
- Range? 1..3999 with standard symbols (there is no zero, and 4000 would need a bar notation).
- Should invalid Roman strings (`"IIII"`, `"IC"`) be rejected? Here, unknown symbols throw. Strict validation is a follow-up.

## Thinking process
<div class="steps" markdown>

1. **Integer → Roman, greedy**: put the six subtractive pairs **into the table** next to the seven symbols (13 entries, largest first).
   Then it is plain greedy: take the largest value that fits, append its symbol, repeat. Greedy is safe because the table was built so it is.
2. **Integer → Roman, by digit**: each decimal digit maps independently (`num / 100 % 10` → `"", "C", …, "CM"`). Four lookups, no loop.
3. **Roman → integer**: add each symbol's value, but **subtract** it when the next symbol is larger (`I` before `V`).
4. **Right-to-left variant**: walk backwards keeping the largest value seen. A symbol smaller than that is subtracted.

</div>

## Dry run: 1994 → Roman (greedy)
| num | largest fitting | append | left |
|-----|-----------------|--------|------|
| 1994 | 1000 | M | 994 |
| 994 | 900 | CM | 94 |
| 94 | 90 | XC | 4 |
| 4 | 4 | IV | 0 |

## Dry run: "MCMXCIV" → integer
| char | value | next larger? | total |
|------|-------|--------------|-------|
| M | 1000 | no | 1000 |
| C | 100 | yes (M) | 900 |
| M | 1000 | no | 1900 |
| X | 10 | yes (C) | 1890 |
| C | 100 | no | 1990 |
| I | 1 | yes (V) | 1989 |
| V | 5 | no | **1994** |

## Code
```java
--8<-- "src/main/java/com/playbook/logical/RomanNumerals.java"
```

## Complexity
Both directions are O(1) for the bounded range (at most 15 symbols), or O(length) in general. The tests round-trip **all** 3999 values through every method.

## Edge cases & pitfalls
- Leaving the subtractive pairs out of the greedy table produces `IIII` and `VIIII`.
- `Map.of` with `char` keys boxes to `Character`. Look up with `charAt`, never with a one-letter `String`.

## Related
[Number base conversion](base-conversion.md) · [Excel column title](excel-column.md) · [String to integer](../01-strings/string-to-integer.md).
