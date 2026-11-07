# Parallel stream pitfalls

<span class="badge expert">Expert</span> <span class="badge tag">parallelStream</span> <span class="badge tag">Thread safety</span> <span class="badge tag">reduce</span>

`parallelStream()` splits the work across the common `ForkJoinPool`. It's one keyword away, and easy to get wrong.
Each pitfall below has a **wrong** and a **right** method in the code.

## 1. Side effects into shared, non-thread-safe state
```java
List<Integer> out = new ArrayList<>();
IntStream.range(0, n).parallel().forEach(out::add);   // ❌ race: lost elements or ArrayIndexOutOfBounds
List<Integer> out = IntStream.range(0, n).parallel().boxed().toList();   // ✅ let the stream collect
```

## 2. `reduce` with a non-neutral identity
`reduce(10, Integer::sum)` adds 10 **once per chunk** in parallel, so the result depends on how the work was split.
The identity must satisfy `identity op x == x`: 0 for `+`, 1 for `×`, and the empty string for concatenation.
Add constants outside the reduce.

## 3. `reduce` with a non-associative operation
Subtraction: `(a − b) − c ≠ a − (b − c)`. Parallel chunks combine in a different grouping, which gives a different answer.
Only use associative operations (+, ×, min, max, string concatenation with a neutral identity).

## 4. Ordering
`forEach` on a parallel stream runs in **any order**. `forEachOrdered` restores encounter order, but serialises the hand-off,
so much of the parallel benefit is gone.

## When parallel streams do pay off
<div class="steps" markdown>

1. **Large n** (roughly 10⁴–10⁵ or more elements) with **CPU-heavy** per-element work.
2. A **splittable source**: `ArrayList`, arrays and `IntStream.range` split well. `LinkedList` and `Stream.iterate` split badly.
3. **Stateless, non-blocking** lambdas. I/O inside a parallel stream blocks the shared common pool for the whole JVM.
4. **Measure** with JMH. Never assume.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/java8/ParallelStreamPitfalls.java"
```

## Testing note
The "right" methods are asserted exactly. The racy method is asserted only loosely (it may lose elements or throw), because a
test can't depend on a data race. The non-neutral-identity result is asserted to be ≥ the sequential one and to differ from it
by a multiple of 10, which holds for any number of threads.
