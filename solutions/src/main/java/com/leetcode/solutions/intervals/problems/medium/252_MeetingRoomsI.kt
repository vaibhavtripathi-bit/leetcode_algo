package com.leetcode.solutions.intervals.problems.medium

/**
 * LeetCode 252: Meeting Rooms
 * https://leetcode.com/problems/meeting-rooms/
 *
 * Difficulty: Easy (Premium)
 * Companies: All FAANG
 *
 * Given meeting time intervals, determine if a person can attend all meetings.
 * (No two meetings can overlap)
 *
 * Example: [[0,30],[5,10],[15,20]] → false (0-30 overlaps with 5-10)
 */

/** Solution 1: Sort + Check Adjacent - Time: O(n log n), Space: O(1) */
class MeetingRooms1 {
    fun canAttendMeetings(intervals: Array<IntArray>): Boolean {
        intervals.sortBy { it[0] }

        for (i in 1 until intervals.size) {
            if (overlaps(intervals[i - 1], intervals[i])) return false
        }

        return true
    }

    private fun overlaps(a: IntArray, b: IntArray) = a[1] > b[0]
}

/** Solution 2: Sort + all() check - Time: O(n log n), Space: O(1) */
class MeetingRooms2 {
    fun canAttendMeetings(intervals: Array<IntArray>): Boolean {
        intervals.sortBy { it[0] }
        return intervals.zipWithNext().all { (a, b) -> a[1] <= b[0] }
    }
}
