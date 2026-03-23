package com.leetcode.solutions.backtracking.problems.medium

/**
 * LeetCode 131: Palindrome Partitioning
 * https://leetcode.com/problems/palindrome-partitioning/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Meta
 *
 * Partition s such that every substring is a palindrome. Return all possible partitions.
 *
 * Example: s = "aab" → [["a","a","b"],["aa","b"]]
 */

/** Solution 1: Backtracking - Time: O(n * 2^n), Space: O(n) */
class PalindromePartition1 {
    fun partition(s: String): List<List<String>> {
        val result = mutableListOf<List<String>>()
        backtrack(s, 0, mutableListOf(), result)
        return result
    }

    private fun backtrack(
        s: String, start: Int,
        current: MutableList<String>, result: MutableList<List<String>>
    ) {
        if (start == s.length) {
            result.add(current.toList())
            return
        }

        for (end in start + 1..s.length) {
            val candidate = s.substring(start, end)
            if (isPalindrome(candidate)) {
                current.add(candidate)
                backtrack(s, end, current, result)
                current.removeLast()
            }
        }
    }

    private fun isPalindrome(s: String): Boolean {
        var l = 0; var r = s.lastIndex
        while (l < r) { if (s[l++] != s[r--]) return false }
        return true
    }
}

/** Solution 2: Backtracking + DP precompute palindrome table - Time: O(n * 2^n), Space: O(n²) */
class PalindromePartition2 {
    fun partition(s: String): List<List<String>> {
        val n = s.length
        val isPalin = precompute(s, n)
        val result = mutableListOf<List<String>>()
        backtrack(s, 0, mutableListOf(), result, isPalin)
        return result
    }

    private fun precompute(s: String, n: Int): Array<BooleanArray> {
        val dp = Array(n) { BooleanArray(n) }
        for (i in n - 1 downTo 0) {
            for (j in i until n) {
                dp[i][j] = s[i] == s[j] && (j - i <= 2 || dp[i + 1][j - 1])
            }
        }
        return dp
    }

    private fun backtrack(
        s: String, start: Int, current: MutableList<String>,
        result: MutableList<List<String>>, dp: Array<BooleanArray>
    ) {
        if (start == s.length) { result.add(current.toList()); return }

        for (end in start until s.length) {
            if (dp[start][end]) {
                current.add(s.substring(start, end + 1))
                backtrack(s, end + 1, current, result, dp)
                current.removeLast()
            }
        }
    }
}
