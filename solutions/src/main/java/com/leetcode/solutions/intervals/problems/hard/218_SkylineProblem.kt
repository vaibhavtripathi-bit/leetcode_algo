package com.leetcode.solutions.intervals.problems.hard

import java.util.TreeMap

/**
 * LeetCode 218: The Skyline Problem
 * https://leetcode.com/problems/the-skyline-problem/
 *
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft
 *
 * Buildings described as [left, right, height]. Return the skyline outline as
 * a list of [x, height] points where height changes.
 *
 * Example: [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
 * → [[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
 */

/**
 * Solution 1: Line Sweep + TreeMap (Max Heap) - Time: O(n log n), Space: O(n)
 *
 * Events: building start = negative height (so starts sort before ends at same x)
 *         building end = positive height
 * Maintain active heights in TreeMap. Record point when max height changes.
 */
class Skyline1 {
    fun getSkyline(buildings: Array<IntArray>): List<List<Int>> {
        val events = buildEvents(buildings)
        return sweepAndRecord(events)
    }

    private fun buildEvents(buildings: Array<IntArray>): List<Pair<Int, Int>> {
        val events = mutableListOf<Pair<Int, Int>>()

        for ((left, right, height) in buildings) {
            events.add(left to -height)   // negative = start (process before end at same x)
            events.add(right to height)   // positive = end
        }

        return events.sortedWith(compareBy({ it.first }, { it.second }))
    }

    private fun sweepAndRecord(events: List<Pair<Int, Int>>): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val activeHeights = TreeMap<Int, Int>()
        activeHeights[0] = 1    // ground level

        for ((x, h) in events) {
            if (h < 0) {
                addHeight(activeHeights, -h)
            } else {
                removeHeight(activeHeights, h)
            }

            val currentMax = activeHeights.lastKey()
            if (result.isEmpty() || result.last()[1] != currentMax) {
                result.add(listOf(x, currentMax))
            }
        }

        return result
    }

    private fun addHeight(map: TreeMap<Int, Int>, height: Int) {
        map[height] = map.getOrDefault(height, 0) + 1
    }

    private fun removeHeight(map: TreeMap<Int, Int>, height: Int) {
        val count = map[height]!!
        if (count == 1) map.remove(height)
        else map[height] = count - 1
    }
}

/**
 * Solution 2: Priority Queue approach - Time: O(n² log n) worst case, O(n log n) avg
 */
class Skyline2 {
    fun getSkyline(buildings: Array<IntArray>): List<List<Int>> {
        val events = mutableListOf<IntArray>()

        for ((l, r, h) in buildings) {
            events.add(intArrayOf(l, -h))
            events.add(intArrayOf(r, h))
        }

        events.sortWith(compareBy({ it[0] }, { it[1] }))

        val result = mutableListOf<List<Int>>()
        val activeHeights = sortedMapOf(0 to 1)

        for ((x, h) in events) {
            if (h < 0) {
                activeHeights[-h] = activeHeights.getOrDefault(-h, 0) + 1
            } else {
                val count = activeHeights[h]!!
                if (count == 1) activeHeights.remove(h) else activeHeights[h] = count - 1
            }

            val maxH = activeHeights.keys.last()
            if (result.isEmpty() || result.last()[1] != maxH) {
                result.add(listOf(x, maxH))
            }
        }

        return result
    }
}
