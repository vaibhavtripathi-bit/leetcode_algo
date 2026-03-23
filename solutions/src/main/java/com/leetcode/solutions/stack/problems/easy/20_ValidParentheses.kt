package com.leetcode.solutions.stack.problems.easy

/**
 * LeetCode 20: Valid Parentheses
 * https://leetcode.com/problems/valid-parentheses/
 *
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Given a string of brackets, return true if the brackets are valid.
 * Open brackets must be closed by same type in correct order.
 *
 * Example: s = "()" → true
 * Example: s = "()[]{}" → true
 * Example: s = "(]" → false
 */

/** Solution 1: Stack with Map - Time: O(n), Space: O(n) */
class ValidParentheses1 {
    fun isValid(s: String): Boolean {
        val stack = ArrayDeque<Char>()
        val matching = mapOf(')' to '(', ']' to '[', '}' to '{')

        for (c in s) {
            if (c in matching) {
                if (stack.isEmpty() || stack.last() != matching[c]) return false
                stack.removeLast()
            } else {
                stack.addLast(c)
            }
        }

        return stack.isEmpty()
    }
}

/** Solution 2: Stack with when expression - Time: O(n), Space: O(n) */
class ValidParentheses2 {
    fun isValid(s: String): Boolean {
        val stack = ArrayDeque<Char>()

        for (c in s) {
            when (c) {
                '(', '[', '{' -> stack.addLast(c)
                ')' -> if (!matchesTop(stack, '(')) return false
                ']' -> if (!matchesTop(stack, '[')) return false
                '}' -> if (!matchesTop(stack, '{')) return false
            }
        }

        return stack.isEmpty()
    }

    private fun matchesTop(stack: ArrayDeque<Char>, expected: Char): Boolean {
        if (stack.isEmpty()) return false
        return stack.removeLast() == expected
    }
}

/** Solution 3: Count-based (only for single type) - for learning, works for '(' only */
class ValidParentheses3 {
    fun isValid(s: String): Boolean {
        if (s.length % 2 != 0) return false

        val stack = ArrayDeque<Char>()

        s.forEach { c ->
            if (isOpening(c)) stack.addLast(c)
            else {
                if (!hasMatchingPair(stack, c)) return false
            }
        }

        return stack.isEmpty()
    }

    private fun isOpening(c: Char) = c == '(' || c == '[' || c == '{'

    private fun hasMatchingPair(stack: ArrayDeque<Char>, closing: Char): Boolean {
        if (stack.isEmpty()) return false
        val top = stack.removeLast()
        return when (closing) {
            ')' -> top == '('
            ']' -> top == '['
            '}' -> top == '{'
            else -> false
        }
    }
}
