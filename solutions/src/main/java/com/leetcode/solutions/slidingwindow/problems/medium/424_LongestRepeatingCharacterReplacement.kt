package com.leetcode.solutions.slidingwindow.problems.medium

/**
 * 424. Longest Repeating Character Replacement
 * https://leetcode.com/problems/longest-repeating-character-replacement/
 *
 * You are given a string s and an integer k. You can choose any character of the string and
 * change it to any other uppercase English character. You can perform this operation at most k times.
 * Return the length of the longest substring containing the same letter you can get after
 * performing the above operations.
 *
 * Example 1:
 * Input: s = "ABAB", k = 2
 * Output: 4
 * Explanation: Replace the two 'A's with two 'B's or vice versa.
 *
 * Example 2:
 * Input: s = "AABABBA", k = 1
 * Output: 4
 * Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
 *
 * Constraints:
 * - 1 <= s.length <= 10^5
 * - s consists of only uppercase English letters.
 * - 0 <= k <= s.length
 */
class LongestRepeatingCharacterReplacement {

    /**
     * Solution 1: Sliding Window with Max Frequency Tracking
     * Window is valid if (window size - max frequency) <= k
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1) - fixed size array of 26
     */
    fun characterReplacementMaxFreq(s: String, k: Int): Int {
        val count = IntArray(26)
        var left = 0
        var maxFreq = 0
        var maxLength = 0

        for (right in s.indices) {
            count[s[right] - 'A']++
            maxFreq = maxOf(maxFreq, count[s[right] - 'A'])

            while ((right - left + 1) - maxFreq > k) {
                count[s[left] - 'A']--
                left++
            }

            maxLength = maxOf(maxLength, right - left + 1)
        }

        return maxLength
    }

    /**
     * Solution 2: Sliding Window without Shrinking Max Frequency
     * Optimization: we don't need to decrease maxFreq as it only matters for larger windows.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    fun characterReplacementOptimized(s: String, k: Int): Int {
        val count = IntArray(26)
        var left = 0
        var maxFreq = 0
        var result = 0

        for (right in s.indices) {
            count[s[right] - 'A']++
            maxFreq = maxOf(maxFreq, count[s[right] - 'A'])

            if ((right - left + 1) - maxFreq > k) {
                count[s[left] - 'A']--
                left++
            }

            result = maxOf(result, right - left + 1)
        }

        return result
    }

    /**
     * Solution 3: Binary Search + Sliding Window
     * Binary search on the answer length, check if valid window exists.
     *
     * Time Complexity: O(n log n)
     * Space Complexity: O(1)
     */
    fun characterReplacementBinarySearch(s: String, k: Int): Int {
        fun canAchieveLength(length: Int): Boolean {
            val count = IntArray(26)
            var maxFreq = 0

            for (i in s.indices) {
                count[s[i] - 'A']++

                if (i >= length) {
                    count[s[i - length] - 'A']--
                }

                if (i >= length - 1) {
                    maxFreq = count.maxOrNull() ?: 0
                    if (length - maxFreq <= k) return true
                }
            }
            return false
        }

        var left = 1
        var right = s.length
        var result = 0

        while (left <= right) {
            val mid = left + (right - left) / 2
            if (canAchieveLength(mid)) {
                result = mid
                left = mid + 1
            } else {
                right = mid - 1
            }
        }

        return result
    }

    /**
     * Solution 4: Per-Character Sliding Window
     * For each character, find the longest substring that can be made all that character.
     *
     * Time Complexity: O(26 * n) = O(n)
     * Space Complexity: O(1)
     */
    fun characterReplacementPerChar(s: String, k: Int): Int {
        var maxLength = 0

        for (c in 'A'..'Z') {
            var left = 0
            var replacements = 0

            for (right in s.indices) {
                if (s[right] != c) {
                    replacements++
                }

                while (replacements > k) {
                    if (s[left] != c) {
                        replacements--
                    }
                    left++
                }

                maxLength = maxOf(maxLength, right - left + 1)
            }
        }

        return maxLength
    }
}
