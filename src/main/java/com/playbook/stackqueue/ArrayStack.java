package com.playbook.stackqueue;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Stack backed by a growable int array. push/pop/peek are O(1) (push is amortised O(1) because of doubling).
 * Also shows the classic "two stacks in one array" variant.
 */
public final class ArrayStack {
    private int[] data;
    private int top = -1;                                    // index of the top element; -1 = empty

    public ArrayStack() {
        this(8);
    }

    public ArrayStack(int initialCapacity) {
        data = new int[Math.max(1, initialCapacity)];
    }

    public void push(int x) {
        if (top + 1 == data.length) data = Arrays.copyOf(data, data.length * 2);   // double: amortised O(1)
        data[++top] = x;
    }

    public int pop() {
        if (isEmpty()) throw new NoSuchElementException("stack underflow");
        return data[top--];
    }

    public int peek() {
        if (isEmpty()) throw new NoSuchElementException("stack is empty");
        return data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    /** Two stacks sharing one fixed array: stack 1 grows from the left, stack 2 from the right. */
    public static final class TwoStacks {
        private final int[] arr;
        private int top1, top2;

        public TwoStacks(int capacity) {
            arr = new int[capacity];
            top1 = -1;
            top2 = capacity;
        }

        public void push1(int x) {
            if (top1 + 1 == top2) throw new IllegalStateException("overflow");   // the two tops met
            arr[++top1] = x;
        }

        public void push2(int x) {
            if (top1 + 1 == top2) throw new IllegalStateException("overflow");
            arr[--top2] = x;
        }

        public int pop1() {
            if (top1 < 0) throw new NoSuchElementException();
            return arr[top1--];
        }

        public int pop2() {
            if (top2 == arr.length) throw new NoSuchElementException();
            return arr[top2++];
        }
    }
}
