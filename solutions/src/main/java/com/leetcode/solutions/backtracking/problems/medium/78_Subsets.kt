package com.leetcode.solutions.backtracking.problems.medium

/**
 * LeetCode 78: Subsets
 * https://leetcode.com/problems/subsets/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Return all possible subsets (power set). Solution set must not contain duplicates.
 *
 * Example: nums = [1,2,3] → [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 */

/** Solution 1: Backtracking - Time: O(2ⁿ * n), Space: O(n) */
class Subsets1 {
    fun subsets(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        backtrack(nums, 0, mutableListOf(), result)
        return result
    }

    private fun backtrack(
        nums: IntArray, start: Int,
        current: MutableList<Int>, result: MutableList<List<Int>>
    ) {
        result.add(current.toList())

        for (i in start until nums.size) {
            current.add(nums[i])
            backtrack(nums, i + 1, current, result)
            current.removeLast()
        }
    }
}

/** Solution 2: Bit Manipulation - Time: O(2ⁿ * n), Space: O(1) */
class Subsets2 {
    fun subsets(nums: IntArray): List<List<Int>> {
        val n = nums.size
        val total = 1 shl n

        return (0 until total).map { mask ->
            buildSubset(nums, mask)
        }
    }

    private fun buildSubset(nums: IntArray, mask: Int): List<Int> {
        return nums.indices.filter { i -> mask and (1 shl i) != 0 }.map { nums[it] }
    }
}

/** Solution 3: Iterative - Time: O(2ⁿ * n), Space: O(2ⁿ * n) */
class Subsets3 {
    fun subsets(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>(emptyList())

        for (num in nums) {
            val newSubsets = result.map { it + num }
            result.addAll(newSubsets)
        }

        return result
    }
}
