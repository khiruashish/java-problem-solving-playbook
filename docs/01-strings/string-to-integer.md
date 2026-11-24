# String to integer (atoi)

<span class="badge medium">Medium</span> <span class="badge tag">Parsing</span> <span class="badge tag">Overflow guard</span> <span class="badge tag">State machine</span>

## Problem
Parse a 32-bit signed integer from a string, following these rules in order:

1. Skip leading spaces.
2. Read one optional `+` or `-`.
3. Read digits until the first non-digit (or the end).
4. No digits → `0`. If the value is outside `[−2³¹, 2³¹ − 1]`, **clamp** it.

## Examples
| Input | Output | Why |
|-------|--------|-----|
| `"42"` | 42 | plain |
| `"   -42"` | −42 | spaces, then sign |
| `"4193 with words"` | 4193 | stop at the first non-digit |
| `"words and 987"` | 0 | first non-space is not a digit or sign |
| `"-91283472332"` | −2147483648 | clamp |
| `"+-12"` | 0 | only one sign is allowed |

## Clarifying questions
- Which whitespace? Here, only `' '`. Ask whether tabs count.
- Is `"3.14"` allowed? Yes: it parses `3` and stops at `.`.
- Can I use `long`? Often the interviewer says no. That is the point of the overflow check.

## Thinking process
<div class="steps" markdown>

1. **Restate**: it is a scanner with a tiny grammar: `spaces? sign? digits*`.
2. **Brute force**: accumulate in a `long` and clamp at the end. It works, but a long digit string overflows a `long` too, so you still have to stop early.
3. **The real question** is overflow *without* a wider type. Before `result = result * 10 + d`, check
   `result > (MAX − d) / 10`. If true, the next step would overflow, so return the clamp.
4. **Pattern**: a deterministic finite automaton. States: `START → SIGNED → NUMBER → END`. Writing it as a state machine scales to harder parsers (valid number, CSV).

</div>

## Dry run: `"  -2147483649"`
| step | char | state | result | check `result > (MAX − d) / 10` |
|------|------|-------|--------|--------------------------------|
| 1–2 | spaces | START | 0 | — |
| 3 | `-` | SIGNED | 0 | — |
| 4…12 | `214748364` | NUMBER | 214748364 | false each time |
| 13 | `9` | NUMBER | — | 214748364 > (2147483647 − 9) / 10 = 214748363 → **clamp to MIN** |

## Code
```java
--8<-- "src/main/java/com/playbook/strings/StringToInteger.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| `long` buffer | O(n) | O(1) |
| int with a pre-check (optimal) | O(n) | O(1) |
| Automaton | O(n) | O(1) |

## Edge cases & pitfalls
- `"-2147483648"` must return MIN without overflowing. The check uses the positive magnitude, and `−2147483648` hits the clamp, which is the same value.
- `"+-12"`, `" + 1"`: a sign must be followed immediately by a digit.
- `Character.isDigit` accepts non-ASCII digits like `'٣'`. Compare with `'0'..'9'` explicitly.
- Leading zeros (`"0000123"`) are fine and must not trigger the overflow check.

## Related
[Reverse number](../03-logical/reverse-number.md) (the same overflow guard) · [Evaluate RPN](../07-stack-queue/evaluate-rpn.md) · [Basic calculator](../07-stack-queue/basic-calculator.md).
