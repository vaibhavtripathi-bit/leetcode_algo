package com.leetcode.solutions.dp.problems.hard

/**
 * LeetCode 72: Edit Distance
 * https://leetcode.com/problems/edit-distance/
 *
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Return the minimum operations (insert, delete, replace) to convert word1 to word2.
 *
 * Example: word1 = "horse", word2 = "ros" → 3
 */

/** Solution 1: 2D DP Table - Time: O(m*n), Space: O(m*n) */
class EditDistance1 {
    fun minDistance(word1: String, word2: String): Int {
        val m = word1.length
        val n = word2.length
        val dp = Array(m + 1) { IntArray(n + 1) }

        initializeBaseCase(dp, m, n)

        for (i in 1..m) {
            for (j in 1..n) {
                dp[i][j] = if (word1[i - 1] == word2[j - 1]) {
                    dp[i - 1][j - 1]
                } else {
                    1 + minOf(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])
                    // delete from w1, insert to w1, replace
                }
            }
        }

        return dp[m][n]
    }

    private fun initializeBaseCase(dp: Array<IntArray>, m: Int, n: Int) {
        for (i in 0..m) dp[i][0] = i  // delete all from word1
        for (j in 0..n) dp[0][j] = j  // insert all from word2
    }
}

/** Solution 2: Space-Optimized DP - Time: O(m*n), Space: O(n) */
class EditDistance2 {
    fun minDistance(word1: String, word2: String): Int {
        val n = word2.length
        var prev = IntArray(n + 1) { it }

        for (i in 1..word1.length) {
            val curr = IntArray(n + 1)
            curr[0] = i

            for (j in 1..n) {
                curr[j] = if (word1[i - 1] == word2[j - 1]) {
                    prev[j - 1]
                } else {
                    1 + minOf(prev[j], curr[j - 1], prev[j - 1])
                }
            }

            prev = curr
        }

        return prev[n]
    }
}

/** Solution 3: Memoization Top-Down - Time: O(m*n), Space: O(m*n) */
class EditDistance3 {
    fun minDistance(word1: String, word2: String): Int {
        val memo = HashMap<Pair<Int, Int>, Int>()
        return solve(word1, word2, word1.length, word2.length, memo)
    }

    private fun solve(w1: String, w2: String, i: Int, j: Int, memo: HashMap<Pair<Int, Int>, Int>): Int {
        if (i == 0) return j
        if (j == 0) return i

        return memo.getOrPut(i to j) {
            if (w1[i - 1] == w2[j - 1]) {
                solve(w1, w2, i - 1, j - 1, memo)
            } else {
                1 + minOf(
                    solve(w1, w2, i - 1, j, memo),
                    solve(w1, w2, i, j - 1, memo),
                    solve(w1, w2, i - 1, j - 1, memo)
                )
            }
        }
    }
}
