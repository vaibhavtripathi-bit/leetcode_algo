package com.leetcode.solutions.stack.problems.easy

/**
 * 20. Valid Parentheses
 * https://leetcode.com/problems/valid-parentheses/
 *
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid.
 *
 * An input string is valid if:
 * 1. Open brackets must be closed by the same type of brackets.
 * 2. Open brackets must be closed in the correct order.
 * 3. Every close bracket has a corresponding open bracket of the same type.
 *
 * Example 1:
 * Input: s = "()"
 * Output: true
 *
 * Example 2:
 * Input: s = "()[]{}"
 * Output: true
 *
 * Example 3:
 * Input: s = "(]"
 * Output: false
 *
 * Constraints:
 * - 1 <= s.length <= 10^4
 * - s consists of parentheses only '()[]{}'.
 *
 * Companies: Google, Amazon, Microsoft, Meta, Bloomberg, Apple, Oracle, Adobe
 */
class ValidParentheses {

    /**
     * Solution 1: Stack with Map
     * Use a stack to track opening brackets and a map for matching pairs.
     *
     * Time Complexity: O(n) - single pass through string
     * Space Complexity: O(n) - stack can grow up to n/2 in worst case
     */
    fun isValidWithMap(s: String): Boolean {
        val stack = ArrayDeque<Char>()
        val matchingPairs = mapOf(')' to '(', '}' to '{', ']' to '[')

        for (char in s) {
            if (char in matchingPairs.values) {
                stack.addLast(char)
            } else if (char in matchingPairs.keys) {
                if (stack.isEmpty() || stack.removeLast() != matchingPairs[char]) {
                    return false
                }
            }
        }

        return stack.isEmpty()
    }

    /**
     * Solution 2: Stack with When Expression
     * Use Kotlin's when expression for cleaner bracket matching.
     *
     * Time Complexity: O(n) - single pass through string
     * Space Complexity: O(n) - stack storage
     */
    fun isValidWithWhen(s: String): Boolean {
        val stack = ArrayDeque<Char>()

        for (char in s) {
            when (char) {
                '(', '{', '[' -> stack.addLast(char)
                ')' -> if (stack.isEmpty() || stack.removeLast() != '(') return false
                '}' -> if (stack.isEmpty() || stack.removeLast() != '{') return false
                ']' -> if (stack.isEmpty() || stack.removeLast() != '[') return false
            }
        }

        return stack.isEmpty()
    }

    /**
     * Solution 3: Push Closing Bracket Approach
     * Instead of pushing opening brackets, push the expected closing bracket.
     * This simplifies the comparison logic.
     *
     * Time Complexity: O(n) - single pass through string
     * Space Complexity: O(n) - stack storage
     */
    fun isValidPushClosing(s: String): Boolean {
        val stack = ArrayDeque<Char>()

        for (char in s) {
            when (char) {
                '(' -> stack.addLast(')')
                '{' -> stack.addLast('}')
                '[' -> stack.addLast(']')
                else -> {
                    if (stack.isEmpty() || stack.removeLast() != char) {
                        return false
                    }
                }
            }
        }

        return stack.isEmpty()
    }

    /**
     * Solution 4: Recursive Replacement (Educational)
     * Repeatedly remove valid pairs until string is empty or no more pairs found.
     * Not recommended for production due to inefficiency.
     *
     * Time Complexity: O(n^2) - each replacement can take O(n), done n/2 times
     * Space Complexity: O(n) - string copies during replacement
     */
    fun isValidReplacement(s: String): Boolean {
        var current = s
        var previous: String

        do {
            previous = current
            current = current
                .replace("()", "")
                .replace("{}", "")
                .replace("[]", "")
        } while (current != previous)

        return current.isEmpty()
    }
}

private fun getMatchingOpen(close: Char): Char {
    return when (close) {
        ')' -> '('
        '}' -> '{'
        ']' -> '['
        else -> throw IllegalArgumentException("Invalid closing bracket: $close")
    }
}

private fun isOpenBracket(char: Char): Boolean {
    return char in setOf('(', '{', '[')
}

private fun isCloseBracket(char: Char): Boolean {
    return char in setOf(')', '}', ']')
}
