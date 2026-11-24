# Count and say

<span class="badge easy">Easy</span> <span class="badge tag">Run-length scan</span> <span class="badge tag">Recursion</span> <span class="badge tag">Regex</span>

## Problem
Term 1 is `"1"`. Each next term **reads the previous term aloud** as groups of *(count, digit)*.
Return the n-th term.

## Examples
| n | term | read as |
|---|------|---------|
| 1 | `1` | (base case) |
| 2 | `11` | "one 1" |
| 3 | `21` | "two 1s" |
| 4 | `1211` | "one 2, one 1" |
| 5 | `111221` | "one 1, one 2, two 1s" |
| 6 | `312211` | "three 1s, two 2s, one 1" |

## Clarifying questions
- Is n ≥ 1? (n = 0 is invalid here and throws.)
- Return type: a `String`, since terms grow quickly (about 30% longer each step).

## Thinking process
<div class="steps" markdown>

1. **Restate**: apply the same "describe" step n − 1 times to `"1"`.
2. **One step is run-length encoding** with the count written *before* the digit.
3. **Scan with two indexes**: `i` marks the start of a run, `j` walks while `s[j] == s[i]`. Append `j − i` then `s[i]`, and jump `i = j`.
4. **Recursive form** is natural: `term(n) = say(term(n − 1))`. The regex `(.)\1*` matches exactly one run.

</div>

## Dry run: `say("1211")`
| i | run | j | append | output |
|---|-----|---|--------|--------|
| 0 | `1` | 1 | `11` | `11` |
| 1 | `2` | 2 | `12` | `1112` |
| 2 | `11` | 4 | `21` | `111221` |

## Code
```java
--8<-- "src/main/java/com/playbook/strings/CountAndSay.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Iterative / recursive / regex | O(L) per step, O(total length) overall | O(L) for the current term |

L grows by a factor of about 1.30 per term (Conway's constant), so the total work is dominated by the last term.

## Edge cases & pitfalls
- `+=` on a `String` inside the loop is O(L²). Use `StringBuilder`.
- Only digits 1, 2 and 3 can ever appear. The tests check this for n = 30.
- The recursive version is n frames deep, which is fine because n is small (the string explodes first).

## Related
[String compression](string-compression.md) (the same run-length scan, count after the char) · [Character frequency](character-frequency.md).
