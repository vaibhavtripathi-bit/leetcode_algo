package com.leetcode.solutions.dp.problems.medium

/**
 * LeetCode 300: Longest Increasing Subsequence
 * https://leetcode.com/problems/longest-increasing-subsequence/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Return the length of the longest strictly increasing subsequence.
 *
 * Example: nums = [10,9,2,5,3,7,101,18] → 4 ([2,3,7,101])
 */

/** Solution 1: DP O(n²) - Time: O(n²), Space: O(n) */
class LIS1 {
    fun lengthOfLIS(nums: IntArray): Int {
        val dp = IntArray(nums.size) { 1 }

        for (i in 1 until nums.size) {
            dp[i] = findMaxLength(nums, dp, i)
        }

        return dp.max()!!
    }

    private fun findMaxLength(nums: IntArray, dp: IntArray, i: Int): Int {
        var best = 1
        for (j in 0 until i) {
            if (nums[j] < nums[i]) {
                best = maxOf(best, dp[j] + 1)
            }
        }
        return best
    }
}

/** Solution 2: Binary Search O(n log n) - Time: O(n log n), Space: O(n) */
class LIS2 {
    fun lengthOfLIS(nums: IntArray): Int {
        val tails = mutableListOf<Int>()

        for (num in nums) {
            val pos = findInsertPosition(tails, num)
            if (pos == tails.size) tails.add(num)
            else tails[pos] = num
        }

        return tails.size
    }

    private fun findInsertPosition(tails: List<Int>, target: Int): Int {
        var left = 0
        var right = tails.size

        while (left < right) {
            val mid = left + (right - left) / 2
            if (tails[mid] < target) left = mid + 1
            else right = mid
        }

        return left
    }
}

/** Solution 3: Memoization - Time: O(n²), Space: O(n) */
class LIS3 {
    fun lengthOfLIS(nums: IntArray): Int {
        val memo = HashMap<Int, Int>()

        return (nums.indices).maxOf { i ->
            dfs(nums, i, memo)
        }
    }

    private fun dfs(nums: IntArray, i: Int, memo: HashMap<Int, Int>): Int {
        return memo.getOrPut(i) {
            var best = 1
            for (j in 0 until i) {
                if (nums[j] < nums[i]) {
                    best = maxOf(best, dfs(nums, j, memo) + 1)
                }
            }
            best
        }
    }
}
