package com.leetcode.solutions.strings.problems.easy

/**
 * LeetCode 242: Valid Anagram
 * https://leetcode.com/problems/valid-anagram/
 *
 * Difficulty: Easy
 * Companies: All FAANG
 *
 * Return true if t is an anagram of s.
 *
 * Example: s = "anagram", t = "nagaram" → true
 */

/** Solution 1: Character count array - Time: O(n), Space: O(1) */
class ValidAnagram1 {
    fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) return false

        val count = IntArray(26)
        for (i in s.indices) {
            count[s[i] - 'a']++
            count[t[i] - 'a']--
        }

        return count.all { it == 0 }
    }
}

/** Solution 2: Sort - Time: O(n log n), Space: O(n) */
class ValidAnagram2 {
    fun isAnagram(s: String, t: String): Boolean {
        return s.toCharArray().sorted() == t.toCharArray().sorted()
    }
}

/** Solution 3: HashMap (works for Unicode) - Time: O(n), Space: O(k) */
class ValidAnagram3 {
    fun isAnagram(s: String, t: String): Boolean {
        if (s.length != t.length) return false

        val freq = HashMap<Char, Int>()
        s.forEach { freq[it] = freq.getOrDefault(it, 0) + 1 }
        t.forEach { freq[it] = freq.getOrDefault(it, 0) - 1 }

        return freq.values.all { it == 0 }
    }
}
