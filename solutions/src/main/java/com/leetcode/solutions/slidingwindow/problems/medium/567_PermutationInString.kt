package com.leetcode.solutions.slidingwindow.problems.medium

/**
 * 567. Permutation in String
 * https://leetcode.com/problems/permutation-in-string/
 *
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1,
 * or false otherwise.
 * In other words, return true if one of s1's permutations is the substring of s2.
 *
 * Example 1:
 * Input: s1 = "ab", s2 = "eidbaooo"
 * Output: true
 * Explanation: s2 contains one permutation of s1 ("ba").
 *
 * Example 2:
 * Input: s1 = "ab", s2 = "eidboaoo"
 * Output: false
 *
 * Constraints:
 * - 1 <= s1.length, s2.length <= 10^4
 * - s1 and s2 consist of lowercase English letters.
 */
class PermutationInString {

    /**
     * Solution 1: Sliding Window with Frequency Match Count
     * Track how many characters have matching frequencies in the window.
     *
     * Time Complexity: O(n) where n = s2.length
     * Space Complexity: O(1) - fixed size arrays of 26
     */
    fun checkInclusionMatchCount(s1: String, s2: String): Boolean {
        if (s1.length > s2.length) return false

        val s1Count = IntArray(26)
        val s2Count = IntArray(26)

        for (c in s1) {
            s1Count[c - 'a']++
        }

        var matches = 0
        for (i in 0 until 26) {
            if (s1Count[i] == 0) matches++
        }

        for (i in s2.indices) {
            val index = s2[i] - 'a'
            s2Count[index]++

            if (s2Count[index] == s1Count[index]) {
                matches++
            } else if (s2Count[index] == s1Count[index] + 1) {
                matches--
            }

            if (i >= s1.length) {
                val leftIndex = s2[i - s1.length] - 'a'
                s2Count[leftIndex]--

                if (s2Count[leftIndex] == s1Count[leftIndex]) {
                    matches++
                } else if (s2Count[leftIndex] == s1Count[leftIndex] - 1) {
                    matches--
                }
            }

            if (matches == 26) return true
        }

        return false
    }

    /**
     * Solution 2: Sliding Window with Array Comparison
     * Compare frequency arrays at each window position.
     *
     * Time Complexity: O(26 * n) = O(n)
     * Space Complexity: O(1)
     */
    fun checkInclusionArrayCompare(s1: String, s2: String): Boolean {
        if (s1.length > s2.length) return false

        val s1Count = IntArray(26)
        val windowCount = IntArray(26)

        for (c in s1) {
            s1Count[c - 'a']++
        }

        for (i in s2.indices) {
            windowCount[s2[i] - 'a']++

            if (i >= s1.length) {
                windowCount[s2[i - s1.length] - 'a']--
            }

            if (s1Count.contentEquals(windowCount)) {
                return true
            }
        }

        return false
    }

    /**
     * Solution 3: Sliding Window with HashMap
     * Use HashMap for character counting with flexible shrinking.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1) - at most 26 keys
     */
    fun checkInclusionHashMap(s1: String, s2: String): Boolean {
        if (s1.length > s2.length) return false

        val need = HashMap<Char, Int>()
        for (c in s1) {
            need[c] = need.getOrDefault(c, 0) + 1
        }

        val window = HashMap<Char, Int>()
        var left = 0
        var valid = 0

        for (right in s2.indices) {
            val c = s2[right]
            if (need.containsKey(c)) {
                window[c] = window.getOrDefault(c, 0) + 1
                if (window[c] == need[c]) {
                    valid++
                }
            }

            while (right - left + 1 >= s1.length) {
                if (valid == need.size) {
                    return true
                }

                val d = s2[left]
                left++
                if (need.containsKey(d)) {
                    if (window[d] == need[d]) {
                        valid--
                    }
                    window[d] = window[d]!! - 1
                }
            }
        }

        return false
    }

    /**
     * Solution 4: Fixed Size Sliding Window with Difference Array
     * Track the difference between required and current counts.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    fun checkInclusionDiffArray(s1: String, s2: String): Boolean {
        if (s1.length > s2.length) return false

        val diff = IntArray(26)
        var numNonZero = 0

        for (c in s1) {
            if (diff[c - 'a'] == 0) numNonZero++
            diff[c - 'a']--
            if (diff[c - 'a'] == 0) numNonZero--
        }

        for (i in s2.indices) {
            val addChar = s2[i] - 'a'
            if (diff[addChar] == 0) numNonZero++
            diff[addChar]++
            if (diff[addChar] == 0) numNonZero--

            if (i >= s1.length) {
                val removeChar = s2[i - s1.length] - 'a'
                if (diff[removeChar] == 0) numNonZero++
                diff[removeChar]--
                if (diff[removeChar] == 0) numNonZero--
            }

            if (numNonZero == 0) return true
        }

        return false
    }
}
