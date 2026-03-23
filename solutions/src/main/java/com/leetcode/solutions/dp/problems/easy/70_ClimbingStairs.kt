package com.leetcode.solutions.dp.problems.easy

/**
 * LeetCode 70: Climbing Stairs
 * https://leetcode.com/problems/climbing-stairs/
 *
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Each time you can climb 1 or 2 steps. How many distinct ways to reach top?
 *
 * Example: n = 3 → 3 (1+1+1, 1+2, 2+1)
 */

/** Solution 1: Tabulation (Bottom-Up DP) - Time: O(n), Space: O(n) */
class ClimbingStairs1 {
    fun climbStairs(n: Int): Int {
        if (n <= 2) return n

        val dp = IntArray(n + 1)
        dp[1] = 1
        dp[2] = 2

        for (i in 3..n) {
            dp[i] = dp[i - 1] + dp[i - 2]
        }

        return dp[n]
    }
}

/** Solution 2: Space Optimized (Fibonacci) - Time: O(n), Space: O(1) */
class ClimbingStairs2 {
    fun climbStairs(n: Int): Int {
        if (n <= 2) return n

        var prev2 = 1
        var prev1 = 2

        for (i in 3..n) {
            val current = prev1 + prev2
            prev2 = prev1
            prev1 = current
        }

        return prev1
    }
}

/** Solution 3: Top-Down with Memoization - Time: O(n), Space: O(n) */
class ClimbingStairs3 {
    private val memo = HashMap<Int, Int>()

    fun climbStairs(n: Int): Int {
        if (n <= 2) return n
        return memo.getOrPut(n) { climbStairs(n - 1) + climbStairs(n - 2) }
    }
}
