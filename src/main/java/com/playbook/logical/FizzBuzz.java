package com.playbook.logical;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** 1..n: "Fizz" for multiples of 3, "Buzz" for 5, "FizzBuzz" for both, else the number. */
public final class FizzBuzz {
    private FizzBuzz() {}

    /** Classic if/else — check 15 FIRST (the most specific rule). */
    public static List<String> classic(int n) {
        List<String> out = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) out.add("FizzBuzz");
            else if (i % 3 == 0) out.add("Fizz");
            else if (i % 5 == 0) out.add("Buzz");
            else out.add(String.valueOf(i));
        }
        return out;
    }

    /** Extensible: string concatenation — adding "Bazz for 7" is one line, no combinatorial branches. */
    public static List<String> extensible(int n) {
        List<String> out = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            String s = (i % 3 == 0 ? "Fizz" : "") + (i % 5 == 0 ? "Buzz" : "");
            out.add(s.isEmpty() ? String.valueOf(i) : s);
        }
        return out;
    }

    /** Java 8 streams. */
    public static List<String> streams(int n) {
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> i % 15 == 0 ? "FizzBuzz" : i % 3 == 0 ? "Fizz" : i % 5 == 0 ? "Buzz" : String.valueOf(i))
                .collect(Collectors.toList());
    }
}
