# Count vowels & consonants

<span class="badge easy">Easy</span> <span class="badge tag">Classification</span> <span class="badge tag">Streams</span>

## Problem
Count the vowels (`a e i o u`) and consonants in `s`. Ignore digits, spaces and symbols. Return `{vowels, consonants}`.

## Examples
`"Hello World 2026"` → `{3, 7}` (e, o, o | h, l, l, w, r, l, d)

## Clarifying questions
Is `y` a vowel? (No, here.) Upper case? (Count it, case-insensitively.) Non-English letters? (Ignore them.)

## Thinking process
<div class="steps" markdown>

1. Each char falls into one of three buckets: vowel, consonant, or ignored.
2. Lower-case once, check the range `a..z`, then test membership in `"aeiou"`.
3. One pass, so O(n). A 5-char lookup is effectively O(1).

</div>

## Approach 1 — Loop (optimal)
For each char `c` in `s.toLowerCase()`: skip it if it's not in `a..z`. Otherwise increment vowels if
`"aeiou".indexOf(c) >= 0`, else consonants.

## Approach 2 — Recursive
Classify `s[i]`, then recurse on `i+1`. Base case: `i == length`. Pass an index, not a substring.

## Approach 3 — Streams
Count the vowels, then count the letters. Consonants = letters − vowels.

## Code
```java
--8<-- "src/main/java/com/playbook/strings/VowelConsonantCount.java"
```

## Complexity
All approaches take O(n) time. The loop and streams use O(1) extra space; the recursive version uses O(n) stack.

## Pitfalls
`Character.isLetter` accepts `é`, `ß` and so on. Decide whether you want "English letters" or "any letter".
