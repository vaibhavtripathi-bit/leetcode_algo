package com.leetcode.solutions.dp.problems.medium

/**
 * LeetCode 1143: Longest Common Subsequence
 * https://leetcode.com/problems/longest-common-subsequence/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Return the length of the longest common subsequence of two strings.
 *
 * Example: text1 = "abcde", text2 = "ace" → 3 (ace)
 */

/** Solution 1: 2D DP Table - Time: O(m*n), Space: O(m*n) */
class LCS1 {
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        val dp = Array(text1.length + 1) { IntArray(text2.length + 1) }

        for (i in 1..text1.length) {
            for (j in 1..text2.length) {
                dp[i][j] = if (charsMatch(text1, text2, i, j)) {
                    dp[i - 1][j - 1] + 1
                } else {
                    maxOf(dp[i - 1][j], dp[i][j - 1])
                }
            }
        }

        return dp[text1.length][text2.length]
    }

    private fun charsMatch(s1: String, s2: String, i: Int, j: Int) =
        s1[i - 1] == s2[j - 1]
}

/** Solution 2: Space Optimized (Two Rows) - Time: O(m*n), Space: O(n) */
class LCS2 {
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        var prev = IntArray(text2.length + 1)

        for (i in 1..text1.length) {
            val curr = IntArray(text2.length + 1)
            for (j in 1..text2.length) {
                curr[j] = if (text1[i - 1] == text2[j - 1]) {
                    prev[j - 1] + 1
                } else {
                    maxOf(prev[j], curr[j - 1])
                }
            }
            prev = curr
        }

        return prev[text2.length]
    }
}

/** Solution 3: Top-Down Memoization - Time: O(m*n), Space: O(m*n) */
class LCS3 {
    fun longestCommonSubsequence(text1: String, text2: String): Int {
        val memo = HashMap<Pair<Int, Int>, Int>()
        return solve(text1, text2, 0, 0, memo)
    }

    private fun solve(s1: String, s2: String, i: Int, j: Int, memo: HashMap<Pair<Int, Int>, Int>): Int {
        if (i == s1.length || j == s2.length) return 0

        return memo.getOrPut(i to j) {
            if (s1[i] == s2[j]) {
                1 + solve(s1, s2, i + 1, j + 1, memo)
            } else {
                maxOf(solve(s1, s2, i + 1, j, memo), solve(s1, s2, i, j + 1, memo))
            }
        }
    }
}
