package com.leetcode.solutions.dp.problems.medium

/**
 * LeetCode 198: House Robber
 * https://leetcode.com/problems/house-robber/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Rob houses without robbing adjacent ones. Return max money.
 *
 * Example: nums = [1,2,3,1] → 4 (rob house 1 & 3)
 */

/** Solution 1: Space-Optimized DP - Time: O(n), Space: O(1) */
class HouseRobber1 {
    fun rob(nums: IntArray): Int {
        if (nums.size == 1) return nums[0]

        var prev2 = nums[0]
        var prev1 = maxOf(nums[0], nums[1])

        for (i in 2 until nums.size) {
            val current = maxOf(prev1, prev2 + nums[i])
            prev2 = prev1
            prev1 = current
        }

        return prev1
    }
}

/** Solution 2: Tabulation - Time: O(n), Space: O(n) */
class HouseRobber2 {
    fun rob(nums: IntArray): Int {
        if (nums.size == 1) return nums[0]

        val dp = IntArray(nums.size)
        dp[0] = nums[0]
        dp[1] = maxOf(nums[0], nums[1])

        for (i in 2 until nums.size) {
            dp[i] = maxOf(dp[i - 1], dp[i - 2] + nums[i])
        }

        return dp.last()
    }
}

/** Solution 3: Memoization (Top-Down) - Time: O(n), Space: O(n) */
class HouseRobber3 {
    fun rob(nums: IntArray): Int {
        val memo = HashMap<Int, Int>()
        return robFrom(nums, nums.lastIndex, memo)
    }

    private fun robFrom(nums: IntArray, i: Int, memo: HashMap<Int, Int>): Int {
        if (i < 0) return 0
        return memo.getOrPut(i) {
            maxOf(robFrom(nums, i - 1, memo), robFrom(nums, i - 2, memo) + nums[i])
        }
    }
}
