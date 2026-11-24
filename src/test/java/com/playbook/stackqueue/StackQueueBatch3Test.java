package com.playbook.stackqueue;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class StackQueueBatch3Test {

    @Test void stackUsingQueues() {
        for (Supplier<StackUsingQueues.IntStack> make :
                java.util.List.<Supplier<StackUsingQueues.IntStack>>of(StackUsingQueues.OneQueue::new, StackUsingQueues.TwoQueues::new)) {
            StackUsingQueues.IntStack s = make.get();
            s.push(1);
            s.push(2);
            assertEquals(2, s.top());
            assertEquals(2, s.pop());
            assertFalse(s.isEmpty());
            assertEquals(1, s.pop());
            assertTrue(s.isEmpty());
            assertThrows(NoSuchElementException.class, s::pop);
            assertThrows(NoSuchElementException.class, s::top);
            // random operations against ArrayDeque as the reference stack
            Random rnd = new Random(41);
            Deque<Integer> ref = new ArrayDeque<>();
            StackUsingQueues.IntStack t = make.get();
            for (int op = 0; op < 3000; op++) {
                int r = rnd.nextInt(3);
                if (r == 0 || ref.isEmpty()) {
                    int x = rnd.nextInt(100);
                    ref.push(x);
                    t.push(x);
                } else if (r == 1) {
                    assertEquals(ref.pop(), t.pop());
                } else {
                    assertEquals(ref.peek(), t.top());
                }
                assertEquals(ref.isEmpty(), t.isEmpty());
            }
        }
    }

    @Test void asteroidCollision() {
        int[][][] cases = {
                {{5, 10, -5}, {5, 10}},
                {{8, -8}, {}},
                {{10, 2, -5}, {10}},
                {{-2, -1, 1, 2}, {-2, -1, 1, 2}},
                {{1, -2, -2, -2}, {-2, -2, -2}},
                {{}, {}},
        };
        for (int[][] c : cases) {
            assertArrayEquals(c[1], AsteroidCollision.stack(c[0]));
            assertArrayEquals(c[1], AsteroidCollision.simulate(c[0]));
        }
        Random rnd = new Random(42);
        for (int t = 0; t < 1000; t++) {
            int[] a = new int[rnd.nextInt(10)];
            for (int i = 0; i < a.length; i++) {
                int size = 1 + rnd.nextInt(4);
                a[i] = rnd.nextBoolean() ? size : -size;
            }
            assertArrayEquals(AsteroidCollision.simulate(a), AsteroidCollision.stack(a), java.util.Arrays.toString(a));
        }
    }

    @Test void decodeString() {
        String[][] cases = {
                {"3[a]2[bc]", "aaabcbc"}, {"3[a2[c]]", "accaccacc"}, {"2[abc]3[cd]ef", "abcabccdcdcdef"},
                {"abc3[cd]xyz", "abccdcdcdxyz"}, {"10[a]", "aaaaaaaaaa"}, {"", ""}, {"plain", "plain"},
                {"2[2[2[x]]]", "xxxxxxxx"}, {"1[a1[b]]", "ab"},
        };
        for (String[] c : cases) {
            assertEquals(c[1], DecodeString.twoStacks(c[0]), c[0]);
            assertEquals(c[1], DecodeString.recursive(c[0]), c[0]);
            assertEquals(c[1], DecodeString.innermostFirst(c[0]), c[0]);
        }
        Random rnd = new Random(43);
        for (int t = 0; t < 500; t++) {
            String s = randomEncoded(rnd, 3);
            String want = DecodeString.innermostFirst(s);
            assertEquals(want, DecodeString.twoStacks(s), s);
            assertEquals(want, DecodeString.recursive(s), s);
        }
    }

    private static String randomEncoded(Random rnd, int depth) {
        StringBuilder sb = new StringBuilder();
        int parts = rnd.nextInt(3) + 1;
        for (int p = 0; p < parts; p++) {
            if (depth > 0 && rnd.nextBoolean()) sb.append(1 + rnd.nextInt(12)).append('[').append(randomEncoded(rnd, depth - 1)).append(']');
            else sb.append((char) ('a' + rnd.nextInt(3)));
        }
        return sb.toString();
    }
}
