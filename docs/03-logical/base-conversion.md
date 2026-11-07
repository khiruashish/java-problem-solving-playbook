# Number base conversion

<span class="badge medium">Medium</span> <span class="badge tag">Division / Horner</span> <span class="badge tag">Recursion</span>

## Problem
Convert integers between bases 2–36: decimal → binary (`10` → `1010`), hex → decimal (`ff` → 255), and any base → any base.

## Thinking process
<div class="steps" markdown>

1. **To base b**: `n % b` is the **last** digit, and `n / b` drops it. Repeat until n is 0. The digits come out
   least-significant first, so **reverse** them at the end.
2. **Recursive version, no reversal**: convert `n / b` first (the higher digits), then append `n % b`.
3. **From base b, Horner's rule**: read left to right with `value = value × b + digit`. `"ff"` → 0×16+15 = 15 → 15×16+15 = 255.
4. **Any → any**: parse into a `long`, then format. Reject digits that are invalid for the base.
5. Handle **0** and **negative** numbers explicitly.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/logical/BaseConversion.java"
```

## Complexity
O(log_b n) for both directions.

## Testing note
2,000 random `long`s in random bases, compared with `Long.toString(n, radix)` and round-tripped through `fromBase`.
