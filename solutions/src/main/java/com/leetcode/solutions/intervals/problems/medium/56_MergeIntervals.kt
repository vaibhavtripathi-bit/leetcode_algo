package com.leetcode.solutions.intervals.problems.medium

/**
 * LeetCode 56: Merge Intervals
 * https://leetcode.com/problems/merge-intervals/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Given an array of intervals, merge all overlapping intervals.
 *
 * Example: [[1,3],[2,6],[8,10],[15,18]] → [[1,6],[8,10],[15,18]]
 */

/** Solution 1: Sort + Merge - Time: O(n log n), Space: O(n) */
class MergeIntervals1 {
    fun merge(intervals: Array<IntArray>): Array<IntArray> {
        intervals.sortBy { it[0] }

        val result = mutableListOf<IntArray>()

        for (interval in intervals) {
            if (overlapsWithLast(result, interval)) {
                extendLast(result, interval)
            } else {
                result.add(interval)
            }
        }

        return result.toTypedArray()
    }

    private fun overlapsWithLast(result: List<IntArray>, interval: IntArray) =
        result.isNotEmpty() && interval[0] <= result.last()[1]

    private fun extendLast(result: MutableList<IntArray>, interval: IntArray) {
        result.last()[1] = maxOf(result.last()[1], interval[1])
    }
}

/** Solution 2: Stack-based - Time: O(n log n), Space: O(n) */
class MergeIntervals2 {
    fun merge(intervals: Array<IntArray>): Array<IntArray> {
        intervals.sortBy { it[0] }
        val stack = ArrayDeque<IntArray>()

        for (interval in intervals) {
            when {
                stack.isEmpty() -> stack.addLast(interval)
                interval[0] <= stack.last()[1] ->
                    stack.last()[1] = maxOf(stack.last()[1], interval[1])
                else -> stack.addLast(interval)
            }
        }

        return stack.toTypedArray()
    }
}

/** Solution 3: Build result inline with index tracking - Time: O(n log n), Space: O(1) extra */
class MergeIntervals3 {
    fun merge(intervals: Array<IntArray>): Array<IntArray> {
        intervals.sortBy { it[0] }

        var writeIdx = 0

        for (i in 1 until intervals.size) {
            if (intervals[i][0] <= intervals[writeIdx][1]) {
                intervals[writeIdx][1] = maxOf(intervals[writeIdx][1], intervals[i][1])
            } else {
                writeIdx++
                intervals[writeIdx] = intervals[i]
            }
        }

        return intervals.copyOfRange(0, writeIdx + 1)
    }
}
