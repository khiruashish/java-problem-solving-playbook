# Pattern printing

<span class="badge medium">Medium</span> <span class="badge tag">Nested loops</span> <span class="badge tag">Formulas</span>

## Problem
The classic star and number patterns: right triangle, pyramid, diamond, Floyd's triangle, Pascal's triangle and a hollow square.
```
pyramid(3)     diamond(3)    Floyd(4)     Pascal(5)
  *              *          1            1
 ***            ***         2 3          1 1
*****          *****        4 5 6        1 2 1
                ***         7 8 9 10     1 3 3 1
                 *                       1 4 6 4 1
```

## Thinking process (the method that solves every pattern)
<div class="steps" markdown>

1. Number the rows (i = 1..n).
2. For row i, write down **two formulas**: the number of leading spaces, and what to print.
    - Pyramid: `n − i` spaces and `2i − 1` stars.
    - Diamond: a pyramid, then the same formulas with i going back down.
3. Floyd's triangle: a single counter that keeps running across rows.
4. **Pascal**: each entry is the sum of the two above it. A single row can also be built directly with
   `C(k, j+1) = C(k, j) · (k − j) / (j + 1)`, without the earlier rows.
5. **Testable code**: return the lines as a `List<String>` instead of calling `System.out`.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/logical/PatternPrinting.java"
```

## Complexity
O(n²) characters for every pattern (that's the output size). A Pascal row on its own is O(k).
