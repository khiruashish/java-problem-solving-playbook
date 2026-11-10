package com.playbook.stackqueue;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class StackQueueTest {

    @Test void arrayStack() {
        ArrayStack s = new ArrayStack(1);
        for (int i = 0; i < 100; i++) s.push(i);                 // forces several resizes
        assertEquals(100, s.size());
        assertEquals(99, s.peek());
        for (int i = 99; i >= 0; i--) assertEquals(i, s.pop());
        assertTrue(s.isEmpty());
        assertThrows(NoSuchElementException.class, s::pop);

        ArrayStack.TwoStacks t = new ArrayStack.TwoStacks(4);
        t.push1(1);
        t.push1(2);
        t.push2(9);
        t.push2(8);
        assertThrows(IllegalStateException.class, () -> t.push1(3));
        assertEquals(2, t.pop1());
        assertEquals(8, t.pop2());
        assertEquals(9, t.pop2());
        assertThrows(NoSuchElementException.class, t::pop2);
    }

    @Test void queueUsingStacks() {
        QueueUsingStacks q = new QueueUsingStacks();
        q.enqueue(1);
        q.enqueue(2);
        assertEquals(1, q.peek());
        assertEquals(1, q.dequeue());
        q.enqueue(3);                                            // must come AFTER 2
        assertEquals(2, q.dequeue());
        assertEquals(3, q.dequeue());
        assertTrue(q.isEmpty());
        assertThrows(NoSuchElementException.class, q::dequeue);

        QueueUsingStacks.StackUsingQueue s = new QueueUsingStacks.StackUsingQueue();
        s.push(1);
        s.push(2);
        s.push(3);
        assertEquals(3, s.top());
        assertEquals(3, s.pop());
        assertEquals(2, s.pop());
        s.push(4);
        assertEquals(4, s.pop());
        assertEquals(1, s.pop());
        assertTrue(s.isEmpty());
    }

    @Test void minStack() {
        Random rnd = new Random(42);
        MinStack a = new MinStack();
        MinStack.Encoded b = new MinStack.Encoded();
        Deque<Integer> ref = new ArrayDeque<>();
        for (int step = 0; step < 5000; step++) {
            if (ref.isEmpty() || rnd.nextInt(3) > 0) {
                int x = rnd.nextInt(2001) - 1000;
                if (step % 97 == 0) x = rnd.nextBoolean() ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                a.push(x);
                b.push(x);
                ref.push(x);
            } else {
                int expected = ref.pop();
                assertEquals(expected, a.pop());
                assertEquals(expected, b.pop());
            }
            if (!ref.isEmpty()) {
                int min = ref.stream().mapToInt(Integer::intValue).min().getAsInt();
                assertEquals(min, a.getMin());
                assertEquals(min, b.getMin());
                assertEquals(ref.peek(), a.top());
                assertEquals(ref.peek(), b.top());
            }
        }
        assertThrows(NoSuchElementException.class, () -> new MinStack().getMin());
    }

    @Test void nextGreater() {
        int[] a = {4, 5, 2, 25, 7, 8};
        int[] expected = {5, 25, 25, -1, 8, -1};
        assertArrayEquals(expected, NextGreaterElement.bruteForce(a));
        assertArrayEquals(expected, NextGreaterElement.monotonicStack(a));
        assertArrayEquals(expected, NextGreaterElement.leftToRight(a));
        assertArrayEquals(new int[]{2, -1, 2}, NextGreaterElement.circular(new int[]{1, 2, 1}));
        assertArrayEquals(new int[]{-1, 4, -1, 2, 2, 7}, NextGreaterElement.previousSmaller(a));
        Random rnd = new Random(7);
        for (int t = 0; t < 200; t++) {
            int[] r = rnd.ints(rnd.nextInt(20), 0, 10).toArray();
            assertArrayEquals(NextGreaterElement.bruteForce(r), NextGreaterElement.monotonicStack(r));
            assertArrayEquals(NextGreaterElement.bruteForce(r), NextGreaterElement.leftToRight(r));
        }
    }

    @Test void rpn() {
        String[][] cases = {{"2", "1", "+", "3", "*"}, {"4", "13", "5", "/", "+"},
                {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}, {"7"}, {"7", "-2", "/"}};
        int[] expected = {9, 6, 22, 7, -3};
        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], EvaluateRpn.evaluate(cases[i]));
            assertEquals(expected[i], EvaluateRpn.recursive(cases[i]));
        }
        assertThrows(IllegalArgumentException.class, () -> EvaluateRpn.evaluate(new String[]{"1", "+"}));
        assertThrows(IllegalArgumentException.class, () -> EvaluateRpn.evaluate(new String[]{"1", "2"}));
        assertThrows(IllegalArgumentException.class, () -> EvaluateRpn.recursive(new String[]{"1", "2"}));
    }

    @Test void dailyTemperatures() {
        int[] t = {73, 74, 75, 71, 69, 72, 76, 73};
        int[] expected = {1, 1, 4, 2, 1, 1, 0, 0};
        assertArrayEquals(expected, DailyTemperatures.bruteForce(t));
        assertArrayEquals(expected, DailyTemperatures.monotonicStack(t));
        assertArrayEquals(expected, DailyTemperatures.backwardJumps(t));
        Random rnd = new Random(3);
        for (int k = 0; k < 200; k++) {
            int[] r = rnd.ints(rnd.nextInt(25), 30, 40).toArray();
            assertArrayEquals(DailyTemperatures.bruteForce(r), DailyTemperatures.monotonicStack(r));
            assertArrayEquals(DailyTemperatures.bruteForce(r), DailyTemperatures.backwardJumps(r));
        }
    }

    @Test void infixToPostfix() {
        assertEquals("abc*+", InfixToPostfix.convert("a+b*c"));
        assertEquals("ab+c*", InfixToPostfix.convert("(a+b)*c"));
        assertEquals("ab-c-", InfixToPostfix.convert("a-b-c"));          // left-associative
        assertEquals("abc^^", InfixToPostfix.convert("a^b^c"));          // right-associative
        assertEquals("abcd^e-fgh*+^*+i-", InfixToPostfix.convert("a+b*(c^d-e)^(f+g*h)-i"));
        assertEquals("*-A/BC-/AKL", InfixToPostfix.toPrefix("(A-B/C)*(A/K-L)"));
        assertEquals("-+a*bcd", InfixToPostfix.toPrefix("a + b * c - d"));
        assertThrows(IllegalArgumentException.class, () -> InfixToPostfix.convert("(a+b"));
        assertThrows(IllegalArgumentException.class, () -> InfixToPostfix.convert("a+b)"));
    }

    @Test void slidingWindowMax() {
        int[] a = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] expected = {3, 3, 5, 5, 6, 7};
        assertArrayEquals(expected, SlidingWindowMaximum.bruteForce(a, 3));
        assertArrayEquals(expected, SlidingWindowMaximum.heap(a, 3));
        assertArrayEquals(expected, SlidingWindowMaximum.monotonicDeque(a, 3));
        assertArrayEquals(a, SlidingWindowMaximum.monotonicDeque(a, 1));
        assertArrayEquals(new int[]{7}, SlidingWindowMaximum.monotonicDeque(a, 8));
        Random rnd = new Random(11);
        for (int t = 0; t < 200; t++) {
            int[] r = rnd.ints(1 + rnd.nextInt(30), -5, 5).toArray();
            int k = 1 + rnd.nextInt(r.length);
            assertArrayEquals(SlidingWindowMaximum.bruteForce(r, k), SlidingWindowMaximum.monotonicDeque(r, k));
            assertArrayEquals(SlidingWindowMaximum.bruteForce(r, k), SlidingWindowMaximum.heap(r, k));
        }
    }

    @Test void largestRectangle() {
        int[][] cases = {{2, 1, 5, 6, 2, 3}, {2, 4}, {}, {5}, {1, 1, 1, 1}, {6, 2, 5, 4, 5, 1, 6}};
        int[] expected = {10, 4, 0, 5, 4, 12};
        for (int i = 0; i < cases.length; i++) {
            assertEquals(expected[i], LargestRectangleHistogram.bruteForce(cases[i]));
            assertEquals(expected[i], LargestRectangleHistogram.nearestSmaller(cases[i]));
            assertEquals(expected[i], LargestRectangleHistogram.onePass(cases[i]));
        }
        char[][] grid = {"10100".toCharArray(), "10111".toCharArray(), "11111".toCharArray(), "10010".toCharArray()};
        assertEquals(6, LargestRectangleHistogram.maximalRectangle(grid));
    }

    @Test void calculator() {
        assertEquals(23, BasicCalculator.plusMinusParens("(1+(4+5+2)-3)+(6+8)"));
        assertEquals(-4, BasicCalculator.plusMinusParens("1-(2+3)"));
        assertEquals(3, BasicCalculator.plusMinusParens(" 2-1 + 2 "));
        assertEquals(-1, BasicCalculator.plusMinusParens("-(3-2)"));
        String[] exprs = {"1+1", "6-4/2", "2*(5+5*2)/3+(6/2+8)", "(2+6*3+5-(3*14/7+2)*5)+3", "14-3/2", "7/-2"};
        int[] expected = {2, 4, 21, -12, 13, -3};
        for (int i = 0; i < exprs.length; i++) {
            assertEquals(expected[i], BasicCalculator.evaluate(exprs[i]), exprs[i]);
            if (!exprs[i].contains("/-")) assertEquals(expected[i], BasicCalculator.twoStacks(exprs[i]), exprs[i]);
        }
        assertEquals(-5, BasicCalculator.evaluate("-(2+3)"));
        assertThrows(IllegalArgumentException.class, () -> BasicCalculator.evaluate("(1+2"));
        assertThrows(IllegalArgumentException.class, () -> BasicCalculator.evaluate("1+"));
    }

    @Test void stackUsingQueueEmpty() {
        QueueUsingStacks.StackUsingQueue s = new QueueUsingStacks.StackUsingQueue();
        assertTrue(s.isEmpty());
        assertThrows(NoSuchElementException.class, s::pop);
        assertThrows(NoSuchElementException.class, s::top);
    }
}
