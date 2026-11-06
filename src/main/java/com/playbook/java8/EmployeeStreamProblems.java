package com.playbook.java8;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/** The classic "Employee dataset" Java 8 interview round (Medium → Hard). */
public final class EmployeeStreamProblems {
    private EmployeeStreamProblems() {}

    public static List<Employee> sample() {
        return List.of(
                new Employee(1, "Asha", 32, "F", "Engineering", 2015, 2_500_000),
                new Employee(2, "Ravi", 28, "M", "Engineering", 2019, 1_800_000),
                new Employee(3, "Meera", 45, "F", "HR", 2010, 1_500_000),
                new Employee(4, "Karan", 38, "M", "Sales", 2012, 1_200_000),
                new Employee(5, "Neha", 26, "F", "Sales", 2021, 900_000),
                new Employee(6, "Vikram", 50, "M", "Engineering", 2008, 3_500_000),
                new Employee(7, "Priya", 30, "F", "HR", 2018, 1_100_000));
    }

    /** Q1. Count by gender. */
    public static Map<String, Long> countByGender(List<Employee> es) {
        return es.stream().collect(Collectors.groupingBy(Employee::gender, TreeMap::new, Collectors.counting()));
    }

    /** Q2. Distinct department names (sorted). */
    public static List<String> departments(List<Employee> es) {
        return es.stream().map(Employee::department).distinct().sorted().toList();
    }

    /** Q3. Average age by gender. */
    public static Map<String, Double> averageAgeByGender(List<Employee> es) {
        return es.stream().collect(Collectors.groupingBy(Employee::gender, TreeMap::new, Collectors.averagingInt(Employee::age)));
    }

    /** Q4. Highest paid employee overall. */
    public static Optional<Employee> highestPaid(List<Employee> es) {
        return es.stream().max(Comparator.comparingLong(Employee::salary));
    }

    /** Q5. Highest paid employee NAME per department. */
    public static Map<String, String> highestPaidPerDepartment(List<Employee> es) {
        return es.stream().collect(Collectors.groupingBy(Employee::department, TreeMap::new,
                Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingLong(Employee::salary)),
                        e -> e.map(Employee::name).orElseThrow())));
    }

    /** Q6. Names of employees who joined after a year, sorted by name. */
    public static List<String> joinedAfter(List<Employee> es, int year) {
        return es.stream().filter(e -> e.joinYear() > year).map(Employee::name).sorted().toList();
    }

    /** Q7. Employee count per department. */
    public static Map<String, Long> countPerDepartment(List<Employee> es) {
        return es.stream().collect(Collectors.groupingBy(Employee::department, TreeMap::new, Collectors.counting()));
    }

    /** Q8. Average salary per department. */
    public static Map<String, Double> averageSalaryPerDepartment(List<Employee> es) {
        return es.stream().collect(Collectors.groupingBy(Employee::department, TreeMap::new, Collectors.averagingLong(Employee::salary)));
    }

    /** Q9. Youngest female employee. */
    public static Optional<Employee> youngestFemale(List<Employee> es) {
        return es.stream().filter(e -> e.gender().equals("F")).min(Comparator.comparingInt(Employee::age));
    }

    /** Q10. Most experienced (earliest join year). */
    public static Optional<Employee> mostExperienced(List<Employee> es) {
        return es.stream().min(Comparator.comparingInt(Employee::joinYear));
    }

    /** Q11. Salary statistics (count, min, max, avg, sum) in one pass. */
    public static DoubleSummaryStatistics salaryStats(List<Employee> es) {
        return es.stream().collect(Collectors.summarizingDouble(Employee::salary));
    }

    /** Q12. Partition: age > 30 vs ≤ 30 (names). */
    public static Map<Boolean, List<String>> partitionByAge(List<Employee> es, int age) {
        return es.stream().collect(Collectors.partitioningBy(e -> e.age() > age,
                Collectors.mapping(Employee::name, Collectors.toList())));
    }

    /** Q13. Nth highest salary (distinct). */
    public static Optional<Long> nthHighestSalary(List<Employee> es, int n) {
        return es.stream().map(Employee::salary).distinct().sorted(Comparator.reverseOrder()).skip(n - 1L).findFirst();
    }

    /** Q14. Sort by department, then salary descending, then name. */
    public static List<String> sortedByDeptSalaryName(List<Employee> es) {
        return es.stream().sorted(Comparator.comparing(Employee::department)
                        .thenComparing(Employee::salary, Comparator.reverseOrder())
                        .thenComparing(Employee::name))
                .map(Employee::name).toList();
    }

    /** Q15. id → name map (throws on duplicate key; provide a merge function if duplicates are possible). */
    public static Map<Integer, String> idToName(List<Employee> es) {
        return es.stream().collect(Collectors.toMap(Employee::id, Employee::name, (a, b) -> a, TreeMap::new));
    }

    /** Q16. Total salary per department, department with the highest total. */
    public static Optional<String> departmentWithHighestPayroll(List<Employee> es) {
        return es.stream().collect(Collectors.groupingBy(Employee::department, Collectors.summingLong(Employee::salary)))
                .entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey);
    }

    /** Q17. Comma-separated names per department. */
    public static Map<String, String> namesPerDepartment(List<Employee> es) {
        return es.stream().collect(Collectors.groupingBy(Employee::department, TreeMap::new,
                Collectors.mapping(Employee::name, Collectors.joining(", "))));
    }
}
