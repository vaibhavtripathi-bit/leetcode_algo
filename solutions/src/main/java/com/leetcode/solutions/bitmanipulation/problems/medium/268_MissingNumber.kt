package com.leetcode.solutions.bitmanipulation.problems.medium

/**
 * LeetCode 268: Missing Number
 * https://leetcode.com/problems/missing-number/
 *
 * Difficulty: Easy
 * Companies: All FAANG
 *
 * Array contains n distinct numbers from [0,n]. Return the missing one.
 *
 * Example: nums = [3,0,1] → 2
 */

/** Solution 1: XOR - Time: O(n), Space: O(1) */
class MissingNumber1 {
    fun missingNumber(nums: IntArray): Int {
        var result = nums.size
        for (i in nums.indices) {
            result = result xor i xor nums[i]
        }
        return result
    }
}

/** Solution 2: Gauss Formula - Time: O(n), Space: O(1) */
class MissingNumber2 {
    fun missingNumber(nums: IntArray): Int {
        val n = nums.size
        val expected = n * (n + 1) / 2
        return expected - nums.sum()
    }
}

/** Solution 3: HashSet - Time: O(n), Space: O(n) */
class MissingNumber3 {
    fun missingNumber(nums: IntArray): Int {
        val set = nums.toHashSet()
        return (0..nums.size).first { it !in set }
    }
}
