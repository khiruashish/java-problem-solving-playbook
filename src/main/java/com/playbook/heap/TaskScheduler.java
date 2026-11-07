package com.playbook.heap;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * CPU tasks (letters A–Z), each takes 1 unit; the same task needs n idle units between runs.
 * Minimum total units (LeetCode 621). [A,A,A,B,B,B], n=2 → 8: A B idle A B idle A B.
 */
public final class TaskScheduler {
    private TaskScheduler() {}

    /**
     * Simulation: max-heap of remaining counts (always run the most-remaining task — it is the hardest to fit),
     * plus a cooldown queue of {count, readyTime}. O(T log 26).
     */
    public static int simulate(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char c : tasks) freq[c - 'A']++;
        PriorityQueue<Integer> ready = new PriorityQueue<>(Collections.reverseOrder());
        for (int f : freq) if (f > 0) ready.offer(f);
        Deque<int[]> cooling = new ArrayDeque<>();           // {remainingCount, timeWhenReady}
        int time = 0;
        while (!ready.isEmpty() || !cooling.isEmpty()) {
            time++;
            if (!ready.isEmpty()) {
                int left = ready.poll() - 1;
                if (left > 0) cooling.offer(new int[]{left, time + n});
            }                                                // else: CPU idles this unit
            if (!cooling.isEmpty() && cooling.peek()[1] == time) ready.offer(cooling.poll()[0]);
        }
        return time;
    }

    /**
     * Formula: the most frequent task (count maxF) forms maxF-1 full "frames" of length n+1, plus a final frame
     * holding every task that ties for maxF. If there are more tasks than slots, no idling is needed → tasks.length.
     * O(T).
     */
    public static int formula(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char c : tasks) freq[c - 'A']++;
        int maxF = 0, tiedAtMax = 0;
        for (int f : freq) {
            if (f > maxF) {
                maxF = f;
                tiedAtMax = 1;
            } else if (f == maxF) tiedAtMax++;
        }
        return Math.max(tasks.length, (maxF - 1) * (n + 1) + tiedAtMax);
    }
}
