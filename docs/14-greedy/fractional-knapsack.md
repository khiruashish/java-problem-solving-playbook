# Fractional knapsack

<span class="badge hard">Hard</span> <span class="badge tag">Value/weight ratio</span> <span class="badge tag">Greedy vs DP</span>

## Problem
As in the [0/1 knapsack](../13-dynamic-programming/knapsack.md), but you may take **any fraction** of an item.
weights `[10,20,30]`, values `[60,100,120]`, capacity 50 → **240** (all of items 1 and 2, plus ⅔ of item 3).

## Thinking process
<div class="steps" markdown>

1. Since items are divisible, every unit of weight is interchangeable, so you should fill the bag with the
   **most valuable weight first**. That means sorting by `value / weight`, descending.
2. Take whole items while they fit, then take a fraction of the next one, and stop.
3. **Compare ratios without division**: `v₁/w₁ > v₂/w₂ ⇔ v₁·w₂ > v₂·w₁`. Use `long` to avoid overflow and floating-point ties.
4. **Why the same greedy fails for 0/1**: you can't take ⅔ of an item. With capacity 50 above, ratio-greedy takes items 1 and 2
   (160) and has 20 left with nothing that fits, but the optimum is items 2 and 3 (220).

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/greedy/FractionalKnapsack.java"
```

## Complexity
O(n log n) for the sort, plus an O(n) fill.

## The interview point
This pair, fractional (greedy works) versus 0/1 (needs DP), is the textbook demonstration of **why you must justify a greedy
choice**. Expect to be asked "why doesn't this work for the 0/1 version?".
