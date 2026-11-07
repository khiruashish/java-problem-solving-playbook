# Letter combinations of a phone number

<span class="badge medium">Medium</span> <span class="badge tag">Backtracking</span> <span class="badge tag">Cartesian product</span>

## Problem
Digits 2–9 map to phone keypad letters. Return every letter string the digits could spell.
`"23"` → `ad ae af bd be bf cd ce cf`.

## Thinking process
<div class="steps" markdown>

1. It's a **Cartesian product**: one letter per digit. The recursion depth equals the number of digits.
2. **Recursive**: at index i, loop over the letters of `digits[i]`, append, recurse on i+1, then remove the letter.
3. **Iterative**: start from `[""]`, and for each digit extend every partial string with each of its letters.
   That's BFS over the choices.
4. Empty input means an **empty list** (not `[""]`). Clarify this with the interviewer.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/recursion/LetterCombinations.java"
```

## Complexity
O(4ⁿ · n) for n digits (7 and 9 have four letters). That's the output size, so it can't be beaten.
