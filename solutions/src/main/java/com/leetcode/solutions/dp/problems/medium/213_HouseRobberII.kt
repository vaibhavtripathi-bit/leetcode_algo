package com.leetcode.solutions.dp.problems.medium

/**
 * LeetCode 213: House Robber II
 * https://leetcode.com/problems/house-robber-ii/
 *
 * Difficulty: Medium
 * Companies: All FAANG
 *
 * Houses are in a circle — first and last are adjacent.
 * Return max money without robbing adjacent houses.
 *
 * Key: Split into two linear problems: [0..n-2] and [1..n-1]
 */

/** Solution 1: Two-Range Approach - Time: O(n), Space: O(1) */
class HouseRobberII1 {
    fun rob(nums: IntArray): Int {
        if (nums.size == 1) return nums[0]
        if (nums.size == 2) return maxOf(nums[0], nums[1])

        return maxOf(
            robLinear(nums, 0, nums.lastIndex - 1),
            robLinear(nums, 1, nums.lastIndex)
        )
    }

    private fun robLinear(nums: IntArray, start: Int, end: Int): Int {
        var prev2 = nums[start]
        var prev1 = maxOf(nums[start], nums[start + 1])

        for (i in start + 2..end) {
            val current = maxOf(prev1, prev2 + nums[i])
            prev2 = prev1
            prev1 = current
        }

        return prev1
    }
}
