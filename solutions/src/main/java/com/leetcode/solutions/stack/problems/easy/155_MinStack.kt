package com.leetcode.solutions.stack.problems.easy

/**
 * LeetCode 155: Min Stack
 * https://leetcode.com/problems/min-stack/
 *
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Design a stack that supports push, pop, top, and getMin in O(1).
 */

/** Solution 1: Two Stacks (Auxiliary Min Stack) - All ops O(1) */
class MinStack1 {
    private val main = ArrayDeque<Int>()
    private val mins = ArrayDeque<Int>()

    fun push(value: Int) {
        main.addLast(value)
        if (mins.isEmpty() || value <= mins.last()) {
            mins.addLast(value)
        }
    }

    fun pop() {
        val removed = main.removeLast()
        if (removed == mins.last()) mins.removeLast()
    }

    fun top(): Int = main.last()

    fun getMin(): Int = mins.last()
}

/** Solution 2: Store Pair (value, currentMin) - All ops O(1) */
class MinStack2 {
    private val stack = ArrayDeque<Pair<Int, Int>>()

    fun push(value: Int) {
        val currentMin = if (stack.isEmpty()) value else minOf(value, stack.last().second)
        stack.addLast(value to currentMin)
    }

    fun pop() { stack.removeLast() }

    fun top(): Int = stack.last().first

    fun getMin(): Int = stack.last().second
}

/** Solution 3: Encode min inside single stack using math - All ops O(1) */
class MinStack3 {
    private val stack = ArrayDeque<Long>()
    private var min = Long.MAX_VALUE

    fun push(value: Int) {
        val v = value.toLong()
        if (stack.isEmpty()) {
            min = v
            stack.addLast(0L)
        } else {
            stack.addLast(v - min)
            if (v < min) min = v
        }
    }

    fun pop() {
        val top = stack.removeLast()
        if (top < 0) min -= top
    }

    fun top(): Int {
        val top = stack.last()
        return if (top < 0) min.toInt() else (min + top).toInt()
    }

    fun getMin(): Int = min.toInt()
}
