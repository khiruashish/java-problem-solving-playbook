# Longest palindromic subsequence

<span class="badge hard">Hard</span> <span class="badge tag">Interval DP</span> <span class="badge tag">LCS reduction</span>

## Problem
Return the length of the longest **subsequence** of `s` that is a palindrome. A subsequence keeps the original order but may skip characters.

## Examples
| s | answer | one palindrome |
|---|--------|----------------|
| `bbbab` | 4 | `bbbb` |
| `cbbd` | 2 | `bb` |
| `racecar` | 7 | itself |

## Clarifying questions
- Subsequence or substring? A subsequence (characters can be skipped). The substring version is [a different problem](../01-strings/longest-palindromic-substring.md).
- Return the length or the string? The length. The string can be rebuilt from the table.

## Thinking process
<div class="steps" markdown>

1. **Look at both ends** of `s[i..j]`:
   - if `s[i] == s[j]`, both can wrap the best palindrome inside: `2 + LPS(i+1, j−1)`,
   - otherwise at least one end isn't used: `max(LPS(i+1, j), LPS(i, j−1))`.
2. Plain recursion is exponential, but there are only n² intervals. That is **interval DP**.
3. **Fill order**: `dp[i][j]` needs `dp[i+1][…]` (the row below) and `dp[i][j−1]` (to its left). So loop `i` from n−1 down to 0, and `j` upward from i+1.
4. **Space**: each row only needs the row below, so two rows are enough (O(n) space).
5. **The reduction**: a palindromic subsequence reads the same backwards, so **LPS(s) = LCS(s, reverse(s))**. Good to mention, and it reuses [LCS](longest-common-subsequence.md).

</div>

## DP table: `bbbab` (dp[i][j], upper triangle)
| i \ j | 0 b | 1 b | 2 b | 3 a | 4 b |
|-------|-----|-----|-----|-----|-----|
| 0 b | 1 | 2 | 3 | 3 | **4** |
| 1 b | | 1 | 2 | 2 | 3 |
| 2 b | | | 1 | 1 | 3 |
| 3 a | | | | 1 | 1 |
| 4 b | | | | | 1 |

`dp[0][4]`: `s[0] = s[4] = b` → 2 + dp[1][3] = 2 + 2 = 4.

## Code
```java
--8<-- "src/main/java/com/playbook/dp/LongestPalindromicSubsequence.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Recursion | O(2ⁿ) | O(n) |
| Interval DP | O(n²) | O(n²) |
| Two rows | O(n²) | **O(n)** |
| LCS with reverse | O(n²) | O(n²) |

## Edge cases & pitfalls
- For `j == i + 1` with equal chars, `dp[i+1][j−1]` is `dp[i+1][i]`, an empty interval. It must read as 0, which the zero-initialised array gives for free.
- Filling `i` upward reads rows that haven't been computed yet.

## Follow-up
**Minimum insertions (or deletions) to make s a palindrome = n − LPS(s).** Whatever isn't in the longest palindromic subsequence needs a partner.

## Related
[Longest common subsequence](longest-common-subsequence.md) · [Longest palindromic substring](../01-strings/longest-palindromic-substring.md) · [Palindrome partitioning](../04-recursion/palindrome-partitioning.md).
