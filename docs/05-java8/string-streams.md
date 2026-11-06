# Streams on strings

<span class="badge easy">Easy</span> → <span class="badge medium">Medium</span> <span class="badge tag">chars()</span> <span class="badge tag">groupingBy</span>

| Q | Question | Example |
|---|----------|---------|
| 1 | Character frequency (ordered) | `"swiss"` → `{s=3, w=1, i=1}` |
| 2 | First non-repeated character | `"swiss"` → `w` |
| 3 | First repeated character | `"swiss"` → `s` |
| 4 | Duplicate characters | `"swiss"` → `[s]` |
| 5 | Reverse each word | `"Java Streams"` → `"avaJ smaertS"` |
| 6 | Longest word | |
| 7 | Word frequency (case-insensitive) | `"The cat, the hat!"` → `{the=2, cat=1, hat=1}` |
| 8 | Most frequent character | |
| 9 | Palindrome check | `IntStream.range(0, n/2).allMatch(...)` |
| 10 | Sort by length, then alphabetically | `comparingInt(String::length).thenComparing(naturalOrder())` |
| 11 | Group words by length | `groupingBy(String::length)` |
| 12 | Remove duplicate characters | `"programming"` → `"progamin"` |
| 13 | Count vowels | |
| 14 | Anagram check | compare `chars().sorted().toArray()` |
| 15 | Capitalise every word | `"hello jAVA world"` → `"Hello Java World"` |

!!! tip "`chars()` returns an `IntStream`"
    Use `mapToObj(c -> (char) c)` to get `Character` values for grouping, and
    `mapToObj(c -> String.valueOf((char) c))` before `joining()`.

## Code
```java
--8<-- "src/main/java/com/playbook/java8/StringStreamProblems.java"
```

See the full step-by-step versions in [Strings](../01-strings/index.md). Every problem there also has a streams approach.
