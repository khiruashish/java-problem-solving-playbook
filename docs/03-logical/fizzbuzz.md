# FizzBuzz

<span class="badge easy">Easy</span> <span class="badge tag">Conditionals</span> <span class="badge tag">Streams</span>

## Problem
For 1..n: print "Fizz" for multiples of 3, "Buzz" for multiples of 5, "FizzBuzz" for multiples of both, and the number otherwise.

## Thinking process
<div class="steps" markdown>

1. The rules overlap, so check the **most specific** one (15) first or it will never fire.
2. **Senior touch**: build the word by concatenation (`Fizz` + `Buzz`). Adding a new rule ("Bazz" for 7) is then one
   line instead of 2ᵏ branches. That shows you think about the **Open/Closed principle**.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/logical/FizzBuzz.java"
```

## Complexity
O(n) time and O(n) output.
