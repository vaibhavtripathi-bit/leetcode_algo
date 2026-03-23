package com.leetcode.solutions.greedy.problems.medium

/**
 * LeetCode 763: Partition Labels
 * https://leetcode.com/problems/partition-labels/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google
 *
 * Partition string into as many parts as possible so each letter appears in at most one part.
 * Return the sizes of these parts.
 *
 * Example: s = "ababcbacadefegdehijhklij" → [9,7,8]
 */

/** Solution 1: Last Occurrence + Greedy - Time: O(n), Space: O(1) */
class PartitionLabels1 {
    fun partitionLabels(s: String): List<Int> {
        val lastOccurrence = buildLastOccurrenceMap(s)
        return buildPartitions(s, lastOccurrence)
    }

    private fun buildLastOccurrenceMap(s: String): IntArray {
        val last = IntArray(26)
        s.forEachIndexed { i, c -> last[c - 'a'] = i }
        return last
    }

    private fun buildPartitions(s: String, last: IntArray): List<Int> {
        val result = mutableListOf<Int>()
        var start = 0
        var end = 0

        for (i in s.indices) {
            end = maxOf(end, last[s[i] - 'a'])

            if (i == end) {
                result.add(end - start + 1)
                start = i + 1
            }
        }

        return result
    }
}
