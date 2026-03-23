package com.leetcode.solutions.dp.problems.medium

/**
 * LeetCode 518: Coin Change II
 * https://leetcode.com/problems/coin-change-ii/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Meta
 *
 * Return the number of ways to make amount using coins (unlimited use).
 *
 * Example: amount = 5, coins = [1,2,5] → 4
 */

/** Solution 1: Unbounded Knapsack DP - Time: O(amount * coins), Space: O(amount) */
class CoinChangeII1 {
    fun change(amount: Int, coins: IntArray): Int {
        val dp = IntArray(amount + 1)
        dp[0] = 1

        for (coin in coins) {
            for (j in coin..amount) {
                dp[j] += dp[j - coin]
            }
        }

        return dp[amount]
    }
}

/** Solution 2: 2D DP - Time: O(n * amount), Space: O(n * amount) */
class CoinChangeII2 {
    fun change(amount: Int, coins: IntArray): Int {
        val n = coins.size
        val dp = Array(n + 1) { IntArray(amount + 1) }
        for (i in 0..n) dp[i][0] = 1

        for (i in 1..n) {
            for (j in 0..amount) {
                dp[i][j] = dp[i - 1][j]
                if (coins[i - 1] <= j) dp[i][j] += dp[i][j - coins[i - 1]]
            }
        }

        return dp[n][amount]
    }
}
