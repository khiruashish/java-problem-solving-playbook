package com.playbook.bits;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BitsTest {
    private final Random rnd = new Random(1515);

    @Test void basics() {
        int n = 0b1010;
        assertTrue(BitBasics.isSet(n, 1));
        assertFalse(BitBasics.isSet(n, 0));
        assertEquals(0b1011, BitBasics.set(n, 0));
        assertEquals(0b1000, BitBasics.clear(n, 1));
        assertEquals(0b0010, BitBasics.toggle(n, 3));
        assertEquals(4, BitBasics.lowestSetBit(12));
        assertEquals(8, BitBasics.clearLowestSetBit(12));
        assertEquals("00001010", BitBasics.toBinary(n, 8));
        for (int t = 0; t < 500; t++) {
            int x = rnd.nextInt(), k = rnd.nextInt(31);
            assertEquals(Integer.lowestOneBit(x), BitBasics.lowestSetBit(x));
            assertEquals(x, BitBasics.toggle(BitBasics.toggle(x, k), k));
        }
    }

    @Test void powerOfTwo() {
        for (int n = -5; n <= 5000; n++) {
            boolean expected = PowerOfTwo.loop(n);
            assertEquals(expected, PowerOfTwo.bitTrick(n), "n=" + n);
            assertEquals(expected, PowerOfTwo.bitCount(n));
            assertEquals(expected && Integer.numberOfTrailingZeros(n) % 2 == 0, PowerOfTwo.powerOfFour(n));
        }
        assertFalse(PowerOfTwo.bitTrick(Integer.MIN_VALUE));
    }

    @Test void countBits() {
        for (int t = 0; t < 1000; t++) {
            int x = rnd.nextInt();
            assertEquals(Integer.bitCount(x), CountSetBits.shiftLoop(x));
            assertEquals(Integer.bitCount(x), CountSetBits.kernighan(x));
        }
        assertEquals(32, CountSetBits.shiftLoop(-1));
        assertArrayEquals(new int[]{0, 1, 1, 2, 1, 2}, CountSetBits.countAll(5));
        assertEquals(2, CountSetBits.hammingDistance(1, 4));
    }

    @Test void singleNumber() {
        assertEquals(4, SingleNumber.xorI(new int[]{4, 1, 2, 1, 2}));
        assertEquals(99, SingleNumber.bitCountII(new int[]{0, 1, 0, 1, 0, 1, 99}));
        assertEquals(-4, SingleNumber.stateMachineII(new int[]{-2, -2, 1, 1, -4, 1, -2}));
        assertArrayEquals(new int[]{3, 5}, SingleNumber.xorIII(new int[]{1, 2, 1, 3, 2, 5}));
        for (int t = 0; t < 300; t++) {
            Set<Integer> distinct = new HashSet<>();
            while (distinct.size() < 6) distinct.add(rnd.nextInt(2001) - 1000);
            List<Integer> d = new ArrayList<>(distinct);
            int single = d.get(0);
            List<Integer> two = new ArrayList<>(List.of(single)), three = new ArrayList<>(List.of(single));
            for (int i = 1; i < 4; i++) {
                two.addAll(List.of(d.get(i), d.get(i)));
                three.addAll(List.of(d.get(i), d.get(i), d.get(i)));
            }
            java.util.Collections.shuffle(two, rnd);
            java.util.Collections.shuffle(three, rnd);
            int[] a2 = two.stream().mapToInt(Integer::intValue).toArray(), a3 = three.stream().mapToInt(Integer::intValue).toArray();
            assertEquals(single, SingleNumber.xorI(a2));
            assertEquals(single, SingleNumber.countingI(a2));
            assertEquals(single, SingleNumber.bitCountII(a3));
            assertEquals(single, SingleNumber.stateMachineII(a3));
            List<Integer> pairPlusTwo = new ArrayList<>(List.of(d.get(4), d.get(5)));
            for (int i = 0; i < 4; i++) pairPlusTwo.addAll(List.of(d.get(i), d.get(i)));
            java.util.Collections.shuffle(pairPlusTwo, rnd);
            int[] expected = {Math.min(d.get(4), d.get(5)), Math.max(d.get(4), d.get(5))};
            assertArrayEquals(expected, SingleNumber.xorIII(pairPlusTwo.stream().mapToInt(Integer::intValue).toArray()));
        }
    }

    @Test void xorTricks() {
        int[] a = {7, -3};
        XorTricks.xorSwap(a, 0, 1);
        assertArrayEquals(new int[]{-3, 7}, a);
        XorTricks.xorSwap(a, 0, 0);
        assertEquals(-3, a[0], "self-swap must not zero the value");
        assertTrue(XorTricks.isOdd(-3));
        assertFalse(XorTricks.isOdd(-4));
        assertTrue(XorTricks.oppositeSigns(-1, 1));
        assertFalse(XorTricks.oppositeSigns(-1, -7));
        int acc = 0;
        for (int n = 1; n <= 1000; n++) {
            acc ^= n;
            assertEquals(acc, XorTricks.xorUpTo(n));
        }
        for (int t = 0; t < 500; t++) {
            int x = rnd.nextInt(), y = rnd.nextInt();
            assertEquals(Integer.reverse(x), XorTricks.reverseBits(x));
            assertEquals(x + y, XorTricks.addWithoutPlus(x, y));
        }
    }

    @Test void bitmask() {
        assertEquals(8, BitmaskSubsets.subsets(new int[]{1, 2, 3}).size());
        assertEquals(List.of(0b101, 0b100, 0b001, 0), BitmaskSubsets.submasks(0b101));
        List<Integer> gray = BitmaskSubsets.grayCode(4);
        assertEquals(16, new HashSet<>(gray).size());
        for (int i = 1; i < gray.size(); i++) assertEquals(1, Integer.bitCount(gray.get(i) ^ gray.get(i - 1)));
        assertEquals(3, BitmaskSubsets.countSubsetsWithSum(new int[]{2, 3, 5, 6, 8, 10}, 10));   // {2,3,5} {2,8} {10}
        for (int mask = 0; mask < 64; mask++)
            assertEquals(1 << Integer.bitCount(mask), BitmaskSubsets.submasks(mask).size());
    }

    @Test void maxXor() {
        assertEquals(28, MaximumXor.trie(new int[]{3, 10, 5, 25, 2, 8}));
        assertEquals(28, MaximumXor.prefixSet(new int[]{3, 10, 5, 25, 2, 8}));
        assertEquals(0, MaximumXor.trie(new int[]{0}));
        for (int t = 0; t < 300; t++) {
            int[] a = rnd.ints(1 + rnd.nextInt(20), 0, Integer.MAX_VALUE).toArray();
            int expected = MaximumXor.bruteForce(a);
            assertEquals(expected, MaximumXor.trie(a));
            assertEquals(expected, MaximumXor.prefixSet(a));
        }
    }
}
