package com.leetcode.solutions.stack.problems.easy

/**
 * LeetCode 232: Implement Queue using Stacks
 * https://leetcode.com/problems/implement-queue-using-stacks/
 *
 * Difficulty: Easy
 * Companies: Amazon, Microsoft, Google
 *
 * Implement a FIFO queue using only two stacks.
 * push, pop, peek must be amortized O(1).
 */

/** Solution 1: Two Stacks - Lazy Transfer (Amortized O(1)) */
class MyQueue1 {
    private val inbox = ArrayDeque<Int>()
    private val outbox = ArrayDeque<Int>()

    fun push(x: Int) {
        inbox.addLast(x)
    }

    fun pop(): Int {
        transferIfNeeded()
        return outbox.removeLast()
    }

    fun peek(): Int {
        transferIfNeeded()
        return outbox.last()
    }

    fun empty(): Boolean = inbox.isEmpty() && outbox.isEmpty()

    private fun transferIfNeeded() {
        if (outbox.isEmpty()) {
            while (inbox.isNotEmpty()) {
                outbox.addLast(inbox.removeLast())
            }
        }
    }
}

/** Solution 2: Two Stacks - Eager Transfer on Push */
class MyQueue2 {
    private val primary = ArrayDeque<Int>()
    private val helper = ArrayDeque<Int>()

    fun push(x: Int) {
        while (primary.isNotEmpty()) helper.addLast(primary.removeLast())
        primary.addLast(x)
        while (helper.isNotEmpty()) primary.addLast(helper.removeLast())
    }

    fun pop(): Int = primary.removeLast()

    fun peek(): Int = primary.last()

    fun empty(): Boolean = primary.isEmpty()
}
