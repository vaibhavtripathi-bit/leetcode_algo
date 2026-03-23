package com.leetcode.solutions.dp.problems.medium

/**
 * LeetCode 416: Partition Equal Subset Sum
 * https://leetcode.com/problems/partition-equal-subset-sum/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Meta
 *
 * Can array be partitioned into two subsets with equal sum?
 * → Classic 0/1 Knapsack: can we form sum = total/2 using array elements?
 *
 * Example: nums = [1,5,11,5] → true ([1,5,5] and [11])
 */

/** Solution 1: 1D DP (Knapsack) - Time: O(n * sum), Space: O(sum) */
class PartitionEqualSubset1 {
    fun canPartition(nums: IntArray): Boolean {
        val total = nums.sum()
        if (total % 2 != 0) return false

        val target = total / 2
        val dp = BooleanArray(target + 1)
        dp[0] = true

        for (num in nums) {
            for (j in target downTo num) {
                dp[j] = dp[j] || dp[j - num]
            }
        }

        return dp[target]
    }
}

/** Solution 2: 2D DP - Time: O(n * sum), Space: O(n * sum) */
class PartitionEqualSubset2 {
    fun canPartition(nums: IntArray): Boolean {
        val total = nums.sum()
        if (total % 2 != 0) return false

        val target = total / 2
        val n = nums.size
        val dp = Array(n + 1) { BooleanArray(target + 1) }
        for (i in 0..n) dp[i][0] = true

        for (i in 1..n) {
            for (j in 1..target) {
                dp[i][j] = dp[i - 1][j]
                if (nums[i - 1] <= j) dp[i][j] = dp[i][j] || dp[i - 1][j - nums[i - 1]]
            }
        }

        return dp[n][target]
    }
}

/** Solution 3: BitSet optimization - Time: O(n * sum / 64), Space: O(sum) */
class PartitionEqualSubset3 {
    fun canPartition(nums: IntArray): Boolean {
        val total = nums.sum()
        if (total % 2 != 0) return false

        val target = total / 2
        var bits = java.util.BitSet(target + 1)
        bits.set(0)

        for (num in nums) {
            val shifted = bits.clone() as java.util.BitSet
            for (i in bits.nextSetBit(0); i >= 0; i = bits.nextSetBit(i + 1)) {
                if (i + num <= target) shifted.set(i + num)
            }
            bits = shifted
        }

        return bits.get(target)
    }
}
