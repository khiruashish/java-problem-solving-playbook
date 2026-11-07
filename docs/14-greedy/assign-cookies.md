# Assign cookies

<span class="badge easy">Easy</span> <span class="badge tag">Sort + two pointers</span> <span class="badge tag">Exchange argument</span>

## Problem
Child i is content with a cookie of size ≥ `greed[i]`. Each child gets at most one cookie. Maximise the number of content children.
greed `[1,2,3]`, cookies `[1,1]` → 1 · greed `[1,2]`, cookies `[1,2,3]` → 2.

## Thinking process
<div class="steps" markdown>

1. **Brute force**: try every assignment. Exponential.
2. **Intuition**: don't waste a big cookie on an easy child, and don't skip an easy child that a small cookie can satisfy.
3. **Greedy**: sort both lists. Walk the cookies from smallest to largest. If the current cookie satisfies the
   **least greedy unsatisfied child**, give it to them. Otherwise the cookie is too small for everyone left, so discard it.
4. **Exchange argument**: if an optimal solution gives the least greedy child a bigger cookie, swapping it for the smallest
   cookie that works never makes things worse.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/greedy/AssignCookies.java"
```

## Complexity
O(n log n + m log m) for the sorting, plus an O(n + m) scan. Brute force is exponential.

## Same shape
Boats to save people (sort, then pair the heaviest with the lightest) · Maximum matching of workers to jobs by ability.
