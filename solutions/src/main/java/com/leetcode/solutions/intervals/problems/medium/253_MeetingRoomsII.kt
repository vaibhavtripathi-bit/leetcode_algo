package com.leetcode.solutions.intervals.problems.medium

import java.util.PriorityQueue

/**
 * LeetCode 253: Meeting Rooms II
 * https://leetcode.com/problems/meeting-rooms-ii/
 *
 * Difficulty: Medium (Premium)
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Find the minimum number of conference rooms required.
 *
 * Example: [[0,30],[5,10],[15,20]] → 2
 */

/**
 * Solution 1: Min-Heap (Track when each room becomes free) - Time: O(n log n), Space: O(n)
 *
 * Sort by start. Use min-heap of end times. If earliest-ending room frees before
 * next meeting, reuse it; otherwise add a new room.
 */
class MeetingRoomsII1 {
    fun minMeetingRooms(intervals: Array<IntArray>): Int {
        intervals.sortBy { it[0] }
        val endTimes = PriorityQueue<Int>()

        for (meeting in intervals) {
            if (roomAvailable(endTimes, meeting)) {
                endTimes.poll()
            }
            endTimes.offer(meeting[1])
        }

        return endTimes.size
    }

    private fun roomAvailable(endTimes: PriorityQueue<Int>, meeting: IntArray) =
        endTimes.isNotEmpty() && endTimes.peek() <= meeting[0]
}

/**
 * Solution 2: Line Sweep with Events - Time: O(n log n), Space: O(n)
 *
 * For each interval, create +1 event at start and -1 at end.
 * Sort all events and track max simultaneous count.
 */
class MeetingRoomsII2 {
    fun minMeetingRooms(intervals: Array<IntArray>): Int {
        val events = buildEvents(intervals)
        return findMaxSimultaneous(events)
    }

    private fun buildEvents(intervals: Array<IntArray>): List<Pair<Int, Int>> {
        val events = mutableListOf<Pair<Int, Int>>()
        for ((start, end) in intervals) {
            events.add(start to 1)    // meeting starts
            events.add(end to -1)     // meeting ends
        }
        return events.sortedWith(compareBy({ it.first }, { it.second }))
    }

    private fun findMaxSimultaneous(events: List<Pair<Int, Int>>): Int {
        var current = 0
        var max = 0
        for ((_, change) in events) {
            current += change
            max = maxOf(max, current)
        }
        return max
    }
}

/**
 * Solution 3: Two Sorted Arrays (Start + End) - Time: O(n log n), Space: O(n)
 *
 * If a meeting starts before the earliest-ending meeting ends, we need a new room.
 */
class MeetingRoomsII3 {
    fun minMeetingRooms(intervals: Array<IntArray>): Int {
        val starts = intervals.map { it[0] }.sorted()
        val ends = intervals.map { it[1] }.sorted()

        var rooms = 0
        var j = 0

        for (i in starts.indices) {
            if (starts[i] < ends[j]) rooms++
            else j++
        }

        return rooms
    }
}
