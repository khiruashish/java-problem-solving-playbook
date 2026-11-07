# Evaluate postfix (Reverse Polish Notation)

<span class="badge medium">Medium</span> <span class="badge tag">Stack</span> <span class="badge tag">Recursion</span>

## Problem
Evaluate a postfix expression of integers with `+ - * /` (division truncates toward zero).
`["2","1","+","3","*"]` = (2 + 1) × 3 = **9** · `["4","13","5","/","+"]` = 4 + 13/5 = **6**.

## Why postfix exists
Postfix needs **no parentheses and no precedence rules**: the order of the tokens *is* the order of evaluation. Compilers
and HP calculators use it, and [infix → postfix](infix-to-postfix.md) is how you get there.

## Thinking process
<div class="steps" markdown>

1. When you read an operator, its operands are the **two most recent results**. "Most recent" means a stack.
2. Push a number. For an operator, pop `right` **first**, then `left`, and push `left op right`. The order matters for `−` and `/`.
3. At the end, exactly one value must remain. Anything else means the expression is malformed.
4. **Recursive view**: read the expression **backwards**. An operator's right operand is the sub-expression just before it,
   and its left operand is the one before that.

</div>

## Dry run (`2 1 + 3 *`)
| Token | Stack (top on the right) |
|-------|--------------------------|
| 2 | 2 |
| 1 | 2 1 |
| + | 3 |
| 3 | 3 3 |
| * | **9** |

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/EvaluateRpn.java"
```

## Complexity
O(n) time and O(n) space, both with the explicit stack and recursively.

## Pitfalls
`"-11"` is a number, not an operator, so check `length() == 1` before treating a token as an operator.
Popping the operands in the wrong order gives `1 − 2` instead of `2 − 1`.

## Related
Prefix (Polish) evaluation: scan right to left, and pop `left` first.
