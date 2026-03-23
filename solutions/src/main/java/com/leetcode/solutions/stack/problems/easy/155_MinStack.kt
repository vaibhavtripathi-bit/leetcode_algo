package com.leetcode.solutions.stack.problems.easy

/**
 * 155. Min Stack
 * https://leetcode.com/problems/min-stack/
 *
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * Implement the MinStack class:
 * - MinStack() initializes the stack object.
 * - void push(int val) pushes the element val onto the stack.
 * - void pop() removes the element on the top of the stack.
 * - int top() gets the top element of the stack.
 * - int getMin() retrieves the minimum element in the stack.
 *
 * You must implement a solution with O(1) time complexity for each function.
 *
 * Example:
 * Input: ["MinStack","push","push","push","getMin","pop","top","getMin"]
 *        [[],[-2],[0],[-3],[],[],[],[]]
 * Output: [null,null,null,null,-3,null,0,-2]
 *
 * Constraints:
 * - -2^31 <= val <= 2^31 - 1
 * - Methods pop, top and getMin operations will always be called on non-empty stacks.
 * - At most 3 * 10^4 calls will be made to push, pop, top, and getMin.
 *
 * Companies: Amazon, Google, Microsoft, Meta, Bloomberg, Apple, Uber, Goldman Sachs
 */

/**
 * Solution 1: Two Stacks Approach
 * Maintain a separate stack to track minimum values.
 *
 * Time Complexity: O(1) for all operations
 * Space Complexity: O(n) for storing elements and minimums
 */
class MinStackTwoStacks {
    private val stack = ArrayDeque<Int>()
    private val minStack = ArrayDeque<Int>()

    fun push(value: Int) {
        stack.addLast(value)
        val currentMin = if (minStack.isEmpty()) value else minOf(value, minStack.last())
        minStack.addLast(currentMin)
    }

    fun pop() {
        stack.removeLast()
        minStack.removeLast()
    }

    fun top(): Int = stack.last()

    fun getMin(): Int = minStack.last()
}

/**
 * Solution 2: Single Stack with Pairs
 * Store value and current minimum as pairs in a single stack.
 *
 * Time Complexity: O(1) for all operations
 * Space Complexity: O(n) for storing pairs
 */
class MinStackPairs {
    private val stack = ArrayDeque<Pair<Int, Int>>()

    fun push(value: Int) {
        val currentMin = if (stack.isEmpty()) value else minOf(value, stack.last().second)
        stack.addLast(Pair(value, currentMin))
    }

    fun pop() {
        stack.removeLast()
    }

    fun top(): Int = stack.last().first

    fun getMin(): Int = stack.last().second
}

/**
 * Solution 3: Single Stack with Encoding
 * Store difference between value and minimum to save space.
 * Uses Long to handle integer overflow.
 *
 * Time Complexity: O(1) for all operations
 * Space Complexity: O(n) but more memory efficient for large values
 */
class MinStackEncoded {
    private val stack = ArrayDeque<Long>()
    private var min: Long = 0

    fun push(value: Int) {
        val valueLong = value.toLong()
        if (stack.isEmpty()) {
            stack.addLast(0L)
            min = valueLong
        } else {
            stack.addLast(valueLong - min)
            if (valueLong < min) {
                min = valueLong
            }
        }
    }

    fun pop() {
        val top = stack.removeLast()
        if (top < 0) {
            min = min - top
        }
    }

    fun top(): Int {
        val top = stack.last()
        return if (top < 0) min.toInt() else (top + min).toInt()
    }

    fun getMin(): Int = min.toInt()
}

/**
 * Solution 4: Linked List Node Approach
 * Each node stores value, minimum at that point, and link to next node.
 *
 * Time Complexity: O(1) for all operations
 * Space Complexity: O(n) for linked list nodes
 */
class MinStackLinkedList {
    private var head: MinNode? = null

    fun push(value: Int) {
        val currentMin = head?.min?.let { minOf(it, value) } ?: value
        head = MinNode(value, currentMin, head)
    }

    fun pop() {
        head = head?.next
    }

    fun top(): Int = head!!.value

    fun getMin(): Int = head!!.min
}

private data class MinNode(
    val value: Int,
    val min: Int,
    val next: MinNode?
)

private fun validateMinStack(minStack: MinStackTwoStacks) {
    require(minStack.top() >= Int.MIN_VALUE) { "Stack value out of range" }
}

private fun createMinStack(): MinStackTwoStacks {
    return MinStackTwoStacks()
}
