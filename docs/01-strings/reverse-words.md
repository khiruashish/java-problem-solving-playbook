# Reverse words in a sentence

<span class="badge medium">Medium</span> <span class="badge tag">Two pointers</span> <span class="badge tag">Parsing</span>

## Problem
Reverse the **order of the words**. Trim the result and collapse multiple spaces into one.
**Variation**: reverse the characters of each word but keep the word order.

## Examples
| Input | Output |
|-------|--------|
| `"  the sky   is blue "` | `"blue is sky the"` |
| `"I love Java"` (variation) | `"I evol avaJ"` |

## Thinking process
<div class="steps" markdown>

1. **Library way**: `trim().split("\\s+")`, then reverse the list and join. That's correct and O(n), but the regex has overhead.
2. **Manual parse**: scan from the **end**, skip spaces, mark the word end, find the word start, and append. Each char is touched about twice, so O(n), and it handles any spacing.
3. **In-place char[] trick (C-style)**: reverse the whole string, then reverse each word.

</div>

## Approach — Manual scan from the end (optimal)
<div class="steps" markdown>

1. `i = n-1`.
2. Skip spaces (`i--`). Stop if `i < 0`.
3. `end = i`. Move `i` left while `s[i] != ' '`.
4. Append `s[i+1..end]`, with a separating space if the builder isn't empty.

</div>

## Recursive
`reverse("the sky is") = reverse("sky is") + " " + "the"`. The base case is a single word.

## Streams
`reduce((acc, w) -> w + " " + acc)` prepends each word.

## Code
```java
--8<-- "src/main/java/com/playbook/strings/ReverseWords.java"
```

## Complexity
O(n) time and O(n) output for all but the recursive version (O(n²) from the substring copies).

## Edge cases
All spaces → `""`. A single word. Leading and trailing spaces. Tabs (handled by `\\s+` in the library version).
