# Palindrome check

<span class="badge easy">Easy</span> <span class="badge tag">Two pointers</span> <span class="badge tag">Recursion</span>

## Problem
Return `true` if `s` reads the same forwards and backwards. **Variation**: ignore case and every
non-alphanumeric char (LeetCode 125).

## Examples
| Input | Strict | Ignore non-alnum |
|-------|--------|------------------|
| `"racecar"` | true | true |
| `"Aba"` | false | true |
| `"A man, a plan, a canal: Panama"` | false | true |
| `""` | true | true |

## Clarifying questions
Case-sensitive? Ignore spaces and punctuation? What should `null` return? Here it returns `false`.

## Thinking process
<div class="steps" markdown>

1. A palindrome means `s[i] == s[n-1-i]` for every `i < n/2`.
2. **Brute force**: reverse and compare. That uses O(n) extra memory.
3. **Waste**: we build a whole copy even when the first char already mismatches.
4. **Pattern**: two pointers moving inward, with an early exit on the first mismatch. O(1) space.

</div>

## Approach 1 — Reverse and compare
`new StringBuilder(s).reverse().toString().equals(s)`. O(n) time, O(n) space.

## Approach 2 — Two pointers (optimal)
<div class="steps" markdown>

1. `left = 0`, `right = n-1`.
2. While `left < right`: if `s[left] != s[right]`, return false. Otherwise move both inward.
3. Return true.

</div>

## Approach 3 — Recursive
`isPal(l, r) = l >= r || (s[l] == s[r] && isPal(l+1, r-1))`. Pass indexes instead of substrings to avoid copies.

## Approach 4 — Streams
`IntStream.range(0, n/2).allMatch(i -> s.charAt(i) == s.charAt(n-1-i))`.

## Variation — Ignore non-alphanumerics
Use the same two pointers, but skip junk with inner `while` loops and compare with `Character.toLowerCase`.

## Dry run (`"abcba"`)
| left | right | s[left] | s[right] | Match? |
|------|-------|---------|----------|--------|
| 0 | 4 | a | a | ✅ |
| 1 | 3 | b | b | ✅ |
| 2 | 2 | stop | | true |

## Code
```java
--8<-- "src/main/java/com/playbook/strings/Palindrome.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Reverse & compare | O(n) | O(n) |
| Two pointers | O(n) | O(1) |
| Recursive | O(n) | O(n) stack |

## Follow-ups
- **Valid palindrome II**: can you delete at most one char? On the first mismatch, try skipping either side.
- Palindrome number. See [Reverse number](../03-logical/reverse-number.md).
- Longest palindromic substring. See [problem 14](longest-palindromic-substring.md).
