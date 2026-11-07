# Word break

<span class="badge expert">Expert</span> <span class="badge tag">1D DP over prefixes</span> <span class="badge tag">Memoization</span>

## Problem
Can s be split into a sequence of dictionary words (each reusable)?
`"leetcode"`, `[leet, code]` → true · `"catsandog"`, `[cats, dog, sand, and, cat]` → false.

## Thinking process
<div class="steps" markdown>

1. **Recursion**: try every dictionary word that is a **prefix** of s, then solve the rest.
2. **Why it explodes**: `"aaaa…ab"` with `[a, aa, aaa, aaaa]` reaches the same suffix along exponentially many paths, and every one fails.
3. **State** = the start index of the remaining suffix, so there are only n + 1 states. **Memoize** on it.
4. **Bottom-up**: `dp[i]` = "the prefix `s[0..i)` can be segmented". `dp[i]` is true if some `j < i` has `dp[j]` and `s[j..i)` is a word.
5. **Optimisation**: only check `j ≥ i − maxWordLength`, and use a `HashSet` for O(1) word lookups.

</div>

## dp for "leetcode"
| i | 0 | 1 | 2 | 3 | **4** | 5 | 6 | 7 | **8** |
|---|---|---|---|---|-------|---|---|---|-------|
| dp | T | F | F | F | T ("leet") | F | F | F | T ("code") |

## Code
```java
--8<-- "src/main/java/com/playbook/dp/WordBreak.java"
```

## Complexity
Recursion: exponential · memo: O(n · |dict| · L) · DP: O(n · maxLen) substring checks (each substring is O(maxLen) to hash).

## Why it's "Expert"
You have to see the hidden overlap, choose the right state (the index, not the substring), and handle the adversarial input.
The tests include `"a"×40 + "b"`, which the memoized versions answer instantly.
**Word break II** (return every sentence) adds memoized lists of sentences, and its output can be exponential.
