# Basic calculator

<span class="badge expert">Expert</span> <span class="badge tag">Recursive descent</span> <span class="badge tag">Two stacks</span> <span class="badge tag">Parsing</span>

## Problem
Evaluate a string expression with non-negative integers, `+ - * /`, parentheses, spaces and unary minus. **Do not**
use `eval` or a script engine. `"2*(5+5*2)/3+(6/2+8)"` = **21**, `"-(2+3)"` = **−5**.

## Clarifying questions
Integer division truncating toward zero? (Yes.) Unary minus allowed? (Yes.) Is the input always valid? (Still, report
errors cleanly.) Overflow? (Compute in `long`.)

## Thinking process
<div class="steps" markdown>

1. **Only `+`, `−` and parentheses (LeetCode 224)**: keep a running `result` and the current `sign`. At `(`, push
   `(result, sign)` and start fresh. At `)`, combine with `outerSign × inner + outerResult`. This is one pass with one stack.
2. **With `*` and `/`**: precedence matters. The cleanest senior answer is to **write the grammar**, so precedence falls
   out of the structure:
    - `expr := term (('+'|'-') term)*`
    - `term := factor (('*'|'/') factor)*`
    - `factor := number | '(' expr ')' | '-' factor`

    Each rule becomes one method. This is **recursive descent**, which is how real compilers parse.
3. **Two stacks (Dijkstra)**: one for operands and one for operators. Apply an operator whenever the one on top has
   ≥ precedence. It's equivalent to [shunting-yard](infix-to-postfix.md) and evaluating in the same pass.

</div>

## How recursive descent handles `2+3*4`
```
expr
├── term → factor 2
├── '+'
└── term
    ├── factor 3
    ├── '*'
    └── factor 4        → 3*4 is computed inside term before expr adds it: 2 + 12 = 14
```

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/BasicCalculator.java"
```

## Complexity
O(n) time for all three approaches. Space is O(n) in the worst case (the nesting depth).

## Why it's "Expert"
You have to handle precedence, associativity, unary minus, multi-digit numbers, whitespace and error reporting at the
same time, and explain your choice of design. Being able to state the grammar is the clearest sign of seniority here.

## Related
Basic calculator II (no parentheses: one stack, apply `*` and `/` immediately) · Decode string `3[a2[c]]` (stack of
(count, builder)) · Expression add operators (backtracking).
