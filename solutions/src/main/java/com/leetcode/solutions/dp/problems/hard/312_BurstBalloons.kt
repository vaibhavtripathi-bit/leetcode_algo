package com.leetcode.solutions.dp.problems.hard

/**
 * LeetCode 312: Burst Balloons
 * https://leetcode.com/problems/burst-balloons/
 *
 * Difficulty: Hard
 * Companies: Google, Amazon
 *
 * Burst balloons to maximize coins. Bursting balloon i gives nums[i-1]*nums[i]*nums[i+1].
 *
 * Key Insight: Instead of "which to burst first", think "which to burst LAST" in a range.
 * dp[i][j] = max coins from bursting all balloons between i and j (exclusive)
 *
 * Example: nums = [3,1,5,8] → 167
 */

/**
 * Solution 1: Interval DP - Time: O(n³), Space: O(n²)
 *
 * Think of it as: for range (i,j), choose k as the LAST balloon to burst.
 * Left subproblem (i,k) + right subproblem (k,j) + cost of bursting k last.
 */
class BurstBalloons1 {
    fun maxCoins(nums: IntArray): Int {
        val padded = buildPaddedArray(nums)
        val n = padded.size
        val dp = Array(n) { IntArray(n) }

        for (length in 2 until n) {
            for (left in 0 until n - length) {
                val right = left + length
                dp[left][right] = findBestLastBalloon(padded, dp, left, right)
            }
        }

        return dp[0][n - 1]
    }

    private fun buildPaddedArray(nums: IntArray): IntArray {
        return intArrayOf(1) + nums + intArrayOf(1)
    }

    private fun findBestLastBalloon(
        padded: IntArray, dp: Array<IntArray>, left: Int, right: Int
    ): Int {
        var best = 0

        for (k in left + 1 until right) {
            val coins = padded[left] * padded[k] * padded[right]
            val total = dp[left][k] + coins + dp[k][right]
            best = maxOf(best, total)
        }

        return best
    }
}

/** Solution 2: Memoization Top-Down */
class BurstBalloons2 {
    fun maxCoins(nums: IntArray): Int {
        val padded = intArrayOf(1) + nums + intArrayOf(1)
        val memo = HashMap<Pair<Int, Int>, Int>()
        return solve(padded, 0, padded.lastIndex, memo)
    }

    private fun solve(p: IntArray, l: Int, r: Int, memo: HashMap<Pair<Int, Int>, Int>): Int {
        if (r - l < 2) return 0

        return memo.getOrPut(l to r) {
            var best = 0
            for (k in l + 1 until r) {
                val gain = p[l] * p[k] * p[r]
                best = maxOf(best, gain + solve(p, l, k, memo) + solve(p, k, r, memo))
            }
            best
        }
    }
}
