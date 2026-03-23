package com.leetcode.solutions.dp.problems.medium

/**
 * LeetCode 62: Unique Paths
 * https://leetcode.com/problems/unique-paths/
 *
 * Difficulty: Medium
 * Companies: All FAANG
 *
 * Count distinct paths from top-left to bottom-right of m×n grid.
 * Only move right or down.
 *
 * Example: m = 3, n = 7 → 28
 */

/** Solution 1: 2D DP - Time: O(m*n), Space: O(m*n) */
class UniquePaths1 {
    fun uniquePaths(m: Int, n: Int): Int {
        val dp = Array(m) { IntArray(n) { 1 } }

        for (r in 1 until m) {
            for (c in 1 until n) {
                dp[r][c] = dp[r - 1][c] + dp[r][c - 1]
            }
        }

        return dp[m - 1][n - 1]
    }
}

/** Solution 2: 1D DP (rolling row) - Time: O(m*n), Space: O(n) */
class UniquePaths2 {
    fun uniquePaths(m: Int, n: Int): Int {
        var dp = IntArray(n) { 1 }

        repeat(m - 1) {
            val newRow = IntArray(n) { 1 }
            for (c in 1 until n) newRow[c] = newRow[c - 1] + dp[c]
            dp = newRow
        }

        return dp[n - 1]
    }
}

/** Solution 3: Combinatorics - Time: O(min(m,n)), Space: O(1) */
class UniquePaths3 {
    fun uniquePaths(m: Int, n: Int): Int {
        val totalSteps = m + n - 2
        val downSteps = m - 1
        var result = 1L

        for (i in 1..downSteps) {
            result = result * (totalSteps - downSteps + i) / i
        }

        return result.toInt()
    }
}
