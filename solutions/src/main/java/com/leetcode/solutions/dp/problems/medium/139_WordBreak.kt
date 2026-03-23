package com.leetcode.solutions.dp.problems.medium

/**
 * LeetCode 139: Word Break
 * https://leetcode.com/problems/word-break/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Return true if s can be segmented into words from wordDict.
 *
 * Example: s = "leetcode", wordDict = ["leet","code"] → true
 */

/** Solution 1: Bottom-Up DP - Time: O(n² * m), Space: O(n) */
class WordBreak1 {
    fun wordBreak(s: String, wordDict: List<String>): Boolean {
        val wordSet = wordDict.toHashSet()
        val dp = BooleanArray(s.length + 1)
        dp[0] = true

        for (end in 1..s.length) {
            dp[end] = canFormWord(s, dp, wordSet, end)
        }

        return dp[s.length]
    }

    private fun canFormWord(s: String, dp: BooleanArray, words: Set<String>, end: Int): Boolean {
        return (0 until end).any { start ->
            dp[start] && s.substring(start, end) in words
        }
    }
}

/** Solution 2: Top-Down Memoization - Time: O(n² * m), Space: O(n) */
class WordBreak2 {
    fun wordBreak(s: String, wordDict: List<String>): Boolean {
        val wordSet = wordDict.toHashSet()
        val memo = HashMap<Int, Boolean>()
        return canBreak(s, wordSet, 0, memo)
    }

    private fun canBreak(s: String, words: Set<String>, start: Int, memo: HashMap<Int, Boolean>): Boolean {
        if (start == s.length) return true

        return memo.getOrPut(start) {
            (start + 1..s.length).any { end ->
                s.substring(start, end) in words && canBreak(s, words, end, memo)
            }
        }
    }
}

/** Solution 3: BFS - Time: O(n²), Space: O(n) */
class WordBreak3 {
    fun wordBreak(s: String, wordDict: List<String>): Boolean {
        val wordSet = wordDict.toHashSet()
        val queue = ArrayDeque<Int>()
        val visited = BooleanArray(s.length + 1)

        queue.addLast(0)
        visited[0] = true

        while (queue.isNotEmpty()) {
            val start = queue.removeFirst()

            for (end in start + 1..s.length) {
                if (!visited[end] && s.substring(start, end) in wordSet) {
                    if (end == s.length) return true
                    visited[end] = true
                    queue.addLast(end)
                }
            }
        }

        return false
    }
}
