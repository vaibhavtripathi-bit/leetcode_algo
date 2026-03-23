package com.leetcode.solutions.arrays.problems.medium

/**
 * LeetCode 31: Next Permutation
 * https://leetcode.com/problems/next-permutation/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft
 *
 * Find the next lexicographically greater permutation in-place.
 * If none exists, rearrange to the lowest possible order (sorted ascending).
 *
 * Example: [1,2,3] → [1,3,2]
 * Example: [3,2,1] → [1,2,3]
 */

/**
 * Solution 1: Three Steps - Time: O(n), Space: O(1)
 *
 * Step 1: Find rightmost index i where nums[i] < nums[i+1] (pivot)
 * Step 2: Find rightmost index j where nums[j] > nums[i], swap them
 * Step 3: Reverse the suffix after position i
 */
class NextPermutation1 {
    fun nextPermutation(nums: IntArray) {
        val pivot = findPivot(nums)

        if (pivot >= 0) {
            val swapTarget = findSwapTarget(nums, pivot)
            swap(nums, pivot, swapTarget)
        }

        reverse(nums, pivot + 1, nums.lastIndex)
    }

    private fun findPivot(nums: IntArray): Int {
        for (i in nums.lastIndex - 1 downTo 0) {
            if (nums[i] < nums[i + 1]) return i
        }
        return -1
    }

    private fun findSwapTarget(nums: IntArray, pivot: Int): Int {
        for (i in nums.lastIndex downTo pivot + 1) {
            if (nums[i] > nums[pivot]) return i
        }
        return -1
    }

    private fun swap(nums: IntArray, i: Int, j: Int) {
        val temp = nums[i]; nums[i] = nums[j]; nums[j] = temp
    }

    private fun reverse(nums: IntArray, start: Int, end: Int) {
        var l = start; var r = end
        while (l < r) { swap(nums, l++, r--) }
    }
}
