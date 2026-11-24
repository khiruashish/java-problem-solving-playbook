package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

/** LIFO stack built only from FIFO queue operations (offer, poll, peek, size, isEmpty). */
public final class StackUsingQueues {
    private StackUsingQueues() {}

    /** Common interface so tests can run every variant through the same script. */
    public interface IntStack {
        void push(int x);
        int pop();
        int top();
        boolean isEmpty();
    }

    /**
     * One queue, push-costly: after offering x, rotate the n-1 older elements behind it, so the queue front is always
     * the stack top. push O(n), pop/top O(1).
     */
    public static final class OneQueue implements IntStack {
        private final Queue<Integer> q = new ArrayDeque<>();

        @Override public void push(int x) {
            q.offer(x);
            for (int i = 1; i < q.size(); i++) q.offer(q.poll());   // move older elements behind x
        }

        @Override public int pop() {
            if (q.isEmpty()) throw new NoSuchElementException("stack is empty");
            return q.poll();
        }

        @Override public int top() {
            if (q.isEmpty()) throw new NoSuchElementException("stack is empty");
            return q.peek();
        }

        @Override public boolean isEmpty() {
            return q.isEmpty();
        }
    }

    /**
     * Two queues, pop-costly: push is a plain offer (O(1)). To pop, move all but the last element to the helper queue,
     * take the last one, then swap the queues' roles. pop O(n). The top is cached so top() is O(1).
     */
    public static final class TwoQueues implements IntStack {
        private Queue<Integer> main = new ArrayDeque<>(), helper = new ArrayDeque<>();
        private int top;

        @Override public void push(int x) {
            main.offer(x);
            top = x;
        }

        @Override public int pop() {
            if (main.isEmpty()) throw new NoSuchElementException("stack is empty");
            while (main.size() > 1) {
                top = main.poll();                 // the last one moved becomes the new top
                helper.offer(top);
            }
            int result = main.poll();
            Queue<Integer> t = main;               // swap roles instead of copying back
            main = helper;
            helper = t;
            return result;
        }

        @Override public int top() {
            if (main.isEmpty()) throw new NoSuchElementException("stack is empty");
            return top;
        }

        @Override public boolean isEmpty() {
            return main.isEmpty();
        }
    }
}
