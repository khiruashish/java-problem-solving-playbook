package com.playbook.bits;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BitsBatch3Test {

    @Test void reverseBits() {
        int n = 0b00000010100101000001111010011100;
        int want = 0b00111001011110000010100101000000;      // 964176192
        assertEquals(964176192, want);
        assertEquals(want, ReverseBits.loop(n));
        assertEquals(want, ReverseBits.masks(n));
        assertEquals(want, ReverseBits.byteTable(n));
        assertEquals(-1, ReverseBits.loop(-1));
        assertEquals(Integer.MIN_VALUE, ReverseBits.loop(1));
        assertEquals("10000000000000000000000000000000", ReverseBits.bin(ReverseBits.masks(1)));
        Random rnd = new Random(121);
        for (int t = 0; t < 20_000; t++) {
            int x = rnd.nextInt();
            int r = Integer.reverse(x);
            assertEquals(r, ReverseBits.loop(x));
            assertEquals(r, ReverseBits.masks(x));
            assertEquals(r, ReverseBits.byteTable(x));
            assertEquals(x, ReverseBits.masks(ReverseBits.masks(x)), "reversing twice is the identity");
        }
    }

    @Test void countingBits() {
        assertArrayEquals(new int[]{0, 1, 1, 2, 1, 2}, CountingBits.halfPlusLowBit(5));
        assertArrayEquals(new int[]{0}, CountingBits.dropLowestBit(0));
        for (int n : new int[]{0, 1, 2, 7, 8, 100, 1025, 65_537}) {
            int[] want = CountingBits.library(n);
            assertArrayEquals(want, CountingBits.kernighanEach(n));
            assertArrayEquals(want, CountingBits.halfPlusLowBit(n));
            assertArrayEquals(want, CountingBits.dropLowestBit(n));
            assertArrayEquals(want, CountingBits.highestPower(n));
        }
    }

    @Test void divideTwoIntegers() {
        int[][] cases = {{10, 3}, {7, -3}, {-7, 3}, {-7, -3}, {0, 5}, {1, 1}, {Integer.MIN_VALUE, 1},
                {Integer.MIN_VALUE, 2}, {Integer.MAX_VALUE, -1}, {Integer.MIN_VALUE, Integer.MIN_VALUE},
                {Integer.MAX_VALUE, Integer.MIN_VALUE}, {Integer.MIN_VALUE, Integer.MAX_VALUE}, {-1, Integer.MIN_VALUE}};
        for (int[] c : cases) {
            int want = c[0] / c[1];
            assertEquals(want, DivideTwoIntegers.shifts(c[0], c[1]), c[0] + "/" + c[1]);
            assertEquals(want, DivideTwoIntegers.negativesOnly(c[0], c[1]), c[0] + "/" + c[1]);
        }
        assertEquals(Integer.MAX_VALUE, DivideTwoIntegers.shifts(Integer.MIN_VALUE, -1));
        assertEquals(Integer.MAX_VALUE, DivideTwoIntegers.negativesOnly(Integer.MIN_VALUE, -1));
        assertThrows(ArithmeticException.class, () -> DivideTwoIntegers.shifts(1, 0));
        assertEquals(3, DivideTwoIntegers.repeatedSubtraction(10, 3));
        Random rnd = new Random(122);
        for (int t = 0; t < 50_000; t++) {
            int a = rnd.nextInt(), b = rnd.nextInt(4) == 0 ? rnd.nextInt(201) - 100 : rnd.nextInt();
            if (b == 0 || (a == Integer.MIN_VALUE && b == -1)) continue;
            assertEquals(a / b, DivideTwoIntegers.shifts(a, b), a + "/" + b);
            assertEquals(a / b, DivideTwoIntegers.negativesOnly(a, b), a + "/" + b);
        }
        for (int t = 0; t < 2000; t++) {
            int a = rnd.nextInt(2001) - 1000, b = rnd.nextInt(41) - 20;
            if (b == 0) continue;
            assertEquals(a / b, DivideTwoIntegers.repeatedSubtraction(a, b));
        }
    }
}
