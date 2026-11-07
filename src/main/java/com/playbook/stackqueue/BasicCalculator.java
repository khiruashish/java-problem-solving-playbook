package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Evaluate an infix string with non-negative integers, + - * /, parentheses, unary minus and spaces.
 * "2*(5+5*2)/3+(6/2+8)" = 21. Division truncates toward zero. Arithmetic is done in long, result cast to int.
 */
public final class BasicCalculator {
    private BasicCalculator() {}

    /**
     * Calculator I (only + - and parentheses, LeetCode 224): one running result, a sign, and a stack that saves
     * (result, sign) when entering a parenthesis. O(n).
     */
    public static int plusMinusParens(String s) {
        Deque<Long> stack = new ArrayDeque<>();
        long result = 0, number = 0;
        int sign = 1;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                number = number * 10 + (c - '0');
            } else if (c == '+' || c == '-') {
                result += sign * number;
                number = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {
                stack.push(result);                          // save the outer state
                stack.push((long) sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                result += sign * number;
                number = 0;
                result = stack.pop() * result + stack.pop(); // outerSign * inner + outerResult
            }
        }
        return (int) (result + sign * number);
    }

    /**
     * Full calculator (LeetCode 772) by recursive descent — the grammar IS the precedence:
     *   expr   := term (('+'|'-') term)*
     *   term   := factor (('*'|'/') factor)*
     *   factor := number | '(' expr ')' | '-' factor
     */
    public static int evaluate(String s) {
        Parser p = new Parser(s.replace(" ", ""));
        long v = p.expr();
        if (p.pos != p.s.length()) throw new IllegalArgumentException("unexpected '" + p.s.charAt(p.pos) + "'");
        return (int) v;
    }

    private static final class Parser {
        final String s;
        int pos;

        Parser(String s) {
            this.s = s;
        }

        long expr() {
            long v = term();
            while (pos < s.length() && (peek() == '+' || peek() == '-')) {
                v = s.charAt(pos++) == '+' ? v + term() : v - term();
            }
            return v;
        }

        long term() {
            long v = factor();
            while (pos < s.length() && (peek() == '*' || peek() == '/')) {
                v = s.charAt(pos++) == '*' ? v * factor() : v / factor();
            }
            return v;
        }

        long factor() {
            if (pos >= s.length()) throw new IllegalArgumentException("unexpected end");
            char c = peek();
            if (c == '-') {
                pos++;
                return -factor();                            // unary minus
            }
            if (c == '(') {
                pos++;
                long v = expr();
                if (pos >= s.length() || s.charAt(pos++) != ')') throw new IllegalArgumentException("missing )");
                return v;
            }
            if (!Character.isDigit(c)) throw new IllegalArgumentException("unexpected '" + c + "'");
            long n = 0;
            while (pos < s.length() && Character.isDigit(peek())) n = n * 10 + (s.charAt(pos++) - '0');
            return n;
        }

        char peek() {
            return s.charAt(pos);
        }
    }

    /**
     * Two-stack version (operands + operators), the classic Dijkstra approach: apply an operator whenever the one
     * on the stack has ≥ precedence. Handles + - * / and parentheses (binary operators only).
     */
    public static int twoStacks(String s) {
        Deque<Long> nums = new ArrayDeque<>();
        Deque<Character> ops = new ArrayDeque<>();
        String e = s.replace(" ", "");
        for (int i = 0; i < e.length(); i++) {
            char c = e.charAt(i);
            if (Character.isDigit(c)) {
                long n = 0;
                while (i < e.length() && Character.isDigit(e.charAt(i))) n = n * 10 + (e.charAt(i++) - '0');
                i--;
                nums.push(n);
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (ops.peek() != '(') apply(nums, ops.pop());
                ops.pop();
            } else {
                while (!ops.isEmpty() && ops.peek() != '(' && prec(ops.peek()) >= prec(c)) apply(nums, ops.pop());
                ops.push(c);
            }
        }
        while (!ops.isEmpty()) apply(nums, ops.pop());
        return (int) (long) nums.pop();
    }

    private static void apply(Deque<Long> nums, char op) {
        long b = nums.pop(), a = nums.pop();
        nums.push(switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            default -> a / b;
        });
    }

    private static int prec(char op) {
        return op == '+' || op == '-' ? 1 : 2;
    }
}
