# Longest common subsequence

<span class="badge hard">Hard</span> <span class="badge tag">Two-string DP</span> <span class="badge tag">Reconstruction</span>

## Problem
The length of the longest sequence that appears, in order, in both strings, and the sequence itself.
`"abcde"`, `"ace"` → 3, `"ace"`.

## Thinking process
<div class="steps" markdown>

1. **State** = two prefix lengths `(i, j)`: `a[0..i)` and `b[0..j)`.
2. **Compare the last characters**:
    - **equal**: they can both end the LCS, giving `1 + f(i−1, j−1)`;
    - **different**: at least one of them isn't in the LCS, giving `max(f(i−1, j), f(i, j−1))`.
3. The base case is an empty prefix, giving 0. That's (m+1)(n+1) states with O(1) work each, so **O(m·n)**.
4. **Reconstruction**: walk back from `dp[m][n]`. On equal characters, take the character and move diagonally. Otherwise move
   toward the larger neighbour.
5. **Space**: for the length only, keep two rows, and make the shorter string the columns.

</div>

## Table (`a = "abcde"` down the side, `b = "ace"` across)
| | ∅ | a | c | e |
|-|---|---|---|---|
| ∅ | 0 | 0 | 0 | 0 |
| a | 0 | **1** | 1 | 1 |
| b | 0 | 1 | 1 | 1 |
| c | 0 | 1 | **2** | 2 |
| d | 0 | 1 | 2 | 2 |
| e | 0 | 1 | 2 | **3** |

## Step through it
Play the trace, or step with ← →. The highlighted line is the code that just ran.

--8<-- "docs/snippets/traces/lcs.html"

## Code
```java
--8<-- "src/main/java/com/playbook/dp/LongestCommonSubsequence.java"
```

## Complexity
Recursion O(2^(m+n)) · memo/table O(m·n) time and space · two rows O(m·n) time, O(min(m,n)) space.

## LCS powers many problems
`diff` and git merges · shortest common supersequence (`m + n − LCS`) · minimum deletions to make two strings equal
(`m + n − 2·LCS`) · longest palindromic subsequence (`LCS(s, reverse(s))`).
