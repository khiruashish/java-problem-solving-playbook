# Longest palindromic substring

<span class="badge hard">Hard</span> <span class="badge tag">Expand around centre</span> <span class="badge tag">DP</span> <span class="badge tag">Manacher</span>

## Problem
Return the longest contiguous palindrome in `s`. `"babad"` → `"bab"` (or `"aba"`), `"cbbd"` → `"bb"`,
`"forgeeksskeegfor"` → `"geeksskeeg"`.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: check all O(n²) substrings, each in O(n), for O(n³).
2. **DP insight**: `s[i..j]` is a palindrome iff `s[i]==s[j]` **and** `s[i+1..j-1]` is one. That's O(n²) time and space.
3. **Centre insight**: every palindrome mirrors around a centre. There are `2n−1` centres (each char,
   and each gap between chars for even lengths). Expand outward while the ends match. That's O(n²) time and **O(1) space**,
   and it's the usual interview answer.
4. **Expert**: Manacher reuses the radius of a mirrored centre, for **O(n)**.

</div>

## Approach — Expand around centre
<div class="steps" markdown>

1. For each `c`: `odd = expand(c, c)` and `even = expand(c, c+1)`.
2. `expand` moves `l--` and `r++` while `s[l]==s[r]`, then returns `r-l-1`.
3. If `len` beats the best, set `start = c - (len-1)/2` and `end = c + len/2`.

</div>

## DP table (`"cbbd"`, ✅ = palindrome)
| i \ j | c | b | b | d |
|-------|---|---|---|---|
| c | ✅ | | | |
| b | | ✅ | ✅ | |
| b | | | ✅ | |
| d | | | | ✅ |

## Manacher in one paragraph
Transform the string to `^#a#b#a#$` so every palindrome has odd length. Keep the right-most palindrome
`[center − p[center], right]`. For `i < right`, the mirror `2·center − i` already knows a radius, so
`p[i] = min(right − i, p[mirror])`. Expand only beyond that, so `right` only ever moves forward,
which gives O(n) in total.

## Code
```java
--8<-- "src/main/java/com/playbook/strings/LongestPalindromicSubstring.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force | O(n³) | O(1) |
| DP | O(n²) | O(n²) |
| Expand around centre | O(n²) | O(1) |
| Manacher | O(n) | O(n) |

## Related
Count palindromic substrings (the same expansion, but count instead of tracking the max). Longest palindromic *subsequence* (2D DP).
