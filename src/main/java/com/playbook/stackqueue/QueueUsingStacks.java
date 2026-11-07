package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

/**
 * FIFO queue built from two LIFO stacks. Every element is moved from {@code in} to {@code out} at most once,
 * so enqueue/dequeue are amortised O(1). Also: a stack built from one queue.
 */
public final class QueueUsingStacks {
    private final Deque<Integer> in = new ArrayDeque<>();    // receives new elements
    private final Deque<Integer> out = new ArrayDeque<>();   // serves the oldest element on top

    public void enqueue(int x) {
        in.push(x);
    }

    public int dequeue() {
        shiftIfNeeded();
        if (out.isEmpty()) throw new NoSuchElementException("queue is empty");
        return out.pop();
    }

    public int peek() {
        shiftIfNeeded();
        if (out.isEmpty()) throw new NoSuchElementException("queue is empty");
        return out.peek();
    }

    public boolean isEmpty() {
        return in.isEmpty() && out.isEmpty();
    }

    /** Only refill out when it is EMPTY — refilling earlier would bury older elements under newer ones. */
    private void shiftIfNeeded() {
        if (out.isEmpty()) while (!in.isEmpty()) out.push(in.pop());
    }

    /** Reverse direction: LIFO stack from ONE FIFO queue — rotate the queue after each push. push O(n), pop O(1). */
    public static final class StackUsingQueue {
        private final Deque<Integer> q = new ArrayDeque<>();

        public void push(int x) {
            q.offer(x);
            for (int i = 1; i < q.size(); i++) q.offer(q.poll());   // move older elements behind x
        }

        public int pop() {
            if (q.isEmpty()) throw new NoSuchElementException();
            return q.poll();
        }

        public int top() {
            if (q.isEmpty()) throw new NoSuchElementException();
            return q.peek();
        }

        public boolean isEmpty() {
            return q.isEmpty();
        }
    }
}
