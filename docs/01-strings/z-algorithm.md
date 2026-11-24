# Z-algorithm

<span class="badge expert">Expert</span> <span class="badge tag">Pattern matching</span> <span class="badge tag">Z-box</span> <span class="badge tag">Linear time</span>

## Problem
Build the **Z array** of a string: `z[i]` is the length of the longest substring starting at `i` that is
also a **prefix** of the string (`z[0]` is defined as 0). Then use it to find every occurrence of a pattern
in a text in O(n + m).

## Examples
| s | z |
|---|---|
| `aabcaabxaaaz` | `0 1 0 0 3 1 0 0 2 2 1 0` |
| `aaaaa` | `0 4 3 2 1` |

Search: `text = "AABAACAADAABAABA"`, `pattern = "AABA"` → `[0, 9, 12]`.

## Clarifying questions
- Overlapping matches? Yes, they are all reported (`"aa"` in `"aaaa"` → 0, 1, 2).
- Empty pattern? Return an empty list.
- Alphabet? Any `char`. The trick needs one separator that never appears in the input; `'\u0000'` is used here.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: for every i, compare `s[i..]` with `s[0..]` char by char. Worst case O(n²) on `"aaaa…"`.
2. **Waste**: if we already know `s[l..r)` equals the prefix `s[0..r−l)` (a **Z-box**), then for any i inside the box,
   `s[i..r)` equals `s[i−l..r−l)`. We have *already computed* `z[i−l]` for that position.
3. **Insight**: start `z[i]` at `min(r − i, z[i − l])` and only compare characters **beyond r**. Each such comparison
   pushes `r` right, and r only moves forward, so the total work is O(n).
4. **Pattern search**: compute Z of `pattern + '\0' + text`. A position with `z == m` is a full match. The separator
   caps every z value at m.

</div>

## Dry run: `aabcaabxaaaz`
| i | inside box [l, r)? | start z | compared | z[i] | new box |
|---|--------------------|---------|----------|------|---------|
| 1 | no | 0 | `a`=`a`, `b`≠`a` | 1 | [1, 2) |
| 2 | no | 0 | `b`≠`a` | 0 | — |
| 4 | no | 0 | `aab` then `x`≠`c` | 3 | [4, 7) |
| 5 | yes, z[1] = 1, r − i = 2 | 1 | `b`≠`a` | 1 | — |
| 6 | yes, z[2] = 0 | 0 | `b`≠`a` | 0 | — |
| 8 | no | 0 | `aa` then `a`≠`b` | 2 | [8, 10) |
| 9 | yes, min(1, z[1] = 1) = 1 | 1 | `a`=`a`, `z`≠`b` | 2 | [9, 11) |

## Code
```java
--8<-- "src/main/java/com/playbook/strings/ZAlgorithm.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute-force Z | O(n²) | O(n) |
| Z-algorithm | **O(n)** | O(n) |
| Search via Z | O(n + m) | O(n + m) |

## Z vs KMP
Both are linear. KMP's LPS table looks at **suffixes ending** at i, while Z looks at **prefixes starting** at i.
Z is often easier to write from memory, and its array answers more questions directly: "how many prefixes
occur at each position", "string period", "number of distinct substrings" (with a loop).

## Edge cases & pitfalls
- Forgetting the separator: without it, z values can run past the pattern into the text and exceed m.
- Using `z[i − l]` without the `r − i` cap. The mirrored value is only trustworthy *inside* the box.
- Strings of length 0 or 1.

## Related
[KMP & Rabin–Karp](kmp-pattern-search.md) · [Longest palindromic substring](longest-palindromic-substring.md) (Manacher uses the same "reuse the mirror inside a box" idea).
