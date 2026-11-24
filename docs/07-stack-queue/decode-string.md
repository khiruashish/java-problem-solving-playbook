# Decode string

<span class="badge hard">Hard</span> <span class="badge tag">Two stacks</span> <span class="badge tag">Recursive descent</span> <span class="badge tag">Nesting</span>

## Problem
Decode a string where `k[encoded]` means *repeat `encoded` exactly k times*. Brackets nest.

## Examples
| Input | Output |
|-------|--------|
| `3[a]2[bc]` | `aaabcbc` |
| `3[a2[c]]` | `accaccacc` |
| `2[abc]3[cd]ef` | `abcabccdcdcdef` |
| `10[a]` | `aaaaaaaaaa` (multi-digit counts) |

## Clarifying questions
- Is the input always valid? Yes: balanced brackets, and digits only appear as counts.
- Can letters appear outside brackets? Yes (`abc3[cd]xyz`).
- Can k be 0, or have several digits? k ≥ 1, and it can have several digits.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: find an **innermost** `k[...]` (no `[` inside it), replace it with its expansion, and repeat. Easy to reason about,
   but every replacement rebuilds the whole string.
2. **Nesting means a stack.** At `[` we start a new inner string, and we must *remember* two things to resume the outer one later:
   the count `k`, and the outer partial string built so far. Push both.
3. **At `]`**: pop the count and the outer string, then `outer.append(inner.repeat(k))`. That result becomes the current string again.
4. **Recursive descent** is the same idea with the call stack: `parse()` reads letters and `k[ … ]` groups until it reaches `]`, recursing for the inside.
   It mirrors the grammar `seq := (letter | number '[' seq ']')*`.

</div>

## Dry run (two stacks): `3[a2[c]]`
| char | counts | outer strings | cur |
|------|--------|---------------|-----|
| 3 | | | k = 3 |
| `[` | `3` | `""` | `""` |
| a | `3` | `""` | `a` |
| 2 | | | k = 2 |
| `[` | `3 2` | `"" a` | `""` |
| c | `3 2` | `"" a` | `c` |
| `]` | `3` | `""` | `a` + `cc` = `acc` |
| `]` | | | `""` + `acc`×3 = **`accaccacc`** |

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/DecodeString.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Innermost-first rewriting | O(depth · output) | O(output) |
| Two stacks | O(output) | O(output + depth) |
| Recursive descent | O(output) | O(output + depth) recursion |

The output can be exponentially longer than the input (`2[2[2[…]]]`), so "linear in the output" is the best possible.

## Edge cases & pitfalls
- Multi-digit counts: accumulate `k = k * 10 + digit`. Don't read one char.
- Reset `k = 0` after pushing it at `[`.
- `String.repeat` (Java 11+) beats a manual loop and avoids O(n²) concatenation.

## Related
[Valid parentheses](../01-strings/valid-parentheses.md) · [Basic calculator](basic-calculator.md) (the same push-the-context-at-`(` idea) · [Evaluate RPN](evaluate-rpn.md).
