package com.leetcode.solutions.stack.problems.medium

/**
 * 853. Car Fleet
 * https://leetcode.com/problems/car-fleet/
 *
 * There are n cars going to the same destination along a one-lane road.
 * The destination is target miles away.
 *
 * You are given two integer arrays position and speed, both of length n, where
 * position[i] is the position of the ith car and speed[i] is the speed of the ith car (mph).
 *
 * A car can never pass another car ahead of it, but it can catch up to it and drive
 * bumper to bumper at the same speed. The faster car will slow down to match the
 * slower car's speed. The distance between these two cars is ignored.
 *
 * A car fleet is some non-empty set of cars driving at the same position and same speed.
 * A single car is also a car fleet.
 *
 * If a car catches up to a car fleet right at the destination point, it will still
 * be considered as one car fleet.
 *
 * Return the number of car fleets that will arrive at the destination.
 *
 * Example 1:
 * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
 * Output: 3
 * Explanation:
 * - Cars starting at 10 (speed 2) and 8 (speed 4) become a fleet at position 12 (1 fleet)
 * - Car starting at 0 (speed 1) alone (1 fleet)
 * - Cars starting at 5 (speed 1) and 3 (speed 3) become a fleet at position 6 (1 fleet)
 *
 * Example 2:
 * Input: target = 10, position = [3], speed = [3]
 * Output: 1
 *
 * Constraints:
 * - n == position.length == speed.length
 * - 1 <= n <= 10^5
 * - 0 < target <= 10^6
 * - 0 <= position[i] < target
 * - 0 < speed[i] <= 10^6
 *
 * Companies: Google, Amazon, Microsoft, Meta, Uber, Apple, Bloomberg
 */
class CarFleet {

    /**
     * Solution 1: Sort and Stack
     * Sort cars by position descending, use stack to track fleet times.
     *
     * Time Complexity: O(n log n) - dominated by sorting
     * Space Complexity: O(n) - for sorted array and stack
     */
    fun carFleetStack(target: Int, position: IntArray, speed: IntArray): Int {
        val n = position.size
        if (n == 0) return 0

        val cars = position.zip(speed.toList())
            .sortedByDescending { it.first }

        val stack = ArrayDeque<Double>()

        for ((pos, spd) in cars) {
            val timeToReach = (target - pos).toDouble() / spd
            if (stack.isEmpty() || timeToReach > stack.last()) {
                stack.addLast(timeToReach)
            }
        }

        return stack.size
    }

    /**
     * Solution 2: Sort and Count (No Explicit Stack)
     * Track the slowest time seen so far (implicit stack top).
     *
     * Time Complexity: O(n log n) - dominated by sorting
     * Space Complexity: O(n) - for sorted array
     */
    fun carFleetCount(target: Int, position: IntArray, speed: IntArray): Int {
        val n = position.size
        if (n == 0) return 0

        val cars = position.indices
            .sortedByDescending { position[it] }

        var fleets = 0
        var maxTime = 0.0

        for (i in cars) {
            val timeToReach = (target - position[i]).toDouble() / speed[i]
            if (timeToReach > maxTime) {
                fleets++
                maxTime = timeToReach
            }
        }

        return fleets
    }

    /**
     * Solution 3: Bucket Sort Approach
     * Use position as index for O(n) time when position range is bounded.
     *
     * Time Complexity: O(n + target) - bucket sort
     * Space Complexity: O(target) - for time array
     */
    fun carFleetBucket(target: Int, position: IntArray, speed: IntArray): Int {
        val timeAtPosition = DoubleArray(target) { -1.0 }

        for (i in position.indices) {
            timeAtPosition[position[i]] = (target - position[i]).toDouble() / speed[i]
        }

        var fleets = 0
        var maxTime = 0.0

        for (pos in target - 1 downTo 0) {
            val time = timeAtPosition[pos]
            if (time > maxTime) {
                fleets++
                maxTime = time
            }
        }

        return fleets
    }

    /**
     * Solution 4: Using Data Class for Clarity
     * More readable approach with explicit Car data class.
     *
     * Time Complexity: O(n log n) - dominated by sorting
     * Space Complexity: O(n) - for car objects
     */
    fun carFleetWithDataClass(target: Int, position: IntArray, speed: IntArray): Int {
        data class Car(val position: Int, val speed: Int) {
            fun timeToTarget(target: Int): Double =
                (target - position).toDouble() / speed
        }

        val cars = position.indices
            .map { Car(position[it], speed[it]) }
            .sortedByDescending { it.position }

        var fleets = 0
        var slowestTime = 0.0

        for (car in cars) {
            val arrivalTime = car.timeToTarget(target)
            if (arrivalTime > slowestTime) {
                fleets++
                slowestTime = arrivalTime
            }
        }

        return fleets
    }
}

private fun calculateTimeToTarget(position: Int, speed: Int, target: Int): Double {
    return (target - position).toDouble() / speed
}

private fun willCatchUp(car1Time: Double, car2Time: Double): Boolean {
    return car1Time <= car2Time
}

private fun validateInput(target: Int, position: IntArray, speed: IntArray): Boolean {
    if (position.size != speed.size) return false
    if (target <= 0) return false
    return position.all { it in 0 until target } && speed.all { it > 0 }
}
