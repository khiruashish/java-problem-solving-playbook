# Palindrome partitioning

<span class="badge hard">Hard</span> <span class="badge tag">Backtracking</span> <span class="badge tag">Precomputed table</span> <span class="badge tag">DP follow-up</span>

## Problem
Return **every** way to cut a string into pieces that are all palindromes.
Follow-up: return the **minimum number of cuts** needed.

## Examples
| s | partitions | min cuts |
|---|------------|----------|
| `aab` | `[a, a, b]`, `[aa, b]` | 1 |
| `a` | `[a]` | 0 |
| `racecar` | 4 ways, including `[racecar]` and `[r, aceca, r]` | 0 |
| `abcd` | `[a, b, c, d]` | 3 |

## Clarifying questions
- Empty string? One partition with zero pieces.
- Do you want the list, or just the count / minimum? The list can be exponential (`"aaaa…"` has 2ⁿ⁻¹ partitions).

## Thinking process
<div class="steps" markdown>

1. **Brute force**: each of the n − 1 gaps is either cut or not. Try all 2ⁿ⁻¹ masks and keep those where every piece is a palindrome.
2. **Backtracking**: choose the **first piece** `s[start..end]`. Only if it is a palindrome, recurse on the rest. A non-palindromic prefix kills that whole subtree.
3. **Waste**: the same `isPalindrome(i, j)` is re-checked in many branches. Precompute `isPal[i][j]` in O(n²) from short to long:
   `s[i] == s[j] && (j − i < 2 || isPal[i+1][j−1])`. Each check becomes O(1).
4. **Follow-up (min cuts)**: you need only the best partition, not all of them, so this is DP.
   `cuts[j] = 0` if `s[0..j]` is a palindrome, otherwise `min(cuts[i−1] + 1)` over every palindromic `s[i..j]`.

</div>

## Recursion tree: `aab`
```
                 "aab"
       ┌───────────┼────────────┐
      "a"         "aa"         "aab" ✗ (not a palindrome)
       │            │
   ┌───┴───┐       "b" → [aa, b]
  "a"    "ab" ✗
   │
  "b" → [a, a, b]
```

## Min-cuts table: `aab`
| j | prefix | palindromic pieces ending at j | cuts[j] |
|---|--------|--------------------------------|---------|
| 0 | a | `a` (whole prefix) | 0 |
| 1 | aa | `aa` (whole prefix) | 0 |
| 2 | aab | `b` → cuts[1] + 1 | **1** |

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/PalindromePartitioning.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Brute force (cut masks) | O(2ⁿ · n) | O(n) |
| Backtracking | O(2ⁿ · n) worst case (`aaaa…`) | O(n) recursion |
| Backtracking + table | O(2ⁿ · n) output-bound, O(1) checks | O(n²) |
| Min cuts DP | **O(n²)** | O(n²) |

## Edge cases & pitfalls
- Building the table in the wrong order: `isPal[i+1][j−1]` must already be known, so fill by increasing length.
- `substring` creates a new String for each piece. That is fine, since the output needs them anyway.
- Don't use backtracking for the min-cuts follow-up. It is exponential, and the DP is O(n²).

## Related
[Subsets](subsets.md) · [Longest palindromic substring](../01-strings/longest-palindromic-substring.md) · [Word break](../13-dynamic-programming/word-break.md) (the same "first piece + rest" structure).
