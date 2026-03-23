package com.leetcode.solutions.stack.problems.medium

/**
 * 150. Evaluate Reverse Polish Notation
 * https://leetcode.com/problems/evaluate-reverse-polish-notation/
 *
 * You are given an array of strings tokens that represents an arithmetic expression
 * in Reverse Polish Notation.
 *
 * Evaluate the expression. Return an integer that represents the value of the expression.
 *
 * Note that:
 * - The valid operators are '+', '-', '*', and '/'.
 * - Each operand may be an integer or another expression.
 * - The division between two integers always truncates toward zero.
 * - There will not be any division by zero.
 * - The input represents a valid arithmetic expression in reverse polish notation.
 * - The answer and all intermediate calculations fit in a 32-bit integer.
 *
 * Example 1:
 * Input: tokens = ["2","1","+","3","*"]
 * Output: 9
 * Explanation: ((2 + 1) * 3) = 9
 *
 * Example 2:
 * Input: tokens = ["4","13","5","/","+"]
 * Output: 6
 * Explanation: (4 + (13 / 5)) = 6
 *
 * Example 3:
 * Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
 * Output: 22
 *
 * Constraints:
 * - 1 <= tokens.length <= 10^4
 * - tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in range [-200, 200].
 *
 * Companies: Amazon, Google, Microsoft, Meta, LinkedIn, Bloomberg, Apple
 */
class EvaluateReversePolishNotation {

    /**
     * Solution 1: Stack with When Expression
     * Classic stack-based evaluation using Kotlin's when expression.
     *
     * Time Complexity: O(n) - single pass through tokens
     * Space Complexity: O(n) - stack storage
     */
    fun evalRPNWithWhen(tokens: Array<String>): Int {
        val stack = ArrayDeque<Int>()

        for (token in tokens) {
            when (token) {
                "+" -> {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.addLast(a + b)
                }
                "-" -> {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.addLast(a - b)
                }
                "*" -> {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.addLast(a * b)
                }
                "/" -> {
                    val b = stack.removeLast()
                    val a = stack.removeLast()
                    stack.addLast(a / b)
                }
                else -> stack.addLast(token.toInt())
            }
        }

        return stack.removeLast()
    }

    /**
     * Solution 2: Stack with Lambda Map
     * Use a map of operator lambdas for cleaner operation handling.
     *
     * Time Complexity: O(n) - single pass through tokens
     * Space Complexity: O(n) - stack storage
     */
    fun evalRPNWithLambda(tokens: Array<String>): Int {
        val operations: Map<String, (Int, Int) -> Int> = mapOf(
            "+" to { a, b -> a + b },
            "-" to { a, b -> a - b },
            "*" to { a, b -> a * b },
            "/" to { a, b -> a / b }
        )

        val stack = ArrayDeque<Int>()

        for (token in tokens) {
            val operation = operations[token]
            if (operation != null) {
                val b = stack.removeLast()
                val a = stack.removeLast()
                stack.addLast(operation(a, b))
            } else {
                stack.addLast(token.toInt())
            }
        }

        return stack.removeLast()
    }

    /**
     * Solution 3: Array as Stack (Optimized)
     * Use primitive array instead of ArrayDeque for better performance.
     *
     * Time Complexity: O(n) - single pass through tokens
     * Space Complexity: O(n) - array storage, but more cache-friendly
     */
    fun evalRPNOptimized(tokens: Array<String>): Int {
        val stack = IntArray(tokens.size)
        var top = -1

        for (token in tokens) {
            when (token) {
                "+", "-", "*", "/" -> {
                    val b = stack[top--]
                    val a = stack[top]
                    stack[top] = applyOperation(a, b, token)
                }
                else -> stack[++top] = token.toInt()
            }
        }

        return stack[top]
    }

    private fun applyOperation(a: Int, b: Int, operator: String): Int {
        return when (operator) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            else -> throw IllegalArgumentException("Unknown operator: $operator")
        }
    }

    /**
     * Solution 4: Recursive Approach (Educational)
     * Process tokens from right to left recursively.
     * Demonstrates alternative thinking but less practical.
     *
     * Time Complexity: O(n) - visits each token once
     * Space Complexity: O(n) - recursion stack
     */
    fun evalRPNRecursive(tokens: Array<String>): Int {
        val index = intArrayOf(tokens.size - 1)
        return evaluate(tokens, index)
    }

    private fun evaluate(tokens: Array<String>, index: IntArray): Int {
        val token = tokens[index[0]--]

        return when (token) {
            "+", "-", "*", "/" -> {
                val b = evaluate(tokens, index)
                val a = evaluate(tokens, index)
                applyOperation(a, b, token)
            }
            else -> token.toInt()
        }
    }
}

private fun isOperator(token: String): Boolean {
    return token in setOf("+", "-", "*", "/")
}

private fun parseNumber(token: String): Int? {
    return token.toIntOrNull()
}

private fun validateRPNExpression(tokens: Array<String>): Boolean {
    var operandCount = 0
    for (token in tokens) {
        if (isOperator(token)) {
            if (operandCount < 2) return false
            operandCount--
        } else {
            operandCount++
        }
    }
    return operandCount == 1
}
