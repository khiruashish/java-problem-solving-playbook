# Asteroid collision

<span class="badge medium">Medium</span> <span class="badge tag">Stack simulation</span> <span class="badge tag">Amortised O(n)</span>

## Problem
Each integer is an asteroid: the absolute value is its size, and the sign is its direction (positive moves right, negative moves left).
All move at the same speed. When two meet, the smaller one explodes. If they are the same size, both explode. Return the survivors in order.

## Examples
| Input | Output | Why |
|-------|--------|-----|
| `[5, 10, -5]` | `[5, 10]` | −5 hits 10 and explodes |
| `[8, -8]` | `[]` | equal sizes, both explode |
| `[10, 2, -5]` | `[10]` | −5 destroys 2, then loses to 10 |
| `[-2, -1, 1, 2]` | unchanged | left-movers on the left never meet right-movers on the right |

## Clarifying questions
- Do two asteroids moving in the same direction ever collide? No, they have the same speed.
- What about `[-1, 1]`? They move apart, so nothing collides. Only **(+ on the left, − on the right)** collides.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: find the first adjacent (+, −) pair, resolve it, and repeat until none remain. Correct but O(n²).
2. **Who can a new asteroid hit?** A right-mover never hits anything on its left. A **left-mover** can only hit the right-movers that
   are *currently the rightmost survivors*, and it hits them **in reverse order**. "Most recent first" means a **stack**.
3. **Algorithm**: for each `x`, while `x` is moving left and the stack top is moving right, fight. If the top is smaller, pop it and keep going.
   If they are equal, pop it and `x` dies too. If the top is bigger, `x` dies. Push `x` if it survives.
4. **Why O(n)**: each asteroid is pushed once and popped at most once.

</div>

## Dry run: `[10, 2, -5]`
| x | stack before | fights | stack after |
|---|--------------|--------|-------------|
| 10 | `[]` | — | `[10]` |
| 2 | `[10]` | — | `[10, 2]` |
| −5 | `[10, 2]` | 2 < 5 → pop 2. 10 > 5 → −5 dies | `[10]` |

## Code
```java
--8<-- "src/main/java/com/playbook/stackqueue/AsteroidCollision.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Repeated adjacent resolution | O(n²) | O(n) |
| Stack | **O(n)** amortised | O(n) |

## Edge cases & pitfalls
- Pushing a negative onto a stack whose top is also negative is fine: they never meet.
- A survivor left-mover can destroy several right-movers in a row (`[1, 2, 3, -4]` → `[-4]`), so fight in a loop, not with a single `if`.
- Popping the stack gives the survivors in reverse. Fill the output array from the back.

## Related
[Valid parentheses](../01-strings/valid-parentheses.md) (the same "cancel against the top" pattern) · [Next greater element](next-greater-element.md) · [Daily temperatures](daily-temperatures.md).
