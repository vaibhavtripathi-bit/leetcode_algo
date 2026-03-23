package com.leetcode.solutions.arrays.problems.easy

/**
 * LeetCode 53: Maximum Subarray (Kadane's Algorithm)
 * https://leetcode.com/problems/maximum-subarray/
 *
 * Difficulty: Easy (Medium conceptually)
 * Companies: All FAANG
 *
 * Find the subarray with the largest sum.
 *
 * Example: nums = [-2,1,-3,4,-1,2,1,-5,4] → 6 ([4,-1,2,1])
 */

/**
 * Solution 1: Kadane's Algorithm - Time: O(n), Space: O(1)
 *
 * Core idea: At each position, decide: extend current subarray OR start fresh.
 * Extend if current subarray sum is positive; start fresh if it went negative.
 */
class MaxSubarray1 {
    fun maxSubArray(nums: IntArray): Int {
        var currentSum = nums[0]
        var maxSum = nums[0]

        for (i in 1 until nums.size) {
            currentSum = maxOf(nums[i], currentSum + nums[i])
            maxSum = maxOf(maxSum, currentSum)
        }

        return maxSum
    }
}

/** Solution 2: DP (explicit) - Time: O(n), Space: O(n) */
class MaxSubarray2 {
    fun maxSubArray(nums: IntArray): Int {
        val dp = IntArray(nums.size)
        dp[0] = nums[0]

        for (i in 1 until nums.size) {
            dp[i] = maxOf(nums[i], dp[i - 1] + nums[i])
        }

        return dp.max()!!
    }
}

/** Solution 3: Divide and Conquer - Time: O(n log n), Space: O(log n) */
class MaxSubarray3 {
    fun maxSubArray(nums: IntArray): Int = solve(nums, 0, nums.lastIndex)

    private fun solve(nums: IntArray, left: Int, right: Int): Int {
        if (left == right) return nums[left]

        val mid = left + (right - left) / 2
        val leftMax  = solve(nums, left, mid)
        val rightMax = solve(nums, mid + 1, right)
        val crossMax = crossMax(nums, left, mid, right)

        return maxOf(leftMax, rightMax, crossMax)
    }

    private fun crossMax(nums: IntArray, left: Int, mid: Int, right: Int): Int {
        var leftSum = Int.MIN_VALUE
        var sum = 0
        for (i in mid downTo left)  { sum += nums[i]; leftSum  = maxOf(leftSum, sum) }

        var rightSum = Int.MIN_VALUE
        sum = 0
        for (i in mid + 1..right)   { sum += nums[i]; rightSum = maxOf(rightSum, sum) }

        return leftSum + rightSum
    }
}
