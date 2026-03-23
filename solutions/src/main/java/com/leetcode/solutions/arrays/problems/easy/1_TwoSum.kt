package com.leetcode.solutions.arrays.problems.easy

/**
 * LeetCode 1: Two Sum
 * https://leetcode.com/problems/two-sum/
 *
 * Difficulty: Easy
 * Companies: All FAANG — most asked interview question of all time
 *
 * Return indices of two numbers that add up to target.
 *
 * Example: nums = [2,7,11,15], target = 9 → [0,1]
 */

/** Solution 1: HashMap (One Pass) - Time: O(n), Space: O(n) */
class TwoSum1 {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val seen = HashMap<Int, Int>()

        for (i in nums.indices) {
            val complement = target - nums[i]
            if (complement in seen) return intArrayOf(seen[complement]!!, i)
            seen[nums[i]] = i
        }

        return intArrayOf()
    }
}

/** Solution 2: HashMap (Two Pass) - Time: O(n), Space: O(n) */
class TwoSum2 {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val indexMap = nums.withIndex().associate { (i, v) -> v to i }

        for (i in nums.indices) {
            val complement = target - nums[i]
            val j = indexMap[complement]
            if (j != null && j != i) return intArrayOf(i, j)
        }

        return intArrayOf()
    }
}

/** Solution 3: Sort + Two Pointers (if indices not needed) - Time: O(n log n), Space: O(n) */
class TwoSum3 {
    fun twoSum(nums: IntArray, target: Int): IntArray {
        val sorted = nums.mapIndexed { i, v -> v to i }.sortedBy { it.first }
        var left = 0
        var right = sorted.lastIndex

        while (left < right) {
            val sum = sorted[left].first + sorted[right].first
            when {
                sum == target -> return intArrayOf(sorted[left].second, sorted[right].second)
                sum < target  -> left++
                else          -> right--
            }
        }

        return intArrayOf()
    }
}
