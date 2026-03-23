package com.leetcode.solutions.stack.problems.medium

/**
 * LeetCode 853: Car Fleet
 * https://leetcode.com/problems/car-fleet/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon
 *
 * Cars travel to target. Faster car catches slower → they form a fleet.
 * Return the number of fleets that arrive.
 *
 * Example: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3] → 3
 */

/** Solution 1: Sort + Stack - Time: O(n log n), Space: O(n) */
class CarFleet1 {
    fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {
        val cars = sortByPositionDescending(position, speed)
        val stack = ArrayDeque<Double>()

        for ((pos, spd) in cars) {
            val timeToTarget = timeToReach(target, pos, spd)

            if (doesNotCatchFleetAhead(stack, timeToTarget)) {
                stack.addLast(timeToTarget)
            }
        }

        return stack.size
    }

    private fun sortByPositionDescending(position: IntArray, speed: IntArray): List<Pair<Int, Int>> {
        return position.indices
            .map { position[it] to speed[it] }
            .sortedByDescending { it.first }
    }

    private fun timeToReach(target: Int, pos: Int, speed: Int): Double =
        (target - pos).toDouble() / speed

    private fun doesNotCatchFleetAhead(stack: ArrayDeque<Double>, time: Double): Boolean =
        stack.isEmpty() || time > stack.last()
}

/** Solution 2: Sort + Count without Stack - Time: O(n log n), Space: O(n) */
class CarFleet2 {
    fun carFleet(target: Int, position: IntArray, speed: IntArray): Int {
        val times = position.indices
            .sortedByDescending { position[it] }
            .map { (target - position[it]).toDouble() / speed[it] }

        var fleets = 0
        var maxTime = 0.0

        for (time in times) {
            if (time > maxTime) {
                fleets++
                maxTime = time
            }
        }

        return fleets
    }
}
