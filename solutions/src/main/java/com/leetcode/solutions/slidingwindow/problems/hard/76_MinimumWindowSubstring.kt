package com.leetcode.solutions.slidingwindow.problems.hard

/**
 * 76. Minimum Window Substring
 * https://leetcode.com/problems/minimum-window-substring/
 *
 * Given two strings s and t of lengths m and n respectively, return the minimum window substring
 * of s such that every character in t (including duplicates) is included in the window.
 * If there is no such substring, return the empty string "".
 *
 * Example 1:
 * Input: s = "ADOBECODEBANC", t = "ABC"
 * Output: "BANC"
 * Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
 *
 * Example 2:
 * Input: s = "a", t = "a"
 * Output: "a"
 *
 * Example 3:
 * Input: s = "a", t = "aa"
 * Output: ""
 *
 * Constraints:
 * - m == s.length
 * - n == t.length
 * - 1 <= m, n <= 10^5
 * - s and t consist of uppercase and lowercase English letters.
 */
class MinimumWindowSubstring {

    /**
     * Solution 1: Sliding Window with HashMap and Valid Count
     * Expand window until valid, then shrink to find minimum.
     *
     * Time Complexity: O(m + n)
     * Space Complexity: O(m + n)
     */
    fun minWindowHashMap(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty() || s.length < t.length) return ""

        val need = HashMap<Char, Int>()
        for (c in t) {
            need[c] = need.getOrDefault(c, 0) + 1
        }

        val window = HashMap<Char, Int>()
        var left = 0
        var valid = 0
        var minLen = Int.MAX_VALUE
        var minStart = 0

        for (right in s.indices) {
            val c = s[right]
            if (need.containsKey(c)) {
                window[c] = window.getOrDefault(c, 0) + 1
                if (window[c] == need[c]) {
                    valid++
                }
            }

            while (valid == need.size) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1
                    minStart = left
                }

                val d = s[left]
                left++
                if (need.containsKey(d)) {
                    if (window[d] == need[d]) {
                        valid--
                    }
                    window[d] = window[d]!! - 1
                }
            }
        }

        return if (minLen == Int.MAX_VALUE) "" else s.substring(minStart, minStart + minLen)
    }

    /**
     * Solution 2: Sliding Window with Array (ASCII optimization)
     * Use arrays instead of HashMaps for better performance.
     *
     * Time Complexity: O(m + n)
     * Space Complexity: O(1) - fixed size arrays
     */
    fun minWindowArray(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty() || s.length < t.length) return ""

        val need = IntArray(128)
        val window = IntArray(128)
        var required = 0

        for (c in t) {
            if (need[c.code] == 0) required++
            need[c.code]++
        }

        var left = 0
        var formed = 0
        var minLen = Int.MAX_VALUE
        var minStart = 0

        for (right in s.indices) {
            val c = s[right].code
            window[c]++

            if (need[c] > 0 && window[c] == need[c]) {
                formed++
            }

            while (formed == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1
                    minStart = left
                }

                val leftChar = s[left].code
                window[leftChar]--
                if (need[leftChar] > 0 && window[leftChar] < need[leftChar]) {
                    formed--
                }
                left++
            }
        }

        return if (minLen == Int.MAX_VALUE) "" else s.substring(minStart, minStart + minLen)
    }

    /**
     * Solution 3: Optimized Sliding Window with Filtered Characters
     * Pre-filter s to only contain characters in t for sparse cases.
     *
     * Time Complexity: O(m + n) - better constants when |t| << |s|
     * Space Complexity: O(m + n)
     */
    fun minWindowFiltered(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty() || s.length < t.length) return ""

        val need = HashMap<Char, Int>()
        for (c in t) {
            need[c] = need.getOrDefault(c, 0) + 1
        }

        val filtered = mutableListOf<Pair<Int, Char>>()
        for (i in s.indices) {
            if (need.containsKey(s[i])) {
                filtered.add(Pair(i, s[i]))
            }
        }

        if (filtered.isEmpty()) return ""

        val window = HashMap<Char, Int>()
        var left = 0
        var valid = 0
        var minLen = Int.MAX_VALUE
        var minStart = 0

        for (right in filtered.indices) {
            val (rightIdx, c) = filtered[right]
            window[c] = window.getOrDefault(c, 0) + 1
            if (window[c] == need[c]) {
                valid++
            }

            while (valid == need.size) {
                val (leftIdx, d) = filtered[left]
                val windowLen = rightIdx - leftIdx + 1

                if (windowLen < minLen) {
                    minLen = windowLen
                    minStart = leftIdx
                }

                if (window[d] == need[d]) {
                    valid--
                }
                window[d] = window[d]!! - 1
                left++
            }
        }

        return if (minLen == Int.MAX_VALUE) "" else s.substring(minStart, minStart + minLen)
    }

    /**
     * Solution 4: Two Pointers with Required Count
     * Track total required characters instead of per-character validity.
     *
     * Time Complexity: O(m + n)
     * Space Complexity: O(1)
     */
    fun minWindowTwoPointers(s: String, t: String): String {
        if (s.isEmpty() || t.isEmpty() || s.length < t.length) return ""

        val count = IntArray(128)
        for (c in t) {
            count[c.code]++
        }

        var required = t.length
        var left = 0
        var minLen = Int.MAX_VALUE
        var minStart = 0

        for (right in s.indices) {
            if (count[s[right].code] > 0) {
                required--
            }
            count[s[right].code]--

            while (required == 0) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1
                    minStart = left
                }

                count[s[left].code]++
                if (count[s[left].code] > 0) {
                    required++
                }
                left++
            }
        }

        return if (minLen == Int.MAX_VALUE) "" else s.substring(minStart, minStart + minLen)
    }
}
