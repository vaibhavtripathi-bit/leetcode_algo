package com.leetcode.solutions.bitmanipulation.problems.easy

/**
 * LeetCode 136: Single Number
 * https://leetcode.com/problems/single-number/
 *
 * Difficulty: Easy
 * Companies: All FAANG
 *
 * Every element appears twice except one. Find it in O(n) time O(1) space.
 *
 * Example: nums = [4,1,2,1,2] → 4
 */

/** Solution 1: XOR - Time: O(n), Space: O(1) */
class SingleNumber1 {
    fun singleNumber(nums: IntArray): Int = nums.fold(0) { acc, n -> acc xor n }
}

/** Solution 2: HashSet - Time: O(n), Space: O(n) */
class SingleNumber2 {
    fun singleNumber(nums: IntArray): Int {
        val seen = HashSet<Int>()
        for (n in nums) {
            if (!seen.add(n)) seen.remove(n)
        }
        return seen.first()
    }
}

/** Solution 3: Math - 2*(sum of set) - (sum of all) - Time: O(n), Space: O(n) */
class SingleNumber3 {
    fun singleNumber(nums: IntArray): Int {
        val uniqueSum = nums.toSet().sum()
        val totalSum = nums.sum()
        return 2 * uniqueSum - totalSum
    }
}
