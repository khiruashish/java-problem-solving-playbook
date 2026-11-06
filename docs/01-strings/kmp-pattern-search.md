# Pattern search: KMP & Rabin–Karp

<span class="badge expert">Expert</span> <span class="badge tag">Pattern matching</span> <span class="badge tag">LPS table</span> <span class="badge tag">Rolling hash</span>

## Problem
Return every start index where `pattern` occurs in `text`, overlaps included.
`text = "AABAACAADAABAABA"`, `pattern = "AABA"` → `[0, 9, 12]`.

## Thinking process
<div class="steps" markdown>

1. **Naive**: try every alignment and compare. Worst case O(n·m) (`"aaaa…a"` vs `"aaab"`).
2. **Waste**: after a partial match of `j` chars fails, naive search moves the text pointer back and
   re-reads chars it has already matched.
3. **KMP insight**: the matched part is a prefix of the pattern, which we know in advance. Precompute, for each prefix,
   the longest proper prefix that is also a suffix (**LPS**). On a mismatch, set `j = lps[j-1]`. The text pointer **never moves back**.
4. **Rabin–Karp insight**: compare hashes, and roll the window hash in O(1). Verify the actual chars on a hash hit.

</div>

## LPS table for `"AABAACAABAA"`
| i | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 |
|---|---|---|---|---|---|---|---|---|---|---|----|
| p[i] | A | A | B | A | A | C | A | A | B | A | A |
| lps | 0 | 1 | 0 | 1 | 2 | 0 | 1 | 2 | 3 | 4 | 5 |

Reading it: `lps[4] = 2` because `"AABAA"` both starts and ends with `"AA"`.

## KMP search loop
<div class="steps" markdown>

1. `j` = number of pattern chars currently matched.
2. While `j > 0` and `text[i] != pattern[j]`: `j = lps[j-1]` (fall back, don't restart).
3. If they match, `j++`. If `j == m`, record `i-m+1`, then set `j = lps[j-1]` to allow overlaps.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/strings/KmpPatternSearch.java"
```

## Complexity
| Algorithm | Preprocess | Search | Notes |
|-----------|------------|--------|-------|
| Naive | — | O(n·m) | fine for short patterns |
| KMP | O(m) | O(n) | guaranteed linear |
| Rabin–Karp | O(m) | O(n+m) average, O(n·m) worst | great for multi-pattern search |
| Z-algorithm | O(n+m) | | concatenate `p + "$" + t` |

## Follow-ups
- Shortest palindrome by adding chars in front (LPS of `s + "#" + reverse(s)`).
- Repeated substring pattern (`n % (n - lps[n-1]) == 0`).
- `String.indexOf` in the JDK is naive (intrinsified), which is fine in practice for typical inputs.
