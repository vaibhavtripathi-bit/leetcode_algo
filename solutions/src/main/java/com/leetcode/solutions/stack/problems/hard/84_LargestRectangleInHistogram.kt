package com.leetcode.solutions.stack.problems.hard

/**
 * 84. Largest Rectangle in Histogram
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 *
 * Given an array of integers heights representing the histogram's bar height where
 * the width of each bar is 1, return the area of the largest rectangle in the histogram.
 *
 * Example 1:
 * Input: heights = [2,1,5,6,2,3]
 * Output: 10
 * Explanation: The largest rectangle has area = 10 units (5 and 6 bars).
 *
 * Example 2:
 * Input: heights = [2,4]
 * Output: 4
 *
 * Constraints:
 * - 1 <= heights.length <= 10^5
 * - 0 <= heights[i] <= 10^4
 *
 * Companies: Amazon, Google, Microsoft, Meta, Apple, Bloomberg, Adobe, Uber, ByteDance
 */
class LargestRectangleInHistogram {

    /**
     * Solution 1: Monotonic Increasing Stack
     * Maintain a stack of indices with increasing heights.
     * When we encounter a smaller height, calculate areas for taller bars.
     *
     * Time Complexity: O(n) - each bar pushed and popped at most once
     * Space Complexity: O(n) - stack storage
     */
    fun largestRectangleAreaStack(heights: IntArray): Int {
        val n = heights.size
        val stack = ArrayDeque<Int>()
        var maxArea = 0

        for (i in 0..n) {
            val currentHeight = if (i == n) 0 else heights[i]

            while (stack.isNotEmpty() && currentHeight < heights[stack.last()]) {
                val height = heights[stack.removeLast()]
                val width = if (stack.isEmpty()) i else i - stack.last() - 1
                maxArea = maxOf(maxArea, height * width)
            }
            stack.addLast(i)
        }

        return maxArea
    }

    /**
     * Solution 2: Stack with Sentinel Values
     * Add sentinel values at both ends to simplify boundary handling.
     *
     * Time Complexity: O(n) - single pass with stack
     * Space Complexity: O(n) - stack and extended array
     */
    fun largestRectangleAreaSentinel(heights: IntArray): Int {
        val extendedHeights = intArrayOf(0) + heights + intArrayOf(0)
        val stack = ArrayDeque<Int>()
        var maxArea = 0

        for (i in extendedHeights.indices) {
            while (stack.isNotEmpty() && extendedHeights[i] < extendedHeights[stack.last()]) {
                val height = extendedHeights[stack.removeLast()]
                val width = i - stack.last() - 1
                maxArea = maxOf(maxArea, height * width)
            }
            stack.addLast(i)
        }

        return maxArea
    }

    /**
     * Solution 3: Precompute Left and Right Boundaries
     * For each bar, find the nearest smaller bar on left and right.
     * Then calculate area for each bar as the center.
     *
     * Time Complexity: O(n) - three passes through array
     * Space Complexity: O(n) - for boundary arrays
     */
    fun largestRectangleAreaBoundaries(heights: IntArray): Int {
        val n = heights.size
        val leftSmaller = IntArray(n)
        val rightSmaller = IntArray(n)

        val stack = ArrayDeque<Int>()
        for (i in 0 until n) {
            while (stack.isNotEmpty() && heights[stack.last()] >= heights[i]) {
                stack.removeLast()
            }
            leftSmaller[i] = if (stack.isEmpty()) -1 else stack.last()
            stack.addLast(i)
        }

        stack.clear()
        for (i in n - 1 downTo 0) {
            while (stack.isNotEmpty() && heights[stack.last()] >= heights[i]) {
                stack.removeLast()
            }
            rightSmaller[i] = if (stack.isEmpty()) n else stack.last()
            stack.addLast(i)
        }

        var maxArea = 0
        for (i in 0 until n) {
            val width = rightSmaller[i] - leftSmaller[i] - 1
            maxArea = maxOf(maxArea, heights[i] * width)
        }

        return maxArea
    }

    /**
     * Solution 4: Divide and Conquer
     * Find minimum height bar, calculate area, then recursively solve left and right.
     * Can be optimized with segment tree for O(n log n) average case.
     *
     * Time Complexity: O(n log n) average, O(n^2) worst case (sorted array)
     * Space Complexity: O(n) - recursion stack
     */
    fun largestRectangleAreaDivideConquer(heights: IntArray): Int {
        return divideAndConquer(heights, 0, heights.size - 1)
    }

    private fun divideAndConquer(heights: IntArray, left: Int, right: Int): Int {
        if (left > right) return 0
        if (left == right) return heights[left]

        var minIndex = left
        for (i in left..right) {
            if (heights[i] < heights[minIndex]) {
                minIndex = i
            }
        }

        val areaWithMin = heights[minIndex] * (right - left + 1)
        val leftArea = divideAndConquer(heights, left, minIndex - 1)
        val rightArea = divideAndConquer(heights, minIndex + 1, right)

        return maxOf(areaWithMin, leftArea, rightArea)
    }
}

private fun findNextSmallerIndex(heights: IntArray, startIndex: Int): Int {
    for (i in startIndex + 1 until heights.size) {
        if (heights[i] < heights[startIndex]) {
            return i
        }
    }
    return heights.size
}

private fun findPreviousSmallerIndex(heights: IntArray, startIndex: Int): Int {
    for (i in startIndex - 1 downTo 0) {
        if (heights[i] < heights[startIndex]) {
            return i
        }
    }
    return -1
}

private fun calculateRectangleArea(height: Int, leftBound: Int, rightBound: Int): Int {
    return height * (rightBound - leftBound - 1)
}

private fun validateHistogram(heights: IntArray): Boolean {
    return heights.isNotEmpty() && heights.all { it >= 0 }
}
