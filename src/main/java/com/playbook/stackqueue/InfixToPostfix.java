package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Convert an infix expression with single-letter/digit operands, + - * / ^ and parentheses to postfix.
 * "a+b*(c^d-e)^(f+g*h)-i" → "abcd^e-fgh*+^*+i-". ^ is right-associative. Spaces are ignored.
 */
public final class InfixToPostfix {
    private InfixToPostfix() {}

    /** Dijkstra's shunting-yard algorithm with an operator stack. O(n). */
    public static String convert(String infix) {
        StringBuilder out = new StringBuilder();
        Deque<Character> ops = new ArrayDeque<>();
        for (char c : infix.toCharArray()) {
            if (c == ' ') continue;
            if (Character.isLetterOrDigit(c)) {
                out.append(c);                               // operands go straight to the output
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') out.append(ops.pop());
                if (ops.isEmpty()) throw new IllegalArgumentException("unbalanced )");
                ops.pop();                                   // discard '('
            } else {
                // pop operators that must be applied BEFORE c: higher precedence, or equal and left-associative
                while (!ops.isEmpty() && ops.peek() != '('
                        && (prec(ops.peek()) > prec(c) || (prec(ops.peek()) == prec(c) && c != '^'))) {
                    out.append(ops.pop());
                }
                ops.push(c);
            }
        }
        while (!ops.isEmpty()) {
            char op = ops.pop();
            if (op == '(') throw new IllegalArgumentException("unbalanced (");
            out.append(op);
        }
        return out.toString();
    }

    /** Bonus: infix → prefix = reverse, swap parentheses, convert (treating ^ carefully), reverse. */
    public static String toPrefix(String infix) {
        StringBuilder rev = new StringBuilder();
        for (int i = infix.length() - 1; i >= 0; i--) {
            char c = infix.charAt(i);
            rev.append(c == '(' ? ')' : c == ')' ? '(' : c);
        }
        // On the reversed string, + - * / become right-associative and ^ left-associative.
        StringBuilder out = new StringBuilder();
        Deque<Character> ops = new ArrayDeque<>();
        for (char c : rev.toString().toCharArray()) {
            if (c == ' ') continue;
            if (Character.isLetterOrDigit(c)) out.append(c);
            else if (c == '(') ops.push(c);
            else if (c == ')') {
                while (ops.peek() != '(') out.append(ops.pop());
                ops.pop();
            } else {
                while (!ops.isEmpty() && ops.peek() != '('
                        && (prec(ops.peek()) > prec(c) || (prec(ops.peek()) == prec(c) && c == '^'))) {
                    out.append(ops.pop());
                }
                ops.push(c);
            }
        }
        while (!ops.isEmpty()) out.append(ops.pop());
        return out.reverse().toString();
    }

    private static int prec(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> throw new IllegalArgumentException("unknown operator " + op);
        };
    }
}
