package com.leetcode.solutions.strings.problems.medium

/**
 * LeetCode 5: Longest Palindromic Substring
 * https://leetcode.com/problems/longest-palindromic-substring/
 *
 * Difficulty: Medium
 * Companies: All FAANG
 *
 * Return the longest palindromic substring.
 *
 * Example: s = "babad" → "bab" or "aba"
 */

/** Solution 1: Expand Around Centers - Time: O(n²), Space: O(1) */
class LongestPalindrome1 {
    fun longestPalindrome(s: String): String {
        var start = 0
        var maxLen = 1

        for (center in s.indices) {
            val odd = expandFromCenter(s, center, center)
            val even = expandFromCenter(s, center, center + 1)
            val best = maxOf(odd, even, Comparator.comparingInt { it.length })

            if (best.length > maxLen) {
                maxLen = best.length
                start = s.indexOf(best, maxOf(0, start - 1))
            }
        }

        return s.substring(start, start + maxLen)
    }

    private fun expandFromCenter(s: String, left: Int, right: Int): String {
        var l = left
        var r = right

        while (l >= 0 && r < s.length && s[l] == s[r]) {
            l--; r++
        }

        return s.substring(l + 1, r)
    }
}

/** Solution 2: DP Table - Time: O(n²), Space: O(n²) */
class LongestPalindrome2 {
    fun longestPalindrome(s: String): String {
        val n = s.length
        val dp = Array(n) { BooleanArray(n) }
        var start = 0; var maxLen = 1

        for (i in n - 1 downTo 0) {
            for (j in i until n) {
                dp[i][j] = s[i] == s[j] && (j - i <= 2 || dp[i + 1][j - 1])

                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1
                    start = i
                }
            }
        }

        return s.substring(start, start + maxLen)
    }
}

/** Solution 3: Manacher's Algorithm - Time: O(n), Space: O(n) */
class LongestPalindrome3 {
    fun longestPalindrome(s: String): String {
        val t = buildTransformed(s)
        val p = manachers(t)
        val maxLen = p.max()!!
        val center = p.indexOf(maxLen)
        val start = (center - maxLen) / 2
        return s.substring(start, start + maxLen)
    }

    private fun buildTransformed(s: String) = "#${s.toCharArray().joinToString("#")}#"

    private fun manachers(t: String): IntArray {
        val n = t.length
        val p = IntArray(n)
        var center = 0; var right = 0

        for (i in t.indices) {
            val mirror = 2 * center - i
            if (i < right) p[i] = minOf(right - i, p[mirror])

            var l = i - (p[i] + 1)
            var r = i + (p[i] + 1)
            while (l >= 0 && r < n && t[l] == t[r]) { p[i]++; l--; r++ }

            if (i + p[i] > right) { center = i; right = i + p[i] }
        }

        return p
    }
}
