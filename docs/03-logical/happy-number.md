# Happy number

<span class="badge easy">Easy</span> <span class="badge tag">Digits</span> <span class="badge tag">Cycle detection</span> <span class="badge tag">Floyd</span>

## Problem
Start with n and repeatedly replace it with the **sum of the squares of its digits**. n is *happy* if this
reaches 1. Otherwise it loops forever in a cycle that doesn't contain 1. Return whether n is happy.

## Examples
| n | sequence | happy? |
|---|----------|--------|
| 19 | 19 → 82 → 68 → 100 → **1** | yes |
| 2 | 2 → 4 → 16 → 37 → 58 → 89 → 145 → 42 → 20 → **4** → … | no (cycle) |
| 7 | 7 → 49 → 97 → 130 → 10 → **1** | yes |

## Clarifying questions
- n ≤ 0? Not happy (return false).
- Why must it end? For n ≥ 1000 the next value is smaller than n, so the sequence soon drops below 1000 and must repeat a value.

## Thinking process
<div class="steps" markdown>

1. **Restate**: follow the arrows n → next(n). Either we reach 1, or we revisit a number and loop.
2. **Hash set**: remember every value. Stop when we hit 1 or a repeat. Simple, with O(k) memory for a k-step sequence.
3. **Reframe**: n → next(n) is an **implicit linked list**, and "loops forever" is **cycle detection**. Floyd's tortoise
   (1 step) and hare (2 steps) must meet inside the cycle. 1 → 1 is a cycle of length 1, so: happy ⇔ they meet at 1.
4. **Math shortcut**: in base 10, every unhappy sequence passes through 4, so `while (n != 1 && n != 4)` is enough.
   Mention it, but lead with Floyd, since it doesn't rely on a memorised fact.

</div>

## Dry run (Floyd): n = 19
| step | slow | fast |
|------|------|------|
| start | 19 | 82 |
| 1 | 82 | 100 |
| 2 | 68 | **1** → stop, happy |

## Code
```java
--8<-- "src/main/java/com/playbook/logical/HappyNumber.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Hash set / recursive | O(k · log n) | O(k) |
| Floyd | O(k · log n) | **O(1)** |
| Cycle through 4 | O(k · log n) | O(1) |

k is the number of steps until the sequence repeats. It is small, because values quickly drop below 243 (= 3 × 9²).

## Edge cases & pitfalls
- In Floyd, start `fast` at `next(n)`, not at n. Otherwise `slow == fast` is true before the loop starts.
- `Integer.MAX_VALUE` is fine: the digit-square sum of any int is at most 10 × 81 = 810.

## Related
[Cycle detection in a linked list](../06-linked-list/linked-list-cycle.md) (the same Floyd algorithm on real nodes) · [Armstrong number](armstrong-number.md) (digit powers).
