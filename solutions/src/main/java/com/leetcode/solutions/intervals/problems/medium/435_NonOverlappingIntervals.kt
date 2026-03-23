package com.leetcode.solutions.intervals.problems.medium

/**
 * LeetCode 435: Non-Overlapping Intervals
 * https://leetcode.com/problems/non-overlapping-intervals/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Meta
 *
 * Return the minimum number of intervals to remove to make the rest non-overlapping.
 *
 * Example: [[1,2],[2,3],[3,4],[1,3]] → 1 (remove [1,3])
 */

/**
 * Solution 1: Greedy (Sort by End) - Time: O(n log n), Space: O(1)
 *
 * Keep intervals ending earliest. Greedy: finishing early gives most room for future intervals.
 */
class NonOverlapping1 {
    fun eraseOverlapIntervals(intervals: Array<IntArray>): Int {
        intervals.sortBy { it[1] }

        var kept = 1
        var lastEnd = intervals[0][1]

        for (i in 1 until intervals.size) {
            if (intervals[i][0] >= lastEnd) {
                kept++
                lastEnd = intervals[i][1]
            }
        }

        return intervals.size - kept
    }
}

/** Solution 2: Sort by Start, DP - Time: O(n²), Space: O(n) */
class NonOverlapping2 {
    fun eraseOverlapIntervals(intervals: Array<IntArray>): Int {
        intervals.sortBy { it[0] }
        val n = intervals.size
        val dp = IntArray(n) { 1 }

        for (i in 1 until n) {
            for (j in 0 until i) {
                if (intervals[j][1] <= intervals[i][0]) {
                    dp[i] = maxOf(dp[i], dp[j] + 1)
                }
            }
        }

        return n - dp.max()!!
    }
}

/** Solution 3: Count overlaps directly - Time: O(n log n), Space: O(1) */
class NonOverlapping3 {
    fun eraseOverlapIntervals(intervals: Array<IntArray>): Int {
        intervals.sortBy { it[1] }

        var removals = 0
        var lastEnd = Int.MIN_VALUE

        for (interval in intervals) {
            if (interval[0] < lastEnd) {
                removals++
            } else {
                lastEnd = interval[1]
            }
        }

        return removals
    }
}
