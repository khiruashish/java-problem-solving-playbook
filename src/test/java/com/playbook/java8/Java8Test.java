package com.playbook.java8;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Java8Test {
    private final List<Integer> nums = List.of(10, 15, 8, 49, 25, 98, 98, 32, 15, 1);

    @Test void basics() {
        assertEquals(List.of(10, 8, 98, 98, 32), StreamBasics.evens(nums));
        assertEquals(List.of(10, 15, 15, 1), StreamBasics.startingWithOne(nums));
        assertEquals(Set.of(98, 15), StreamBasics.duplicates(nums));
        assertEquals(List.of(98, 15), List.copyOf(StreamBasics.duplicates(nums)));
        assertEquals(Optional.of(10), StreamBasics.first(nums));
        assertEquals(Optional.of(98), StreamBasics.max(nums));
        assertEquals(Optional.of(49), StreamBasics.secondHighest(nums));
        assertEquals(351, StreamBasics.sum(nums));
        assertEquals(35.1, StreamBasics.average(nums), 1e-9);
        assertEquals((225 + 2401 + 625 + 9604 + 9604 + 1024 + 225) / 7.0, StreamBasics.averageOfSquaresAbove100(nums), 1e-9);
        assertEquals(2L, StreamBasics.frequency(nums).get(98));
        assertEquals(5, StreamBasics.partitionEvenOdd(nums).get(true).size());
        assertEquals(List.of(1, 2, 3, 4), StreamBasics.flatten(List.of(List.of(1, 2), List.of(), List.of(3, 4))));
        assertArrayEquals(new int[]{1, 2, 3, 5}, StreamBasics.mergeSortedDistinct(new int[]{5, 1, 3}, new int[]{3, 2}));
        assertTrue(StreamBasics.allPositive(nums));
        assertEquals(List.of(98, 98, 49), StreamBasics.top3(nums));
        assertEquals(List.of(98, 98, 49, 32, 25, 15, 15, 10, 8, 1), StreamBasics.sortDescending(nums));
        assertEquals(6, StreamBasics.sumOfDigits(-123));
        assertEquals("[1, 2]", StreamBasics.join(List.of(1, 2)));
        assertEquals(Optional.empty(), StreamBasics.max(List.of()));
    }

    @Test void strings() {
        assertEquals(Optional.of('w'), StringStreamProblems.firstNonRepeated("swiss"));
        assertEquals(Optional.of('v'), StringStreamProblems.firstNonRepeated("level"));
        assertEquals(Optional.of('s'), StringStreamProblems.firstRepeated("swiss"));
        assertEquals(List.of('s'), StringStreamProblems.duplicateChars("swiss"));
        assertEquals("avaJ smaertS", StringStreamProblems.reverseEachWord("Java Streams"));
        assertEquals(Optional.of("collectors"), StringStreamProblems.longestWord("java streams collectors are fun"));
        assertEquals(Map.of("the", 2L, "cat", 1L, "hat", 1L), StringStreamProblems.wordFrequency("The cat, the hat!"));
        assertEquals(Optional.of('s'), StringStreamProblems.mostFrequentChar("swiss"));
        assertTrue(StringStreamProblems.isPalindrome("madam"));
        assertEquals(List.of("a", "bb", "cc", "aaa"), StringStreamProblems.sortByLengthThenAlpha(List.of("cc", "aaa", "bb", "a")));
        assertEquals(List.of("bb", "cc"), StringStreamProblems.groupByLength(List.of("cc", "aaa", "bb", "a")).get(2).stream().sorted().toList());
        assertEquals("progamin", StringStreamProblems.removeDuplicateChars("programming"));
        assertEquals(3, StringStreamProblems.countVowels("Programming in Java".substring(0, 11)));
        assertTrue(StringStreamProblems.areAnagrams("listen", "silent"));
        assertEquals("Hello Java World", StringStreamProblems.capitalize("hello jAVA world"));
    }

    @Test void employees() {
        List<Employee> es = EmployeeStreamProblems.sample();
        assertEquals(Map.of("F", 4L, "M", 3L), EmployeeStreamProblems.countByGender(es));
        assertEquals(List.of("Engineering", "HR", "Sales"), EmployeeStreamProblems.departments(es));
        assertEquals(33.25, EmployeeStreamProblems.averageAgeByGender(es).get("F"), 1e-9);
        assertEquals("Vikram", EmployeeStreamProblems.highestPaid(es).orElseThrow().name());
        assertEquals(Map.of("Engineering", "Vikram", "HR", "Meera", "Sales", "Karan"), EmployeeStreamProblems.highestPaidPerDepartment(es));
        assertEquals(List.of("Neha", "Priya", "Ravi"), EmployeeStreamProblems.joinedAfter(es, 2015));
        assertEquals(3L, EmployeeStreamProblems.countPerDepartment(es).get("Engineering"));
        assertEquals(1_300_000.0, EmployeeStreamProblems.averageSalaryPerDepartment(es).get("HR"), 1e-9);
        assertEquals("Neha", EmployeeStreamProblems.youngestFemale(es).orElseThrow().name());
        assertEquals("Vikram", EmployeeStreamProblems.mostExperienced(es).orElseThrow().name());
        assertEquals(7, EmployeeStreamProblems.salaryStats(es).getCount());
        assertEquals(List.of("Ravi", "Neha", "Priya"), EmployeeStreamProblems.partitionByAge(es, 30).get(false));
        assertEquals(Optional.of(1_800_000L), EmployeeStreamProblems.nthHighestSalary(es, 3));
        assertEquals(List.of("Vikram", "Asha", "Ravi", "Meera", "Priya", "Karan", "Neha"), EmployeeStreamProblems.sortedByDeptSalaryName(es));
        assertEquals("Asha", EmployeeStreamProblems.idToName(es).get(1));
        assertEquals(Optional.of("Engineering"), EmployeeStreamProblems.departmentWithHighestPayroll(es));
        assertEquals("Meera, Priya", EmployeeStreamProblems.namesPerDepartment(es).get("HR"));
    }
}
