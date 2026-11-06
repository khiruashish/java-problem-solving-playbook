# 01 · Strings

## Concept primer

- `String` is **immutable**. Each "modification" creates a new object, so build results with `StringBuilder`.
- `char` is a 16-bit UTF-16 unit. `c - 'a'` maps `a..z` to `0..25`, which is the basis of the **int[26] frequency trick**.
- Most string problems reduce to five patterns: **two pointers**, **frequency counting**, **sliding window**,
  **stack**, and **pattern matching** (KMP / Rabin–Karp).

## Pattern map

| Pattern | Problems |
|---------|----------|
| Two pointers | Reverse, Palindrome, Reverse words, Longest palindromic substring |
| Frequency array / map | Character frequency, Anagram, First non-repeating, Group anagrams |
| Run-length / scanning | String compression, Longest common prefix |
| Stack | Valid parentheses |
| Recursion / backtracking | Permutations |
| Sliding window | Longest substring without repeating, Minimum window substring |
| Pattern matching | KMP, Rabin–Karp |

## Problems (Easy → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [Reverse a string](reverse-string.md) | <span class="badge easy">Easy</span> | Two pointers on `char[]` |
| 2 | [Palindrome check](palindrome.md) | <span class="badge easy">Easy</span> | Compare mirrored indexes |
| 3 | [Count vowels & consonants](vowel-consonant-count.md) | <span class="badge easy">Easy</span> | Classify each char |
| 4 | [Character frequency & duplicates](character-frequency.md) | <span class="badge easy">Easy</span> | `Map.merge` / `int[26]` |
| 5 | [Anagram check](anagram.md) | <span class="badge easy">Easy</span> | Count +1 / −1 |
| 6 | [First non-repeating character](first-non-repeating-char.md) | <span class="badge easy">Easy</span> | Two-pass count |
| 7 | [Reverse words in a sentence](reverse-words.md) | <span class="badge medium">Medium</span> | Scan from the end |
| 8 | [String compression (RLE)](string-compression.md) | <span class="badge medium">Medium</span> | Count runs |
| 9 | [Longest common prefix](longest-common-prefix.md) | <span class="badge medium">Medium</span> | Vertical scan / sort trick |
| 10 | [Valid parentheses](valid-parentheses.md) | <span class="badge medium">Medium</span> | Stack of expected closers |
| 11 | [All permutations of a string](string-permutations.md) | <span class="badge medium">Medium</span> | Backtracking |
| 12 | [Group anagrams](group-anagrams.md) | <span class="badge medium">Medium</span> | Canonical key |
| 13 | [Longest substring without repeating characters](longest-substring-without-repeating.md) | <span class="badge hard">Hard</span> | Sliding window + last index |
| 14 | [Longest palindromic substring](longest-palindromic-substring.md) | <span class="badge hard">Hard</span> | Expand around centre → Manacher |
| 15 | [Minimum window substring](minimum-window-substring.md) | <span class="badge expert">Expert</span> | Variable sliding window |
| 16 | [Pattern search: KMP & Rabin–Karp](kmp-pattern-search.md) | <span class="badge expert">Expert</span> | LPS table / rolling hash |
