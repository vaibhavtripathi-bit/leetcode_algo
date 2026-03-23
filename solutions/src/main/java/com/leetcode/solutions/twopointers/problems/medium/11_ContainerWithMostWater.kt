package com.leetcode.solutions.twopointers.problems.medium

import kotlin.math.max
import kotlin.math.min

/**
 * 11. Container With Most Water
 * https://leetcode.com/problems/container-with-most-water/
 *
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai).
 * n vertical lines are drawn such that the two endpoints of the line i is at (i, ai) and (i, 0).
 * Find two lines, which, together with the x-axis forms a container, such that the container
 * contains the most water.
 *
 * Companies: Amazon, Facebook, Google, Microsoft, Apple, Goldman Sachs, Bloomberg
 */

/**
 * Solution 1: Two Pointers (Optimal)
 * Time Complexity: O(n) - single pass with two pointers
 * Space Complexity: O(1) - constant extra space
 *
 * Key insight: Always move the pointer pointing to the shorter line inward,
 * because moving the taller one can only decrease the area.
 */
class ContainerWithMostWaterTwoPointers {
    fun maxArea(height: IntArray): Int {
        var left = 0
        var right = height.size - 1
        var maxArea = 0

        while (left < right) {
            val width = right - left
            val currentHeight = min(height[left], height[right])
            val area = width * currentHeight
            maxArea = max(maxArea, area)

            if (height[left] < height[right]) {
                left++
            } else {
                right--
            }
        }
        return maxArea
    }
}

/**
 * Solution 2: Brute Force (for comparison - TLE on large inputs)
 * Time Complexity: O(n²) - checking all pairs
 * Space Complexity: O(1)
 */
class ContainerWithMostWaterBruteForce {
    fun maxArea(height: IntArray): Int {
        var maxArea = 0

        for (i in height.indices) {
            for (j in i + 1 until height.size) {
                val width = j - i
                val currentHeight = min(height[i], height[j])
                maxArea = max(maxArea, width * currentHeight)
            }
        }
        return maxArea
    }
}

/**
 * Solution 3: Two Pointers with Skip Optimization
 * Time Complexity: O(n) - with potential early skips
 * Space Complexity: O(1)
 *
 * Optimization: Skip lines that are shorter than the current min height
 * since they cannot form a larger area.
 */
class ContainerWithMostWaterOptimized {
    fun maxArea(height: IntArray): Int {
        var left = 0
        var right = height.size - 1
        var maxArea = 0

        while (left < right) {
            val minHeight = min(height[left], height[right])
            maxArea = max(maxArea, minHeight * (right - left))

            if (height[left] < height[right]) {
                val currentLeft = height[left]
                while (left < right && height[left] <= currentLeft) {
                    left++
                }
            } else {
                val currentRight = height[right]
                while (left < right && height[right] <= currentRight) {
                    right--
                }
            }
        }
        return maxArea
    }
}

/**
 * Solution 4: Kotlin Functional Style Two Pointers
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
class ContainerWithMostWaterFunctional {
    fun maxArea(height: IntArray): Int {
        var left = 0
        var right = height.size - 1

        return generateSequence {
            if (left >= right) null
            else {
                val area = (right - left) * minOf(height[left], height[right])
                if (height[left] < height[right]) left++ else right--
                area
            }
        }.maxOrNull() ?: 0
    }

    fun maxAreaRecursive(height: IntArray): Int {
        return helper(height, 0, height.size - 1, 0)
    }

    private fun helper(height: IntArray, left: Int, right: Int, maxSoFar: Int): Int {
        if (left >= right) return maxSoFar

        val area = (right - left) * minOf(height[left], height[right])
        val newMax = maxOf(maxSoFar, area)

        return if (height[left] < height[right]) {
            helper(height, left + 1, right, newMax)
        } else {
            helper(height, left, right - 1, newMax)
        }
    }
}

fun main() {
    val solutions = listOf(
        ContainerWithMostWaterTwoPointers(),
        ContainerWithMostWaterBruteForce(),
        ContainerWithMostWaterOptimized()
    )

    val testCases = listOf(
        intArrayOf(1, 8, 6, 2, 5, 4, 8, 3, 7) to 49,
        intArrayOf(1, 1) to 1,
        intArrayOf(4, 3, 2, 1, 4) to 16
    )

    testCases.forEach { (heights, expected) ->
        println("Heights: ${heights.toList()}")
        println("Expected: $expected")
        solutions.forEachIndexed { idx, solution ->
            println("Solution ${idx + 1}: ${solution.maxArea(heights.copyOf())}")
        }
        println()
    }
}
