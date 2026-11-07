# Leap year

<span class="badge easy">Easy</span> <span class="badge tag">Conditionals</span>

## Problem
Is a year a Gregorian leap year? 2024 ✅ · 1900 ❌ · 2000 ✅.

## Thinking process
<div class="steps" markdown>

1. The rule has **three layers**: divisible by 4 → leap; **except** centuries (÷100) → not leap; **except** ÷400 → leap.
2. **Check the most specific rule first** (400, then 100, then 4), the same lesson as [FizzBuzz](fizzbuzz.md).
3. One expression: `(y % 4 == 0 && y % 100 != 0) || y % 400 == 0`.
4. In production, use `java.time.Year.isLeap(y)`. Interviewers want to see that you know it exists **and** can derive the rule.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/logical/LeapYear.java"
```

## Complexity
O(1).

## Testing note
Years 1–4000 are checked against `java.time`.
