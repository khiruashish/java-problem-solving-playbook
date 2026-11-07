# Optional deep-dive

<span class="badge medium">Medium</span> <span class="badge tag">Optional</span> <span class="badge tag">Null safety</span>

`Optional<T>` is a **return type** that says "there may be no value", and forces the caller to deal with that.

| Q | Question | Key API |
|---|----------|---------|
| 1 | Wrap a possibly-null lookup | `Optional.ofNullable(map.get(k))`. `Optional.of(null)` throws NPE |
| 2 | Chain transformations safely | `map → filter → map`, where any empty step short-circuits |
| 3 | The mapper itself returns an Optional | `flatMap`, to avoid `Optional<Optional<T>>` |
| 4 | `orElse` vs `orElseGet` | `orElse` **always evaluates** its argument, `orElseGet` is lazy |
| 5 | Fail with a meaningful error | `orElseThrow(() -> new …)` instead of `get()` |
| 6 | Keep only present values from `List<Optional<T>>` | `flatMap(Optional::stream)` (Java 9) |
| 7 | Fall back to another source lazily | `or(() -> otherOptional)` (Java 9) |
| 8 | Handle both outcomes without `isPresent()` / `get()` | `ifPresentOrElse` (Java 9) |

## The `orElse` trap, demonstrated
```java
present.orElse(expensiveCall());      // expensiveCall() RUNS even though a value is present
present.orElseGet(this::expensiveCall);  // runs only when empty
```
The tests count the calls: `orElse` → 1, `orElseGet` → 0.

!!! warning "Optional anti-patterns interviewers look for"
    - `opt.isPresent() ? opt.get() : x`: that's just `orElse(x)`.
    - `Optional` as a **field** or **method parameter**: it's not serializable, it adds overhead, and it was designed for return types.
    - `Optional<List<T>>`: return an **empty list** instead.
    - `Optional.get()` without a check is an NPE under a different name. Prefer `orElseThrow()`.

## Code
```java
--8<-- "src/main/java/com/playbook/java8/OptionalProblems.java"
```
