package com.leetcode.solutions.strings.problems.medium

/**
 * LeetCode 49: Group Anagrams
 * https://leetcode.com/problems/group-anagrams/
 *
 * Difficulty: Medium
 * Companies: All FAANG
 *
 * Group strings that are anagrams of each other.
 *
 * Example: strs = ["eat","tea","tan","ate","nat","bat"]
 * → [["bat"],["nat","tan"],["ate","eat","tea"]]
 */

/** Solution 1: Sort key - Time: O(n * k log k), Space: O(nk) */
class GroupAnagrams1 {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val groups = HashMap<String, MutableList<String>>()

        for (word in strs) {
            val key = word.toCharArray().sorted().joinToString("")
            groups.getOrPut(key) { mutableListOf() }.add(word)
        }

        return groups.values.toList()
    }
}

/** Solution 2: Count array as key - Time: O(n * k), Space: O(nk) */
class GroupAnagrams2 {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {
        val groups = HashMap<String, MutableList<String>>()

        for (word in strs) {
            val key = buildCountKey(word)
            groups.getOrPut(key) { mutableListOf() }.add(word)
        }

        return groups.values.toList()
    }

    private fun buildCountKey(word: String): String {
        val count = IntArray(26)
        word.forEach { count[it - 'a']++ }
        return count.joinToString(",")
    }
}
