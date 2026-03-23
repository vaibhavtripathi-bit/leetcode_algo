package com.leetcode.solutions.strings.algorithms

/**
 * KMP (Knuth-Morris-Pratt) String Matching Algorithm
 *
 * Key Insight: When a mismatch occurs, use the LPS array to skip re-comparing
 * characters that we already know will match.
 *
 * LPS[i] = length of longest proper prefix of pattern[0..i] that is also a suffix
 *
 * Time: O(n + m), Space: O(m)
 */
object KMP {

    fun search(text: String, pattern: String): List<Int> {
        if (pattern.isEmpty()) return emptyList()

        val lps = buildLPS(pattern)
        return findMatches(text, pattern, lps)
    }

    private fun buildLPS(pattern: String): IntArray {
        val lps = IntArray(pattern.length)
        var length = 0
        var i = 1

        while (i < pattern.length) {
            if (pattern[i] == pattern[length]) {
                length++
                lps[i] = length
                i++
            } else {
                if (length != 0) length = lps[length - 1]
                else { lps[i] = 0; i++ }
            }
        }

        return lps
    }

    private fun findMatches(text: String, pattern: String, lps: IntArray): List<Int> {
        val matches = mutableListOf<Int>()
        var i = 0
        var j = 0

        while (i < text.length) {
            if (text[i] == pattern[j]) { i++; j++ }

            when {
                j == pattern.length -> {
                    matches.add(i - j)
                    j = lps[j - 1]
                }
                i < text.length && text[i] != pattern[j] -> {
                    if (j != 0) j = lps[j - 1] else i++
                }
            }
        }

        return matches
    }
}
