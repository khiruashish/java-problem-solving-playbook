# Transactions dataset round

<span class="badge medium">Medium</span> → <span class="badge hard">Hard</span> <span class="badge tag">summingLong</span> <span class="badge tag">YearMonth</span> <span class="badge tag">Money as long</span>

The fintech version of the Employee round. You get a list of bank transactions and a series of questions.
Money is a `long` in **paise** (minor units). Saying "never `double` for money" out loud earns points by itself.

```java
--8<-- "src/main/java/com/playbook/java8/Transaction.java"
```

Sample data: 10 transactions across 3 accounts in January and February 2026 (see `sample()` in the code).

| Q | Question | Key API | Sample answer |
|---|----------|---------|---------------|
| 1 | Net balance per account | `groupingBy(account, summingLong(signedPaise))` | `ACC-1 = ₹25850.00` |
| 2 | Debit spend per month | `groupingBy(t -> YearMonth.from(t.date()), …)` | `2026-01 = ₹16650.00` |
| 3 | Largest debit per account | `collectingAndThen(maxBy(…), o -> o.orElseThrow().id())` | `ACC-1 → id 9` |
| 4 | Top-2 merchants by spend | group + sum, then sort entries by value desc | `[Travel, Rent]` |
| 5 | Count large vs small debits | `partitioningBy(amount ≥ X, counting())` | `{true=2, false=5}` |
| 6 | Debit min / max / sum / avg in one pass | `mapToLong(…).summaryStatistics()` | count 7, max ₹20000.00 |
| 7 | Accounts with more than k debits | group + count, then filter the entries | `[ACC-1]` for k = 2 |
| 8 | First transaction id per day | `toMap(date, id, Math::min, TreeMap::new)` | 8 days |
| 9 | Distinct merchants per account | `mapping(merchant, toCollection(TreeSet::new))` + join | `Cafe, Grocer, Salary, Travel` |
| 10 | **Running balance** of an account | sorted stream → **loop** (a prefix sum) | `5000000, 4880000, …` |
| 11 | Was any account ever overdrawn? | `anyMatch(b -> b < 0)` over Q10 | none, until a ₹9000 rent hits ACC-3 |

## Thinking process
<div class="steps" markdown>

1. **Pick the grouping key**: account, `YearMonth.from(date)`, merchant. Dates group by month cleanly through `YearMonth`, with no string formatting.
2. **Pick the downstream**: `summingLong` for money, `counting` for counts, `maxBy` for "largest", `mapping` for "which merchants".
3. **Sign once, in the model**: `signedPaise()` puts the credit/debit rule in one place, so every query reuses it.
4. **Know when to stop using streams**: a running balance is a **scan** (prefix sum). Streams have no scan operator, and a
   stateful lambda (`balance[0] += …`) silently breaks under `parallel()`. Collect to a sorted list, then loop.
   Saying this is a senior signal.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/java8/TransactionStreams.java"
```

## Pitfalls
- `double` for money: `0.1 + 0.2 != 0.3`. Use `long` minor units, or `BigDecimal` with an explicit scale and rounding mode.
- Formatting with `amount / 100.0` reintroduces floating point. Use integer division and `% 100`, as `rupees()` does.
- A "running" anything inside `map()` with an external counter is a race condition under parallel streams.
- `maxBy` returns `Optional`. Inside `groupingBy` the group is never empty, so `orElseThrow()` is safe there and nowhere else.

## Related
[Employee dataset](employee-streams.md) · [Map API & advanced Collectors](map-collectors-advanced.md) · [Parallel stream pitfalls](parallel-stream-pitfalls.md).
