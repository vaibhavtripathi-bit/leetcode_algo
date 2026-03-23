package com.leetcode.solutions.dp.problems.medium

/**
 * LeetCode 152: Maximum Product Subarray
 * https://leetcode.com/problems/maximum-product-subarray/
 *
 * Difficulty: Medium
 * Companies: All FAANG
 *
 * Find the subarray with the largest product.
 *
 * Key: Track both max AND min (negative * negative = positive)
 *
 * Example: nums = [2,3,-2,4] → 6 ([2,3])
 */

/** Solution 1: Track Max and Min - Time: O(n), Space: O(1) */
class MaxProduct1 {
    fun maxProduct(nums: IntArray): Int {
        var maxSoFar = nums[0]
        var maxEndingHere = nums[0]
        var minEndingHere = nums[0]

        for (i in 1 until nums.size) {
            val candidates = triple(nums[i], maxEndingHere * nums[i], minEndingHere * nums[i])
            maxEndingHere = candidates.max()!!
            minEndingHere = candidates.min()!!
            maxSoFar = maxOf(maxSoFar, maxEndingHere)
        }

        return maxSoFar
    }

    private fun triple(a: Int, b: Int, c: Int) = intArrayOf(a, b, c)
}

/** Solution 2: Prefix & Suffix products - Time: O(n), Space: O(1) */
class MaxProduct2 {
    fun maxProduct(nums: IntArray): Int {
        var prefix = 1; var suffix = 1; var max = Int.MIN_VALUE

        for (i in nums.indices) {
            prefix = if (prefix == 0) nums[i] else prefix * nums[i]
            suffix = if (suffix == 0) nums[nums.lastIndex - i] else suffix * nums[nums.lastIndex - i]
            max = maxOf(max, prefix, suffix)
        }

        return max
    }
}
