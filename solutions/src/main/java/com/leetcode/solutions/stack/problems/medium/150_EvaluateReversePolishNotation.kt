package com.leetcode.solutions.stack.problems.medium

/**
 * LeetCode 150: Evaluate Reverse Polish Notation
 * https://leetcode.com/problems/evaluate-reverse-polish-notation/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, LinkedIn
 *
 * Evaluate an arithmetic expression in Reverse Polish Notation.
 * Valid operators: +, -, *, /. Integer division truncates toward zero.
 *
 * Example: tokens = ["2","1","+","3","*"] → 9
 * Example: tokens = ["4","13","5","/","+"] → 6
 */

/** Solution 1: Stack with when - Time: O(n), Space: O(n) */
class EvalRPN1 {
    fun evalRPN(tokens: Array<String>): Int {
        val stack = ArrayDeque<Int>()

        for (token in tokens) {
            when (token) {
                "+", "-", "*", "/" -> {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.addLast(applyOp(token, a, b))
                }
                else -> stack.addLast(token.toInt())
            }
        }

        return stack.last()
    }

    private fun applyOp(op: String, a: Int, b: Int) = when (op) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> a / b
        else -> throw IllegalArgumentException("Unknown operator: $op")
    }
}

/** Solution 2: Lambda map for operators - Time: O(n), Space: O(n) */
class EvalRPN2 {
    private val operators = mapOf<String, (Int, Int) -> Int>(
        "+" to { a, b -> a + b },
        "-" to { a, b -> a - b },
        "*" to { a, b -> a * b },
        "/" to { a, b -> a / b }
    )

    fun evalRPN(tokens: Array<String>): Int {
        val stack = ArrayDeque<Int>()

        for (token in tokens) {
            val op = operators[token]
            if (op != null) {
                val b = stack.removeLast()
                val a = stack.removeLast()
                stack.addLast(op(a, b))
            } else {
                stack.addLast(token.toInt())
            }
        }

        return stack.last()
    }
}
