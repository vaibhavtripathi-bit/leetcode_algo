package com.leetcode.solutions.twopointers.problems.medium

/**
 * 15. 3Sum
 * https://leetcode.com/problems/3sum/
 *
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
 * such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 *
 * Companies: Facebook, Amazon, Google, Microsoft, Apple, Bloomberg, Adobe, Uber
 */

/**
 * Solution 1: Sort + Two Pointers (Optimal)
 * Time Complexity: O(n²) - sorting O(n log n) + two-pointer search O(n²)
 * Space Complexity: O(1) or O(n) depending on sorting implementation
 */
class ThreeSumTwoPointers {
    fun threeSum(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        nums.sort()

        for (i in 0 until nums.size - 2) {
            if (i > 0 && nums[i] == nums[i - 1]) continue

            if (nums[i] > 0) break

            var left = i + 1
            var right = nums.size - 1

            while (left < right) {
                val sum = nums[i] + nums[left] + nums[right]
                when {
                    sum == 0 -> {
                        result.add(listOf(nums[i], nums[left], nums[right]))
                        while (left < right && nums[left] == nums[left + 1]) left++
                        while (left < right && nums[right] == nums[right - 1]) right--
                        left++
                        right--
                    }
                    sum < 0 -> left++
                    else -> right--
                }
            }
        }
        return result
    }
}

/**
 * Solution 2: HashSet Approach
 * Time Complexity: O(n²) - iterating through pairs
 * Space Complexity: O(n) - for HashSet storage
 */
class ThreeSumHashSet {
    fun threeSum(nums: IntArray): List<List<Int>> {
        val result = mutableSetOf<List<Int>>()
        nums.sort()

        for (i in 0 until nums.size - 2) {
            if (i > 0 && nums[i] == nums[i - 1]) continue

            val seen = mutableSetOf<Int>()
            for (j in i + 1 until nums.size) {
                val complement = -nums[i] - nums[j]
                if (complement in seen) {
                    result.add(listOf(nums[i], complement, nums[j]))
                }
                seen.add(nums[j])
            }
        }
        return result.toList()
    }
}

/**
 * Solution 3: No-Sort HashSet (Higher complexity but educational)
 * Time Complexity: O(n²)
 * Space Complexity: O(n)
 */
class ThreeSumNoSort {
    fun threeSum(nums: IntArray): List<List<Int>> {
        val result = mutableSetOf<List<Int>>()
        val duplicates = mutableSetOf<Int>()
        val seen = mutableMapOf<Int, Int>()

        for (i in nums.indices) {
            if (duplicates.add(nums[i])) {
                for (j in i + 1 until nums.size) {
                    val complement = -nums[i] - nums[j]
                    if (seen[complement] == i) {
                        val triplet = listOf(nums[i], nums[j], complement).sorted()
                        result.add(triplet)
                    }
                    seen[nums[j]] = i
                }
            }
        }
        return result.toList()
    }
}

/**
 * Solution 4: Optimized Two Pointers with Early Termination
 * Time Complexity: O(n²)
 * Space Complexity: O(1) excluding result
 */
class ThreeSumOptimized {
    fun threeSum(nums: IntArray): List<List<Int>> {
        if (nums.size < 3) return emptyList()

        val result = mutableListOf<List<Int>>()
        nums.sort()

        for (i in 0 until nums.size - 2) {
            if (nums[i] > 0) break

            if (i > 0 && nums[i] == nums[i - 1]) continue

            if (nums[i] + nums[i + 1] + nums[i + 2] > 0) break
            if (nums[i] + nums[nums.size - 2] + nums[nums.size - 1] < 0) continue

            var left = i + 1
            var right = nums.size - 1

            while (left < right) {
                val sum = nums[i] + nums[left] + nums[right]
                when {
                    sum == 0 -> {
                        result.add(listOf(nums[i], nums[left], nums[right]))
                        left++
                        right--
                        while (left < right && nums[left] == nums[left - 1]) left++
                        while (left < right && nums[right] == nums[right + 1]) right--
                    }
                    sum < 0 -> left++
                    else -> right--
                }
            }
        }
        return result
    }
}

fun main() {
    val solutions = listOf(
        ThreeSumTwoPointers(),
        ThreeSumHashSet(),
        ThreeSumNoSort(),
        ThreeSumOptimized()
    )

    val testCases = listOf(
        intArrayOf(-1, 0, 1, 2, -1, -4),
        intArrayOf(0, 1, 1),
        intArrayOf(0, 0, 0)
    )

    testCases.forEach { input ->
        println("Input: ${input.toList()}")
        println("Result: ${solutions[0].threeSum(input.copyOf())}")
        println()
    }
}
