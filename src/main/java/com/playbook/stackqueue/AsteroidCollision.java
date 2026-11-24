package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Asteroids move along a line: |x| is size, sign is direction (+ right, − left), all at the same speed.
 * When a right-mover meets a left-mover, the smaller explodes; equal sizes both explode. Return the survivors.
 * Only "right-mover on the left, left-mover on the right" ever collide.
 */
public final class AsteroidCollision {
    private AsteroidCollision() {}

    /** Brute force: repeatedly find the first adjacent (+, −) pair and resolve it, until none remain. O(n²). */
    public static int[] simulate(int[] asteroids) {
        List<Integer> a = new ArrayList<>();
        for (int x : asteroids) a.add(x);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i + 1 < a.size(); i++) {
                int l = a.get(i), r = a.get(i + 1);
                if (l > 0 && r < 0) {                   // a collision
                    if (l > -r) a.remove(i + 1);
                    else if (l < -r) a.remove(i);
                    else { a.remove(i + 1); a.remove(i); }
                    changed = true;
                    break;
                }
            }
        }
        return a.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Optimal: a stack of survivors so far. A new left-mover fights the right-movers on top of the stack until it dies,
     * ties, or runs out of opponents. Each asteroid is pushed and popped at most once: O(n).
     */
    public static int[] stack(int[] asteroids) {
        Deque<Integer> st = new ArrayDeque<>();
        for (int x : asteroids) {
            boolean alive = true;
            while (alive && x < 0 && !st.isEmpty() && st.peek() > 0) {
                int top = st.peek();
                if (top < -x) st.pop();                  // top explodes; x keeps flying left
                else if (top == -x) { st.pop(); alive = false; }   // both explode
                else alive = false;                       // x explodes
            }
            if (alive) st.push(x);
        }
        int[] out = new int[st.size()];
        for (int i = out.length - 1; i >= 0; i--) out[i] = st.pop();   // stack top is the rightmost survivor
        return out;
    }
}
