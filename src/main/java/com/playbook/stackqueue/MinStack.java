package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/** Stack with push, pop, top and getMin — all O(1). */
public final class MinStack {
    /** Each entry remembers the minimum of the stack at the moment it was pushed. */
    private record Entry(int value, int minSoFar) {}

    private final Deque<Entry> stack = new ArrayDeque<>();

    public void push(int x) {
        int min = stack.isEmpty() ? x : Math.min(x, stack.peek().minSoFar());
        stack.push(new Entry(x, min));
    }

    public int pop() {
        if (stack.isEmpty()) throw new NoSuchElementException();
        return stack.pop().value();                          // the entry below already knows its own min
    }

    public int top() {
        if (stack.isEmpty()) throw new NoSuchElementException();
        return stack.peek().value();
    }

    public int getMin() {
        if (stack.isEmpty()) throw new NoSuchElementException();
        return stack.peek().minSoFar();
    }

    /**
     * Expert variant: O(1) EXTRA space (one long for the min). When x is a new minimum we store an encoded
     * value 2x - oldMin (always < x), which lets pop recover the previous minimum.
     */
    public static final class Encoded {
        private final Deque<Long> stack = new ArrayDeque<>();
        private long min;

        public void push(int x) {
            if (stack.isEmpty()) {
                stack.push((long) x);
                min = x;
            } else if (x < min) {
                stack.push(2L * x - min);                    // encoded flag: value < current min
                min = x;
            } else {
                stack.push((long) x);
            }
        }

        public int pop() {
            if (stack.isEmpty()) throw new NoSuchElementException();
            long y = stack.pop();
            if (y < min) {                                   // this slot held the min → restore the previous min
                int popped = (int) min;
                min = 2 * min - y;
                return popped;
            }
            return (int) y;
        }

        public int top() {
            if (stack.isEmpty()) throw new NoSuchElementException();
            long y = stack.peek();
            return (int) (y < min ? min : y);
        }

        public int getMin() {
            if (stack.isEmpty()) throw new NoSuchElementException();
            return (int) min;
        }
    }
}
