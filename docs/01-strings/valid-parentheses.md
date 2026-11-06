# Valid parentheses

<span class="badge medium">Medium</span> <span class="badge tag">Stack</span>

## Problem
Given a string of `()[]{}`, return true if every opener is closed by the same type in the correct order.

## Examples
`"()[]{}"` ✅ · `"{[()]}"` ✅ · `"(]"` ❌ · `"([)]"` ❌ · `"(("` ❌

## Thinking process
<div class="steps" markdown>

1. The **most recent unmatched opener** must be closed first, which is **LIFO**. So use a stack.
2. **Trick**: push the **expected closer** instead of the opener. Then the check is just `pop() == c`.
3. A mismatch, or a pop from an empty stack, means invalid. Leftovers at the end also mean invalid.

</div>

## Approach 1 — Brute force
Repeatedly remove `"()"`, `"[]"` and `"{}"` until nothing changes. Valid if the string ends up empty. That's O(n²).

## Approach 2 — Stack (optimal)
| char | Action | Stack (top on the right) |
|------|--------|--------------------------|
| `{` | push `}` | `}` |
| `[` | push `]` | `} ]` |
| `(` | push `)` | `} ] )` |
| `)` | pop `)` ✅ | `} ]` |
| `]` | pop `]` ✅ | `}` |
| `}` | pop `}` ✅ | empty → valid |

## Approach 3 — Single bracket type
With only `(` and `)`, a counter is enough: it must never go negative and must end at 0. That's O(1) space.

## Code
```java
--8<-- "src/main/java/com/playbook/strings/ValidParentheses.java"
```

## Complexity
Stack: O(n) time and O(n) space. Counter: O(n) time and O(1) space.

## Follow-ups
- Minimum additions to make the string valid. Count the unmatched openers and closers.
- Longest valid parentheses (Hard). Use a stack of indexes, or DP.
- Generate all valid combinations of n pairs (backtracking).
