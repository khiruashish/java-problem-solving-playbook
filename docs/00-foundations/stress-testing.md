# Stress testing against a brute force

<span class="badge easy">Guide</span> <span class="badge tag">Testing</span> <span class="badge tag">Differential testing</span>

The optimal solution is clever, and clever code is where bugs hide. The brute force is slow but
obviously correct. **Run both on hundreds of small random inputs and compare.** The first mismatch is a
tiny, reproducible failing case. This is how almost every test in this playbook is written, and it is the
single most effective habit for take-home assignments and contests.

## The recipe

<div class="steps" markdown>

1. **Write the brute force first.** It is your oracle. Keep it as simple as possible, even O(n³).
2. **Write a random generator** for *small* inputs: lengths 0–8, values in a tiny range like −3..3. Small ranges force duplicates, zeros, negatives and ties, which is where edge-case bugs live.
3. **Fix the seed.** `new Random(42)` makes every failure reproducible.
4. **Loop a few hundred times**, compare the outputs, and on a mismatch report the input.
5. **Shrink.** When it fails, the generator already gives you a short input. Debug that one by hand.

</div>

## A tiny reusable helper

The helper below runs a generator, two implementations and a comparison, and throws with the failing
input. It is tested itself, and the test shows it catching a planted off-by-one bug.

```java
--8<-- "src/main/java/com/playbook/foundations/StressTester.java"
```

## Using it

```java
// Kadane (optimal) vs. checking every subarray (brute force)
StressTester.check(500, 42,
        rnd -> StressTester.randomArray(rnd, 1, 8, -5, 5),
        MaxSubarraySum::bruteForce,
        MaxSubarraySum::kadane);
```

## What a mismatch report looks like

```
java.lang.AssertionError: mismatch on case #17
  input    = [-3, -1]
  expected = -1
  actual   = 0
```

That input says it all: the "optimal" version starts its running maximum at 0, which is wrong when every
number is negative. A hand-picked test like `[1, -2, 3]` would never have found it.

## What to compare

| Output | Comparison |
|--------|------------|
| a number or boolean | `equals` |
| a list where order doesn't matter (subsets, 3Sum triplets) | sort both, then `equals` |
| "any valid answer" (topological order, reorganized string) | write a **validator** instead of comparing to the brute force |
| floating point | `Math.abs(a − b) < 1e-9` |

## Pitfalls

- **Large random inputs.** A failure on n = 10,000 is impossible to debug. Keep n tiny and run many cases.
- **Sharing mutable input.** If one implementation sorts the array in place, the other sees sorted input. Pass a copy.
- **Testing the brute force against itself.** Make sure the two lambdas really call different methods.
