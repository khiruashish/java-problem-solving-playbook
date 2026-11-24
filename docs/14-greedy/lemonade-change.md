# Lemonade change

<span class="badge easy">Easy</span> <span class="badge tag">Greedy choice</span> <span class="badge tag">Exchange argument</span>

## Problem
Lemonade costs **5**. Customers arrive in order and pay with a 5, 10 or 20 bill. You start with **no change**.
Return whether you can give every customer the correct change.

## Examples
| bills | result | why |
|-------|--------|-----|
| `[5, 5, 5, 10, 20]` | true | the 20 gets 10 + 5 |
| `[5, 5, 10, 10, 20]` | false | after two 10s only one 5 is left, so the 20 can't be changed |
| `[10]` | false | no change at all |

## Clarifying questions
- Only 5/10/20 bills? Yes. Anything else throws.
- Is the order fixed? Yes, and that is what makes it a greedy problem rather than a sorting one.

## Thinking process
<div class="steps" markdown>

1. **Change for 10**: you must give one 5. There is no choice.
2. **Change for 20**: you need 15, either **10 + 5** or **5 + 5 + 5**. This is the only decision in the problem.
3. **Greedy choice: prefer 10 + 5.** A 5 can make change for *both* 10s and 20s, while a 10 is only useful for 20s.
   Keeping more fives is never worse (an **exchange argument**: any plan that uses 5 + 5 + 5 can swap in the 10 and still work).
4. **Proof by test**: the tests run an exhaustive DFS that tries *both* options for every 20, on 3,000 random sequences, and confirm greedy never loses.

</div>

## Dry run: `[5, 5, 5, 10, 20]`
| bill | fives | tens | action |
|------|-------|------|--------|
| 5 | 1 | 0 | keep |
| 5 | 2 | 0 | keep |
| 5 | 3 | 0 | keep |
| 10 | 2 | 1 | give 5 |
| 20 | 1 | 0 | give 10 + 5 ✓ |

## Code
```java
--8<-- "src/main/java/com/playbook/greedy/LemonadeChange.java"
```

## Complexity
| Approach | Time | Space |
|----------|------|-------|
| Exhaustive DFS (oracle) | O(2ⁿ) | O(n) |
| Greedy counters | **O(n)** | **O(1)** |

## Edge cases & pitfalls
- 20s are never given as change, so don't count them.
- Paying a 20 with 5 + 5 + 5 whenever possible fails on `[5, 5, 5, 5, 10, 20, 10]`: no five is left for the last 10. Greedy (10 + 5) succeeds.

## Related
[Assign cookies](assign-cookies.md) · [Gas station](gas-station.md) · [Coin change](../13-dynamic-programming/coin-change.md) (with arbitrary denominations greedy fails and you need DP).
