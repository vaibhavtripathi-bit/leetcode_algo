package com.leetcode.solutions.intervals.problems.medium

/**
 * LeetCode 57: Insert Interval
 * https://leetcode.com/problems/insert-interval/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Given sorted non-overlapping intervals and a new interval, insert and merge.
 *
 * Example: intervals = [[1,3],[6,9]], newInterval = [2,5] → [[1,5],[6,9]]
 */

/** Solution 1: Three-phase scan - Time: O(n), Space: O(n) */
class InsertInterval1 {
    fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
        val result = mutableListOf<IntArray>()
        var i = 0

        i = addIntervalsBefore(intervals, newInterval, result, i)
        val merged = mergeOverlapping(intervals, newInterval, result, i)
        i = merged.second
        addIntervalsAfter(intervals, result, i)

        return result.toTypedArray()
    }

    private fun addIntervalsBefore(
        intervals: Array<IntArray>, newInterval: IntArray,
        result: MutableList<IntArray>, start: Int
    ): Int {
        var i = start
        while (i < intervals.size && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i++])
        }
        return i
    }

    private fun mergeOverlapping(
        intervals: Array<IntArray>, newInterval: IntArray,
        result: MutableList<IntArray>, start: Int
    ): Pair<IntArray, Int> {
        var merged = newInterval
        var i = start

        while (i < intervals.size && intervals[i][0] <= merged[1]) {
            merged = intArrayOf(
                minOf(merged[0], intervals[i][0]),
                maxOf(merged[1], intervals[i][1])
            )
            i++
        }

        result.add(merged)
        return merged to i
    }

    private fun addIntervalsAfter(
        intervals: Array<IntArray>, result: MutableList<IntArray>, start: Int
    ) {
        for (i in start until intervals.size) result.add(intervals[i])
    }
}

/** Solution 2: Binary Search for position, then merge - Time: O(n), Space: O(n) */
class InsertInterval2 {
    fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
        if (intervals.isEmpty()) return arrayOf(newInterval)

        val result = mutableListOf<IntArray>()
        var inserted = false

        for (interval in intervals) {
            when {
                interval[1] < newInterval[0] -> result.add(interval)
                interval[0] > newInterval[1] -> {
                    if (!inserted) { result.add(newInterval); inserted = true }
                    result.add(interval)
                }
                else -> {
                    newInterval[0] = minOf(newInterval[0], interval[0])
                    newInterval[1] = maxOf(newInterval[1], interval[1])
                }
            }
        }

        if (!inserted) result.add(newInterval)
        return result.toTypedArray()
    }
}
