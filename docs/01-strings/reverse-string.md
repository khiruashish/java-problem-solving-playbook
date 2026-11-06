# Reverse a string

<span class="badge easy">Easy</span> <span class="badge tag">Two pointers</span> <span class="badge tag">Recursion</span> <span class="badge tag">Streams</span>

## Problem
Given a string `s`, return it reversed. `null` returns `null`.

## Examples
| Input | Output |
|-------|--------|
| `"hello"` | `"olleh"` |
| `"a"` | `"a"` |
| `""` | `""` |

## Clarifying questions
- Can I use `StringBuilder.reverse()`? (Usually "no, implement it yourself".)
- Unicode emoji or surrogate pairs? A naive char swap breaks them, while `StringBuilder.reverse()` handles them.
- Should I reverse in place? Java strings are immutable, so "in place" means on a `char[]`.

## Thinking process
<div class="steps" markdown>

1. Reversing means `out[i] = s[n-1-i]`.
2. **Brute force**: prepend each char to a result string. It's correct, but every `+` copies the whole string, so it's **O(n²)**.
3. **Waste**: the repeated copies. Use a mutable `char[]` instead.
4. **Pattern**: two pointers at the ends, swap, move inward. That's n/2 swaps.

</div>

## Approach 1 — Brute force (prepend)
Loop over the chars and do `result = c + result`. **O(n²) time**, O(n) space.

## Approach 2 — Optimal (two pointers on `char[]`)
<div class="steps" markdown>

1. `chars = s.toCharArray()`, `left = 0`, `right = n-1`.
2. While `left < right`: swap `chars[left]` and `chars[right]`, then `left++`, `right--`.
3. Return `new String(chars)`.

</div>
**O(n) time**, O(n) space for the copy.

## Approach 3 — Recursive
- **Base case**: length ≤ 1, so return `s`.
- **Recursive case**: `reverse(s.substring(1)) + s.charAt(0)`.

It is elegant but O(n²) (substring + concat at each level) and uses O(n) stack.

## Approach 4 — Java 8 Streams
`IntStream.range(0, n).map(i -> s.charAt(n-1-i))`, then join the chars.

## Dry run (two pointers, `"hello"`)
| Step | left | right | chars |
|------|------|-------|-------|
| 0 | 0 | 4 | h e l l o |
| 1 | 1 | 3 | **o** e l l **h** |
| 2 | 2 | 2 | o **l** l **e** h. Stop: left == right |

## Code
```java
--8<-- "src/main/java/com/playbook/strings/ReverseString.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Prepend | O(n²) | O(n) |
| Two pointers | O(n) | O(n) |
| Recursive | O(n²) | O(n) stack + copies |
| Streams | O(n) | O(n) |

## Edge cases & pitfalls
`null`, empty, a single char, an even/odd length, and surrogate pairs (`"😀"` is 2 chars).

## Follow-ups
- Reverse only the vowels. Use two pointers that skip non-vowels.
- Reverse each word. See [Reverse words](reverse-words.md).
- Reverse the first k chars of every 2k (LeetCode 541).
