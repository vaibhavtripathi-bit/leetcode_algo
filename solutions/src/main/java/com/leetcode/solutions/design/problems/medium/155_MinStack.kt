package com.leetcode.solutions.design.problems.medium

/**
 * LeetCode 155: Min Stack
 * https://leetcode.com/problems/min-stack/
 *
 * Difficulty: Medium
 * Companies: All FAANG
 *
 * Design a stack that supports push, pop, top, and getMin in O(1).
 */

/** Solution 1: Pair Stack (stores value + current min) - Time: O(1), Space: O(n) */
class MinStack {
    private val stack = ArrayDeque<Pair<Int, Int>>()  // (value, minSoFar)

    fun push(`val`: Int) {
        val currentMin = if (stack.isEmpty()) `val` else minOf(`val`, stack.last().second)
        stack.addLast(`val` to currentMin)
    }

    fun pop()    { stack.removeLast() }
    fun top()    = stack.last().first
    fun getMin() = stack.last().second
}

/** Solution 2: Two Stacks (separate min tracking) - Time: O(1), Space: O(n) */
class MinStack2 {
    private val mainStack = ArrayDeque<Int>()
    private val minStack  = ArrayDeque<Int>()

    fun push(`val`: Int) {
        mainStack.addLast(`val`)
        val newMin = if (minStack.isEmpty()) `val` else minOf(`val`, minStack.last())
        minStack.addLast(newMin)
    }

    fun pop() {
        mainStack.removeLast()
        minStack.removeLast()
    }

    fun top()    = mainStack.last()
    fun getMin() = minStack.last()
}
