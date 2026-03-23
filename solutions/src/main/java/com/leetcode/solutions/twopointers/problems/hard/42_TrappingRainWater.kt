package com.leetcode.solutions.twopointers.problems.hard

import kotlin.math.max
import kotlin.math.min

/**
 * 42. Trapping Rain Water
 * https://leetcode.com/problems/trapping-rain-water/
 *
 * Given n non-negative integers representing an elevation map where the width of each bar is 1,
 * compute how much water it can trap after raining.
 *
 * Companies: Amazon, Google, Facebook, Microsoft, Goldman Sachs, Apple, Bloomberg, Uber, Adobe
 */

/**
 * Solution 1: Two Pointers (Optimal)
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(1) - constant extra space
 *
 * Key insight: Water at each position is determined by min(maxLeft, maxRight) - height[i]
 * We can track left and right max while moving pointers inward.
 */
class TrappingRainWaterTwoPointers {
    fun trap(height: IntArray): Int {
        if (height.isEmpty()) return 0

        var left = 0
        var right = height.size - 1
        var leftMax = 0
        var rightMax = 0
        var totalWater = 0

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left]
                } else {
                    totalWater += leftMax - height[left]
                }
                left++
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right]
                } else {
                    totalWater += rightMax - height[right]
                }
                right--
            }
        }
        return totalWater
    }
}

/**
 * Solution 2: Dynamic Programming
 * Time Complexity: O(n) - three passes
 * Space Complexity: O(n) - two arrays for left/right max
 */
class TrappingRainWaterDP {
    fun trap(height: IntArray): Int {
        if (height.isEmpty()) return 0

        val n = height.size
        val leftMax = IntArray(n)
        val rightMax = IntArray(n)

        leftMax[0] = height[0]
        for (i in 1 until n) {
            leftMax[i] = max(leftMax[i - 1], height[i])
        }

        rightMax[n - 1] = height[n - 1]
        for (i in n - 2 downTo 0) {
            rightMax[i] = max(rightMax[i + 1], height[i])
        }

        var totalWater = 0
        for (i in 0 until n) {
            totalWater += min(leftMax[i], rightMax[i]) - height[i]
        }
        return totalWater
    }
}

/**
 * Solution 3: Stack-based Approach
 * Time Complexity: O(n) - each element pushed/popped once
 * Space Complexity: O(n) - stack space
 *
 * Process bars and use stack to track indices of bars that could trap water.
 */
class TrappingRainWaterStack {
    fun trap(height: IntArray): Int {
        val stack = ArrayDeque<Int>()
        var totalWater = 0

        for (current in height.indices) {
            while (stack.isNotEmpty() && height[current] > height[stack.last()]) {
                val top = stack.removeLast()

                if (stack.isEmpty()) break

                val distance = current - stack.last() - 1
                val boundedHeight = min(height[current], height[stack.last()]) - height[top]
                totalWater += distance * boundedHeight
            }
            stack.addLast(current)
        }
        return totalWater
    }
}

/**
 * Solution 4: Brute Force (for understanding - TLE on large inputs)
 * Time Complexity: O(n²) - for each element, find left and right max
 * Space Complexity: O(1)
 */
class TrappingRainWaterBruteForce {
    fun trap(height: IntArray): Int {
        var totalWater = 0

        for (i in height.indices) {
            var leftMax = 0
            var rightMax = 0

            for (j in 0..i) {
                leftMax = max(leftMax, height[j])
            }

            for (j in i until height.size) {
                rightMax = max(rightMax, height[j])
            }

            totalWater += min(leftMax, rightMax) - height[i]
        }
        return totalWater
    }
}

/**
 * Solution 5: Monotonic Stack with Detailed Calculation
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
class TrappingRainWaterMonotonicStack {
    fun trap(height: IntArray): Int {
        if (height.size < 3) return 0

        val stack = ArrayDeque<Int>()
        var water = 0

        for (i in height.indices) {
            while (stack.isNotEmpty() && height[i] > height[stack.last()]) {
                val valley = stack.removeLast()

                if (stack.isEmpty()) break

                val leftBound = stack.last()
                val width = i - leftBound - 1
                val minHeight = minOf(height[leftBound], height[i])
                val waterLevel = minHeight - height[valley]

                water += width * waterLevel
            }
            stack.addLast(i)
        }
        return water
    }

    fun trapWithVisualization(height: IntArray): Int {
        if (height.size < 3) return 0

        val stack = ArrayDeque<Int>()
        var water = 0
        val contributions = mutableListOf<String>()

        for (i in height.indices) {
            while (stack.isNotEmpty() && height[i] > height[stack.last()]) {
                val valley = stack.removeLast()

                if (stack.isEmpty()) break

                val leftBound = stack.last()
                val width = i - leftBound - 1
                val minHeight = minOf(height[leftBound], height[i])
                val waterLevel = minHeight - height[valley]
                val contribution = width * waterLevel

                contributions.add(
                    "Valley at $valley: left=$leftBound, right=$i, width=$width, level=$waterLevel, water=$contribution"
                )
                water += contribution
            }
            stack.addLast(i)
        }

        contributions.forEach { println(it) }
        return water
    }
}

fun main() {
    val solutions = mapOf(
        "Two Pointers" to TrappingRainWaterTwoPointers(),
        "DP" to TrappingRainWaterDP(),
        "Stack" to TrappingRainWaterStack(),
        "Brute Force" to TrappingRainWaterBruteForce()
    )

    val testCases = listOf(
        intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1) to 6,
        intArrayOf(4, 2, 0, 3, 2, 5) to 9,
        intArrayOf(1, 2, 3, 4, 5) to 0,
        intArrayOf(5, 4, 3, 2, 1) to 0
    )

    testCases.forEach { (heights, expected) ->
        println("Heights: ${heights.toList()}")
        println("Expected: $expected")
        solutions.forEach { (name, solution) ->
            val result = solution.trap(heights.copyOf())
            val status = if (result == expected) "✓" else "✗"
            println("$status $name: $result")
        }
        println()
    }

    println("=== Visualization ===")
    TrappingRainWaterMonotonicStack().trapWithVisualization(intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1))
}
