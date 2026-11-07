# Infix to postfix (shunting-yard)

<span class="badge hard">Hard</span> <span class="badge tag">Stack</span> <span class="badge tag">Parsing</span>

## Problem
Convert an infix expression (single-character operands, `+ - * / ^`, parentheses) to postfix.
`a+b*(c^d-e)^(f+g*h)-i` → `abcd^e-fgh*+^*+i-`. **Bonus**: convert to prefix.

## Rules you must state
| Operator | Precedence | Associativity |
|----------|------------|---------------|
| `^` | 3 | **right** (`a^b^c = a^(b^c)`) |
| `* /` | 2 | left |
| `+ -` | 1 | left |

## Thinking process (Dijkstra's shunting-yard)
<div class="steps" markdown>

1. **Operands** go straight to the output, because their order never changes between infix and postfix.
2. **Operators wait** on a stack until we know nothing with higher priority still needs to be applied first.
3. When a new operator `c` arrives, pop to the output every operator on the stack that must run **before** it:
   higher precedence, or **equal precedence and `c` is left-associative**. Then push `c`.
4. `(` is pushed as a wall. `)` pops to the output until the matching `(`, then discards both.
5. At the end, pop everything that's left. A stray `(` means the parentheses were unbalanced.

</div>

## Dry run (`a+b*c-d`)
| Token | Output | Operator stack |
|-------|--------|----------------|
| a | a | |
| + | a | + |
| b | ab | + |
| * | ab | + * (higher, so it waits on top) |
| c | abc | + * |
| - | abc*+ | − (pops `*` and `+`: higher and equal-left) |
| d | abc*+d | − |
| end | **abc*+d-** | |

## Infix → prefix
Reverse the string, swap `(` and `)`, run the same algorithm **with associativity flipped**, then reverse the output.
`(A-B/C)*(A/K-L)` → `*-A/BC-/AKL`.

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/InfixToPostfix.java"
```

## Complexity
O(n) time and O(n) space. Every operator is pushed and popped once.

## Next step
Feed the postfix output into [Evaluate RPN](evaluate-rpn.md) to get a full calculator, or evaluate directly with a
[Basic calculator](basic-calculator.md).
