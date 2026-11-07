package com.playbook.graphs;

/**
 * numCourses courses; prerequisites[i] = {course, prereq} means prereq must be taken first (LeetCode 207 / 210).
 * Note the edge direction: prereq → course.
 */
public final class CourseSchedule {
    private CourseSchedule() {}

    /** Can all courses be finished? ⇔ the prerequisite graph has no cycle. */
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        return findOrder(numCourses, prerequisites).length == numCourses;
    }

    /** A valid order, or an empty array if impossible. Kahn's algorithm on edges prereq → course. */
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        int[][] edges = new int[prerequisites.length][];
        for (int i = 0; i < prerequisites.length; i++) edges[i] = new int[]{prerequisites[i][1], prerequisites[i][0]};
        int[] order = TopologicalSort.kahn(numCourses, edges);
        return order.length == numCourses ? order : new int[0];
    }
}
