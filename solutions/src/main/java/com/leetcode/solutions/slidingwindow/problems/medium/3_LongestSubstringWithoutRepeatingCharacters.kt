package com.leetcode.solutions.slidingwindow.problems.medium

/**
 * 3. Longest Substring Without Repeating Characters
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 *
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 *
 * Constraints:
 * - 0 <= s.length <= 5 * 10^4
 * - s consists of English letters, digits, symbols and spaces.
 */
class LongestSubstringWithoutRepeatingCharacters {

    /**
     * Solution 1: Sliding Window with HashSet
     * Use a set to track characters in current window, shrink when duplicate found.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(min(n, m)) where m is the charset size
     */
    fun lengthOfLongestSubstringHashSet(s: String): Int {
        val charSet = HashSet<Char>()
        var left = 0
        var maxLength = 0

        for (right in s.indices) {
            while (charSet.contains(s[right])) {
                charSet.remove(s[left])
                left++
            }
            charSet.add(s[right])
            maxLength = maxOf(maxLength, right - left + 1)
        }

        return maxLength
    }

    /**
     * Solution 2: Sliding Window with HashMap (Optimized)
     * Use a map to store character's last index and jump directly.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(min(n, m)) where m is the charset size
     */
    fun lengthOfLongestSubstringHashMap(s: String): Int {
        val charIndexMap = HashMap<Char, Int>()
        var left = 0
        var maxLength = 0

        for (right in s.indices) {
            if (charIndexMap.containsKey(s[right])) {
                left = maxOf(left, charIndexMap[s[right]]!! + 1)
            }
            charIndexMap[s[right]] = right
            maxLength = maxOf(maxLength, right - left + 1)
        }

        return maxLength
    }

    /**
     * Solution 3: Sliding Window with Array (ASCII optimization)
     * Use an array for O(1) lookups when dealing with ASCII characters.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1) - fixed size array of 128
     */
    fun lengthOfLongestSubstringArray(s: String): Int {
        val lastIndex = IntArray(128) { -1 }
        var left = 0
        var maxLength = 0

        for (right in s.indices) {
            val charCode = s[right].code
            if (lastIndex[charCode] >= left) {
                left = lastIndex[charCode] + 1
            }
            lastIndex[charCode] = right
            maxLength = maxOf(maxLength, right - left + 1)
        }

        return maxLength
    }

    /**
     * Solution 4: Sliding Window with Frequency Count
     * Track character frequencies and shrink window when count > 1.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(min(n, m)) where m is the charset size
     */
    fun lengthOfLongestSubstringFrequency(s: String): Int {
        val freq = IntArray(128)
        var left = 0
        var maxLength = 0

        for (right in s.indices) {
            freq[s[right].code]++

            while (freq[s[right].code] > 1) {
                freq[s[left].code]--
                left++
            }

            maxLength = maxOf(maxLength, right - left + 1)
        }

        return maxLength
    }
}
