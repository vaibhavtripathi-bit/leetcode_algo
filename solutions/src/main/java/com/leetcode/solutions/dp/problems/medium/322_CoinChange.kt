package com.leetcode.solutions.dp.problems.medium

/**
 * LeetCode 322: Coin Change
 * https://leetcode.com/problems/coin-change/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Find minimum number of coins to make the amount. Return -1 if impossible.
 *
 * Example: coins = [1,5,6,9], amount = 11 → 2 (5+6)
 */

/** Solution 1: Tabulation (Bottom-Up) - Time: O(amount * coins), Space: O(amount) */
class CoinChange1 {
    fun coinChange(coins: IntArray, amount: Int): Int {
        val dp = IntArray(amount + 1) { amount + 1 }
        dp[0] = 0

        for (i in 1..amount) {
            dp[i] = findMinCoins(coins, dp, i)
        }

        return if (dp[amount] > amount) -1 else dp[amount]
    }

    private fun findMinCoins(coins: IntArray, dp: IntArray, amount: Int): Int {
        return coins
            .filter { it <= amount }
            .minOfOrNull { dp[amount - it] + 1 } ?: (amount + 1)
    }
}

/** Solution 2: Top-Down Memoization - Time: O(amount * coins), Space: O(amount) */
class CoinChange2 {
    private val memo = HashMap<Int, Int>()

    fun coinChange(coins: IntArray, amount: Int): Int {
        return solve(coins, amount)
    }

    private fun solve(coins: IntArray, remaining: Int): Int {
        if (remaining == 0) return 0
        if (remaining < 0) return -1

        return memo.getOrPut(remaining) {
            val minCoins = coins
                .map { solve(coins, remaining - it) }
                .filter { it >= 0 }
                .minOrNull()

            if (minCoins == null) -1 else minCoins + 1
        }
    }
}

/** Solution 3: BFS (Shortest Path) - Time: O(amount * coins), Space: O(amount) */
class CoinChange3 {
    fun coinChange(coins: IntArray, amount: Int): Int {
        if (amount == 0) return 0

        val visited = BooleanArray(amount + 1)
        val queue = ArrayDeque<Int>()
        queue.addLast(0)
        visited[0] = true
        var steps = 0

        while (queue.isNotEmpty()) {
            steps++
            repeat(queue.size) {
                val current = queue.removeFirst()
                for (coin in coins) {
                    val next = current + coin
                    if (next == amount) return steps
                    if (next < amount && !visited[next]) {
                        visited[next] = true
                        queue.addLast(next)
                    }
                }
            }
        }

        return -1
    }
}
