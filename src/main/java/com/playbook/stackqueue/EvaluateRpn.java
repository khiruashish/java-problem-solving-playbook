package com.playbook.stackqueue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Evaluate a postfix (Reverse Polish) expression of integers with + - * /. Division truncates toward zero.
 * ["2","1","+","3","*"] = (2 + 1) * 3 = 9. Invalid input throws IllegalArgumentException.
 */
public final class EvaluateRpn {
    private EvaluateRpn() {}

    /** Operand stack: numbers are pushed; an operator pops two, applies, pushes the result. O(n). */
    public static int evaluate(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String t : tokens) {
            if (t.length() == 1 && "+-*/".contains(t)) {
                if (stack.size() < 2) throw new IllegalArgumentException("not enough operands for " + t);
                int right = stack.pop(), left = stack.pop();    // order matters for - and /
                stack.push(switch (t.charAt(0)) {
                    case '+' -> left + right;
                    case '-' -> left - right;
                    case '*' -> left * right;
                    default -> left / right;
                });
            } else {
                stack.push(Integer.parseInt(t));             // handles negatives like "-11"
            }
        }
        if (stack.size() != 1) throw new IllegalArgumentException("malformed expression");
        return stack.pop();
    }

    /** Recursive: read the expression from the END — an operator's operands are the two sub-expressions before it. */
    public static int recursive(String[] tokens) {
        int[] pos = {tokens.length - 1};
        int result = eval(tokens, pos);
        if (pos[0] != -1) throw new IllegalArgumentException("malformed expression");
        return result;
    }

    private static int eval(String[] t, int[] pos) {
        if (pos[0] < 0) throw new IllegalArgumentException("not enough operands");
        String tok = t[pos[0]--];
        if (tok.length() == 1 && "+-*/".contains(tok)) {
            int right = eval(t, pos), left = eval(t, pos);   // right operand is nearer the operator
            return switch (tok.charAt(0)) {
                case '+' -> left + right;
                case '-' -> left - right;
                case '*' -> left * right;
                default -> left / right;
            };
        }
        return Integer.parseInt(tok);
    }
}
