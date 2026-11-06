package com.playbook.logical;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LogicalTest {

    @Test void fizzBuzz() {
        List<String> expected = List.of("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz");
        assertEquals(expected, FizzBuzz.classic(15));
        assertEquals(expected, FizzBuzz.extensible(15));
        assertEquals(expected, FizzBuzz.streams(15));
        assertTrue(FizzBuzz.classic(0).isEmpty());
    }

    @Test void reverseNumber() {
        assertEquals(321, ReverseNumber.reverse(123));
        assertEquals(-321, ReverseNumber.reverse(-123));
        assertEquals(21, ReverseNumber.reverse(120));
        assertEquals(0, ReverseNumber.reverse(1534236469));   // overflows
        assertEquals(0, ReverseNumber.reverse(Integer.MIN_VALUE));
        assertEquals(-321, ReverseNumber.viaString(-123));
        assertEquals(0, ReverseNumber.viaString(1534236469));
        assertEquals(-321L, ReverseNumber.recursive(-123));
        assertTrue(ReverseNumber.isPalindrome(121));
        assertTrue(ReverseNumber.isPalindrome(1221));
        assertTrue(ReverseNumber.isPalindrome(0));
        assertFalse(ReverseNumber.isPalindrome(-121));
        assertFalse(ReverseNumber.isPalindrome(10));
    }

    @Test void armstrong() {
        for (int n : new int[]{0, 1, 153, 370, 371, 407, 9474}) {
            assertTrue(ArmstrongNumber.isArmstrong(n), "" + n);
            assertTrue(ArmstrongNumber.recursive(n), "" + n);
            assertTrue(ArmstrongNumber.streams(n), "" + n);
        }
        assertFalse(ArmstrongNumber.isArmstrong(123));
        assertFalse(ArmstrongNumber.streams(-153));
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 153, 370, 371, 407), ArmstrongNumber.upTo(999));
    }

    @Test void primes() {
        List<Integer> sieve = PrimeNumbers.sieve(100);
        assertEquals(25, sieve.size());
        for (int n = -2; n <= 100; n++) {
            boolean expected = sieve.contains(n);
            assertEquals(expected, PrimeNumbers.bruteForce(n), "" + n);
            assertEquals(expected, PrimeNumbers.isPrime(n), "" + n);
            assertEquals(expected, PrimeNumbers.streams(n), "" + n);
        }
        assertTrue(PrimeNumbers.isPrime(2_147_483_647));      // Integer.MAX_VALUE is prime
        assertTrue(PrimeNumbers.sieve(1).isEmpty());
    }

    @Test void gcdLcm() {
        int[][] cases = {{12, 18, 6}, {17, 5, 1}, {0, 7, 7}, {100, 75, 25}, {-12, 18, 6}};
        for (int[] c : cases) {
            assertEquals(c[2], GcdLcm.bruteForce(c[0], c[1]));
            assertEquals(c[2], GcdLcm.recursive(c[0], c[1]));
            assertEquals(c[2], GcdLcm.iterative(c[0], c[1]));
        }
        assertEquals(36, GcdLcm.lcm(12, 18));
        assertEquals(0, GcdLcm.lcm(0, 5));
        assertEquals(4, GcdLcm.gcdOfArray(new int[]{8, 12, 20}));
    }
}
