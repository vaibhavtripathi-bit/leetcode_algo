package com.leetcode.solutions.arrays.problems.medium

/**
 * LeetCode 303: Range Sum Query - Immutable
 * https://leetcode.com/problems/range-sum-query-immutable/
 *
 * Difficulty: Easy
 * Companies: Amazon, Google
 *
 * Given array, efficiently answer multiple range sum queries.
 * Each query: return sum of nums[left..right] inclusive.
 */

/** Solution 1: Prefix Sum Array - Build O(n), Query O(1) */
class NumArray(nums: IntArray) {
    private val prefix = IntArray(nums.size + 1)

    init {
        for (i in nums.indices) prefix[i + 1] = prefix[i] + nums[i]
    }

    fun sumRange(left: Int, right: Int): Int = prefix[right + 1] - prefix[left]
}
