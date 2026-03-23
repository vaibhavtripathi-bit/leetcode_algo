package com.leetcode.solutions.arrays.problems.hard

/**
 * LeetCode 41: First Missing Positive
 * https://leetcode.com/problems/first-missing-positive/
 *
 * Difficulty: Hard
 * Companies: All FAANG
 *
 * Find the smallest missing positive integer in O(n) time and O(1) space.
 *
 * Example: nums = [3,4,-1,1] → 2
 * Example: nums = [1,2,0] → 3
 */

/**
 * Solution 1: Index as Hash (Cyclic Sort idea) - Time: O(n), Space: O(1)
 *
 * Key insight: Answer must be in [1, n+1]. So use the array itself as a hash map.
 * Place each num in its "correct" position: num=1 → index 0, num=2 → index 1...
 * Then scan for first position where nums[i] != i+1.
 */
class FirstMissingPositive1 {
    fun firstMissingPositive(nums: IntArray): Int {
        placeEachNumberInCorrectPosition(nums)
        return findFirstMissingPosition(nums)
    }

    private fun placeEachNumberInCorrectPosition(nums: IntArray) {
        for (i in nums.indices) {
            while (isInRange(nums[i], nums.size) && nums[nums[i] - 1] != nums[i]) {
                swap(nums, i, nums[i] - 1)
            }
        }
    }

    private fun findFirstMissingPosition(nums: IntArray): Int {
        for (i in nums.indices) {
            if (nums[i] != i + 1) return i + 1
        }
        return nums.size + 1
    }

    private fun isInRange(num: Int, n: Int) = num in 1..n

    private fun swap(nums: IntArray, i: Int, j: Int) {
        val temp = nums[i]; nums[i] = nums[j]; nums[j] = temp
    }
}

/** Solution 2: Mark with negatives - Time: O(n), Space: O(1) */
class FirstMissingPositive2 {
    fun firstMissingPositive(nums: IntArray): Int {
        val n = nums.size

        cleanupNonPositives(nums, n)
        markPresent(nums, n)
        return findFirstUnmarked(nums, n)
    }

    private fun cleanupNonPositives(nums: IntArray, n: Int) {
        for (i in nums.indices) { if (nums[i] <= 0) nums[i] = n + 1 }
    }

    private fun markPresent(nums: IntArray, n: Int) {
        for (i in nums.indices) {
            val num = Math.abs(nums[i])
            if (num <= n) nums[num - 1] = -Math.abs(nums[num - 1])
        }
    }

    private fun findFirstUnmarked(nums: IntArray, n: Int): Int {
        for (i in nums.indices) { if (nums[i] > 0) return i + 1 }
        return n + 1
    }
}
