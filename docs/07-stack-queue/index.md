# 07 · Stack & Queue

## Concept primer

| | Stack (LIFO) | Queue (FIFO) | Deque |
|-|--------------|--------------|-------|
| Order | last in, first out | first in, first out | both ends |
| Java type | `ArrayDeque` (`push`/`pop`/`peek`) | `ArrayDeque` (`offer`/`poll`/`peek`) | `ArrayDeque` (`offerFirst`/`pollLast`…) |
| Typical use | undo, recursion, matching brackets, "most recent unresolved" | BFS, scheduling, buffering | sliding window, monotonic queue |

!!! warning "Don't use `java.util.Stack`"
    It extends `Vector`, so every call is synchronised and it allows index access, which breaks the abstraction.
    `Deque<Integer> stack = new ArrayDeque<>()` is the modern choice.

## When should you think "stack"?

<div class="steps" markdown>

1. Something must be **matched with the most recent unmatched thing**: brackets, tags, operators.
2. You need the **nearest greater or smaller element** to the left or right. That's a **monotonic stack**.
3. You're converting **recursion to iteration**. The call stack becomes an explicit stack.
4. You're **evaluating or parsing expressions**: postfix, infix, calculators.

</div>

## The monotonic stack template (memorise it)

```java
Deque<Integer> st = new ArrayDeque<>();        // indexes; values kept monotonic
for (int i = 0; i < n; i++) {
    while (!st.isEmpty() && a[st.peek()] < a[i]) {   // a[i] resolves everyone smaller
        int j = st.pop();
        // a[i] is the NEXT GREATER of a[j]; st.peek() (if any) is the PREVIOUS GREATER-OR-EQUAL of a[j]
    }
    st.push(i);
}
// every index is pushed once and popped at most once → O(n) total, despite the nested while
```

## Problems (Easy → Expert)

| # | Problem | Level | Key idea |
|---|---------|-------|----------|
| 1 | [Implement a stack with an array](array-stack.md) | <span class="badge easy">Easy</span> | Top index + doubling |
| 2 | [Queue using two stacks (and stack using a queue)](queue-using-stacks.md) | <span class="badge easy">Easy</span> | Amortised transfer |
| 3 | [Min stack in O(1)](min-stack.md) | <span class="badge medium">Medium</span> | Store the min with each entry |
| 4 | [Next greater element](next-greater-element.md) | <span class="badge medium">Medium</span> | Monotonic stack |
| 5 | [Evaluate postfix (RPN)](evaluate-rpn.md) | <span class="badge medium">Medium</span> | Operand stack |
| 6 | [Daily temperatures](daily-temperatures.md) | <span class="badge medium">Medium</span> | Stack of waiting indexes |
| 7 | [Infix to postfix / prefix](infix-to-postfix.md) | <span class="badge hard">Hard</span> | Shunting-yard |
| 8 | [Sliding window maximum](sliding-window-maximum.md) | <span class="badge hard">Hard</span> | Monotonic deque |
| 9 | [Largest rectangle in histogram](largest-rectangle-histogram.md) | <span class="badge expert">Expert</span> | Previous/next smaller |
| 10 | [Basic calculator](basic-calculator.md) | <span class="badge expert">Expert</span> | Recursive descent / two stacks |

Also in this playbook: [Valid parentheses](../01-strings/valid-parentheses.md) · [Trapping rain water (monotonic stack)](../02-arrays/trapping-rain-water.md).
