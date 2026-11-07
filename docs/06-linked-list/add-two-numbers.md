# Add two numbers

<span class="badge hard">Hard</span> <span class="badge tag">Carry</span> <span class="badge tag">Dummy head</span> <span class="badge tag">Recursion</span>

## Problem
Two non-negative numbers are stored as digit lists in **reverse** order (`342` is `2→4→3`). Return their sum in the
same form. `2→4→3` + `5→6→4` → `7→0→8` (342 + 465 = 807).
**Follow-up** (LeetCode 445): the digits are in forward order.

## Clarifying questions
Can the lists have different lengths? (Yes.) Leading zeros? (No, except for the number 0 itself.) Could the numbers
exceed `long`? (Yes, which is why converting to a number is wrong.)

## Thinking process
<div class="steps" markdown>

1. **Tempting but wrong**: convert to `long`, add, and convert back. It overflows after 19 digits.
2. **Reverse order is a gift**: the head is the ones digit, exactly where grade-school addition starts.
3. At each step, `sum = carry + digitA + digitB`. Emit `sum % 10` and carry `sum / 10`.
4. Loop **while either list has digits or the carry is non-zero**. That last condition produces the extra
   leading digit (`99 + 1 = 100`).
5. **Forward-order follow-up**: reverse both lists, add, and reverse the result. A stack-based version also works.

</div>

## Dry run (342 + 465)
| a | b | carry in | sum | digit | carry out |
|---|---|----------|-----|-------|-----------|
| 2 | 5 | 0 | 7 | 7 | 0 |
| 4 | 6 | 0 | 10 | 0 | 1 |
| 3 | 4 | 1 | 8 | 8 | 0 |

## Code
```java
--8<-- "src/main/java/com/playbook/linkedlist/AddTwoNumbers.java"
```

## Complexity
O(max(m, n)) time. O(max(m, n)) for the output, plus O(max(m, n)) stack in the recursive version.
