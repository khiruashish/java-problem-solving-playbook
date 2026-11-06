# Employee dataset round

<span class="badge medium">Medium</span> → <span class="badge hard">Hard</span> <span class="badge tag">groupingBy</span> <span class="badge tag">Comparator chains</span> <span class="badge tag">collectingAndThen</span>

This is the most common Java 8 live-coding round. You get a list of employees and a series of questions.

```java
--8<-- "src/main/java/com/playbook/java8/Employee.java"
```

| Q | Question | Key API | Sample answer |
|---|----------|---------|---------------|
| 1 | Count by gender | `groupingBy(gender, counting())` | `{F=4, M=3}` |
| 2 | Distinct departments | `map → distinct → sorted` | `[Engineering, HR, Sales]` |
| 3 | Average age by gender | `averagingInt` | `F=33.25` |
| 4 | Highest-paid employee | `max(comparingLong(salary))` | Vikram |
| 5 | Highest paid **per department** | `groupingBy + collectingAndThen(maxBy, …)` | `{Engineering=Vikram, HR=Meera, Sales=Karan}` |
| 6 | Joined after 2015 | `filter → map → sorted` | `[Neha, Priya, Ravi]` |
| 7 | Count per department | `groupingBy(dept, counting())` | |
| 8 | Average salary per department | `averagingLong` | |
| 9 | Youngest female | `filter → min` | Neha |
| 10 | Most experienced | `min(comparingInt(joinYear))` | Vikram |
| 11 | Salary statistics | `summarizingDouble` | |
| 12 | Partition by age > 30 | `partitioningBy + mapping` | |
| 13 | Nth highest salary | `distinct → sorted(reverse) → skip(n−1)` | 3rd = 1,800,000 |
| 14 | Sort by dept, salary desc, name | `comparing().thenComparing(…, reverseOrder())` | |
| 15 | id → name map | `toMap(k, v, merge, TreeMap::new)` | |
| 16 | Department with the highest payroll | `summingLong`, then `max(comparingByValue())` | Engineering |
| 17 | Names per department, comma-separated | `mapping(name, joining(", "))` | `HR=Meera, Priya` |

## Thinking process for "X per Y" questions
<div class="steps" markdown>

1. "**per Y**" means `groupingBy(Y)`.
2. "**count / sum / average / max of X**" is the **downstream collector**: `counting()`, `summingLong`, `averagingX`, `maxBy`.
3. "**only the name**" means wrap it: `collectingAndThen(maxBy(...), opt -> opt.map(Employee::name).orElseThrow())`,
   or use `mapping(Employee::name, …)`.
4. "**sorted output**" means passing `TreeMap::new` as the map factory.

</div>

## Code
```java
--8<-- "src/main/java/com/playbook/java8/EmployeeStreamProblems.java"
```
