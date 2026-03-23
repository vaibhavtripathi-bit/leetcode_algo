package com.leetcode.solutions.intervals.problems.medium

/**
 * LeetCode 1094: Car Pooling
 * https://leetcode.com/problems/car-pooling/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google
 *
 * trips[i] = [numPassengers, from, to]. Car has capacity seats.
 * Return true if all trips can be completed without exceeding capacity.
 *
 * Example: trips = [[2,1,5],[3,3,7]], capacity = 4 → false
 */

/**
 * Solution 1: Line Sweep with Array (fixed stops 0-1000) - Time: O(n + 1001), Space: O(1001)
 */
class CarPooling1 {
    fun carPooling(trips: Array<IntArray>, capacity: Int): Boolean {
        val stops = IntArray(1001)

        for ((passengers, from, to) in trips) {
            stops[from] += passengers
            stops[to] -= passengers
        }

        var current = 0
        for (count in stops) {
            current += count
            if (current > capacity) return false
        }

        return true
    }
}

/** Solution 2: Sort Events - Time: O(n log n), Space: O(n) */
class CarPooling2 {
    fun carPooling(trips: Array<IntArray>, capacity: Int): Boolean {
        val events = mutableListOf<Pair<Int, Int>>()

        for ((passengers, from, to) in trips) {
            events.add(from to passengers)
            events.add(to to -passengers)
        }

        events.sortWith(compareBy({ it.first }, { it.second }))

        var current = 0
        for ((_, change) in events) {
            current += change
            if (current > capacity) return false
        }

        return true
    }
}

/** Solution 3: Priority Queue - Time: O(n log n), Space: O(n) */
class CarPooling3 {
    fun carPooling(trips: Array<IntArray>, capacity: Int): Boolean {
        trips.sortBy { it[1] }
        val active = PriorityQueue<IntArray>(compareBy { it[2] })
        var current = 0

        for (trip in trips) {
            while (active.isNotEmpty() && active.peek()[2] <= trip[1]) {
                current -= active.poll()[0]
            }
            current += trip[0]
            if (current > capacity) return false
            active.offer(trip)
        }

        return true
    }
}
