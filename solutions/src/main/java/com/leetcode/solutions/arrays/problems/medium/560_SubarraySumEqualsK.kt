package com.leetcode.solutions.arrays.problems.medium

/**
 * LeetCode 560: Subarray Sum Equals K
 * https://leetcode.com/problems/subarray-sum-equals-k/
 *
 * Difficulty: Medium
 * Companies: All FAANG — extremely frequent
 *
 * Return the total number of subarrays whose sum equals k.
 *
 * Example: nums = [1,1,1], k = 2 → 2
 */

/**
 * Solution 1: Prefix Sum + HashMap - Time: O(n), Space: O(n)
 *
 * If prefix[j] - prefix[i] = k, then nums[i+1..j] sums to k.
 * For each j, find how many prefix sums equal (prefix[j] - k).
 */
class SubarraySum1 {
    fun subarraySum(nums: IntArray, k: Int): Int {
        val prefixCount = HashMap<Int, Int>()
        prefixCount[0] = 1

        var runningSum = 0
        var count = 0

        for (num in nums) {
            runningSum += num
            count += prefixCount.getOrDefault(runningSum - k, 0)
            prefixCount[runningSum] = prefixCount.getOrDefault(runningSum, 0) + 1
        }

        return count
    }
}

/** Solution 2: Brute Force O(n²) — only for understanding */
class SubarraySum2 {
    fun subarraySum(nums: IntArray, k: Int): Int {
        var count = 0

        for (i in nums.indices) {
            var sum = 0
            for (j in i until nums.size) {
                sum += nums[j]
                if (sum == k) count++
            }
        }

        return count
    }
}
