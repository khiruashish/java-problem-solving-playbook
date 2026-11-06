package com.playbook.java8;

/** Sample domain used by {@link EmployeeStreamProblems}. Salary in whole rupees. */
public record Employee(int id, String name, int age, String gender, String department, int joinYear, long salary) {}
