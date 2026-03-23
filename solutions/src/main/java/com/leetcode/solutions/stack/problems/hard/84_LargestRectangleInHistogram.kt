package com.leetcode.solutions.stack.problems.hard

/**
 * LeetCode 84: Largest Rectangle in Histogram
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 *
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Given an array of bar heights, find the area of the largest rectangle.
 *
 * Example: heights = [2,1,5,6,2,3] → 10
 */

/** Solution 1: Monotonic Stack - Time: O(n), Space: O(n) */
class LargestRectangle1 {
    fun largestRectangleArea(heights: IntArray): Int {
        val stack = ArrayDeque<Int>()
        var maxArea = 0

        for (i in 0..heights.size) {
            val currentHeight = if (i == heights.size) 0 else heights[i]

            while (stack.isNotEmpty() && heights[stack.last()] > currentHeight) {
                val height = heights[stack.removeLast()]
                val width = calculateWidth(stack, i)
                maxArea = maxOf(maxArea, height * width)
            }

            stack.addLast(i)
        }

        return maxArea
    }

    private fun calculateWidth(stack: ArrayDeque<Int>, rightBoundary: Int): Int {
        return if (stack.isEmpty()) rightBoundary
               else rightBoundary - stack.last() - 1
    }
}

/** Solution 2: Left & Right Boundary Precompute - Time: O(n), Space: O(n) */
class LargestRectangle2 {
    fun largestRectangleArea(heights: IntArray): Int {
        val n = heights.size
        val leftBound = findLeftBoundaries(heights)
        val rightBound = findRightBoundaries(heights)

        return heights.indices.maxOf { i ->
            heights[i] * (rightBound[i] - leftBound[i] - 1)
        }
    }

    private fun findLeftBoundaries(heights: IntArray): IntArray {
        val n = heights.size
        val left = IntArray(n)
        val stack = ArrayDeque<Int>()

        for (i in 0 until n) {
            while (stack.isNotEmpty() && heights[stack.last()] >= heights[i]) {
                stack.removeLast()
            }
            left[i] = if (stack.isEmpty()) -1 else stack.last()
            stack.addLast(i)
        }

        return left
    }

    private fun findRightBoundaries(heights: IntArray): IntArray {
        val n = heights.size
        val right = IntArray(n) { n }
        val stack = ArrayDeque<Int>()

        for (i in n - 1 downTo 0) {
            while (stack.isNotEmpty() && heights[stack.last()] >= heights[i]) {
                stack.removeLast()
            }
            right[i] = if (stack.isEmpty()) n else stack.last()
            stack.addLast(i)
        }

        return right
    }
}

/** Solution 3: Divide and Conquer - Time: O(n log n) avg, Space: O(n) */
class LargestRectangle3 {
    fun largestRectangleArea(heights: IntArray): Int {
        return solve(heights, 0, heights.size - 1)
    }

    private fun solve(heights: IntArray, left: Int, right: Int): Int {
        if (left > right) return 0

        val minIdx = findMinIndex(heights, left, right)
        val withMinHeight = heights[minIdx] * (right - left + 1)

        val leftMax = solve(heights, left, minIdx - 1)
        val rightMax = solve(heights, minIdx + 1, right)

        return maxOf(withMinHeight, leftMax, rightMax)
    }

    private fun findMinIndex(heights: IntArray, left: Int, right: Int): Int {
        var minIdx = left
        for (i in left + 1..right) {
            if (heights[i] < heights[minIdx]) minIdx = i
        }
        return minIdx
    }
}
