package com.leetcode.solutions.stack.problems.easy

/**
 * 232. Implement Queue using Stacks
 * https://leetcode.com/problems/implement-queue-using-stacks/
 *
 * Implement a first in first out (FIFO) queue using only two stacks.
 * The implemented queue should support all the functions of a normal queue
 * (push, peek, pop, and empty).
 *
 * Implement the MyQueue class:
 * - void push(int x) Pushes element x to the back of the queue.
 * - int pop() Removes the element from the front of the queue and returns it.
 * - int peek() Returns the element at the front of the queue.
 * - boolean empty() Returns true if the queue is empty, false otherwise.
 *
 * Notes:
 * - You must use only standard operations of a stack.
 * - Follow-up: Can you implement the queue such that each operation is amortized O(1)?
 *
 * Example:
 * Input: ["MyQueue", "push", "push", "peek", "pop", "empty"]
 *        [[], [1], [2], [], [], []]
 * Output: [null, null, null, 1, 1, false]
 *
 * Constraints:
 * - 1 <= x <= 9
 * - At most 100 calls will be made to push, pop, peek, and empty.
 * - All the calls to pop and peek are valid.
 *
 * Companies: Amazon, Microsoft, Google, Meta, Apple, Bloomberg, Goldman Sachs
 */

/**
 * Solution 1: Two Stacks - Push O(n), Pop O(1)
 * Transfer all elements on each push operation.
 *
 * Time Complexity: push O(n), pop O(1), peek O(1), empty O(1)
 * Space Complexity: O(n) for storing elements
 */
class MyQueuePushHeavy {
    private val inputStack = ArrayDeque<Int>()
    private val outputStack = ArrayDeque<Int>()

    fun push(x: Int) {
        while (outputStack.isNotEmpty()) {
            inputStack.addLast(outputStack.removeLast())
        }
        inputStack.addLast(x)
        while (inputStack.isNotEmpty()) {
            outputStack.addLast(inputStack.removeLast())
        }
    }

    fun pop(): Int = outputStack.removeLast()

    fun peek(): Int = outputStack.last()

    fun empty(): Boolean = outputStack.isEmpty()
}

/**
 * Solution 2: Two Stacks - Amortized O(1)
 * Lazy transfer: only move elements when output stack is empty.
 * This achieves amortized O(1) for all operations.
 *
 * Time Complexity: push O(1), pop amortized O(1), peek amortized O(1), empty O(1)
 * Space Complexity: O(n) for storing elements
 */
class MyQueueAmortized {
    private val inputStack = ArrayDeque<Int>()
    private val outputStack = ArrayDeque<Int>()

    fun push(x: Int) {
        inputStack.addLast(x)
    }

    fun pop(): Int {
        transferIfNeeded()
        return outputStack.removeLast()
    }

    fun peek(): Int {
        transferIfNeeded()
        return outputStack.last()
    }

    fun empty(): Boolean = inputStack.isEmpty() && outputStack.isEmpty()

    private fun transferIfNeeded() {
        if (outputStack.isEmpty()) {
            while (inputStack.isNotEmpty()) {
                outputStack.addLast(inputStack.removeLast())
            }
        }
    }
}

/**
 * Solution 3: Single Stack with Recursion
 * Use recursion to simulate second stack during pop/peek.
 * Educational approach - not recommended for production.
 *
 * Time Complexity: push O(1), pop O(n), peek O(n)
 * Space Complexity: O(n) for recursion stack
 */
class MyQueueRecursive {
    private val stack = ArrayDeque<Int>()

    fun push(x: Int) {
        stack.addLast(x)
    }

    fun pop(): Int {
        return removeBottom()
    }

    fun peek(): Int {
        return peekBottom()
    }

    fun empty(): Boolean = stack.isEmpty()

    private fun removeBottom(): Int {
        val top = stack.removeLast()
        if (stack.isEmpty()) {
            return top
        }
        val bottom = removeBottom()
        stack.addLast(top)
        return bottom
    }

    private fun peekBottom(): Int {
        val top = stack.removeLast()
        if (stack.isEmpty()) {
            stack.addLast(top)
            return top
        }
        val bottom = peekBottom()
        stack.addLast(top)
        return bottom
    }
}

/**
 * Solution 4: Two Stacks with Front Cache
 * Optimization of amortized approach with cached front element.
 *
 * Time Complexity: push O(1), pop amortized O(1), peek O(1), empty O(1)
 * Space Complexity: O(n) for storing elements
 */
class MyQueueWithFrontCache {
    private val inputStack = ArrayDeque<Int>()
    private val outputStack = ArrayDeque<Int>()
    private var front: Int? = null

    fun push(x: Int) {
        if (inputStack.isEmpty()) {
            front = x
        }
        inputStack.addLast(x)
    }

    fun pop(): Int {
        if (outputStack.isEmpty()) {
            while (inputStack.isNotEmpty()) {
                outputStack.addLast(inputStack.removeLast())
            }
        }
        return outputStack.removeLast()
    }

    fun peek(): Int {
        return if (outputStack.isNotEmpty()) {
            outputStack.last()
        } else {
            front!!
        }
    }

    fun empty(): Boolean = inputStack.isEmpty() && outputStack.isEmpty()
}

private fun <T> ArrayDeque<T>.transferTo(destination: ArrayDeque<T>) {
    while (this.isNotEmpty()) {
        destination.addLast(this.removeLast())
    }
}

private fun validateQueueOperation(queue: MyQueueAmortized, expectedSize: Int) {
    require(!queue.empty() || expectedSize == 0) { "Queue state mismatch" }
}
