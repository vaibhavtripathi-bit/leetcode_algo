package com.leetcode.solutions.greedy.problems.medium

/**
 * LeetCode 134: Gas Station
 * https://leetcode.com/problems/gas-station/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Microsoft
 *
 * Find the starting gas station index to complete the circular route.
 * Return -1 if impossible.
 *
 * Example: gas = [1,2,3,4,5], cost = [3,4,5,1,2] → 3
 */

/** Solution 1: Greedy One Pass - Time: O(n), Space: O(1) */
class GasStation1 {
    fun canCompleteCircuit(gas: IntArray, cost: IntArray): Int {
        val totalSurplus = gas.zip(cost.toList()).sumOf { (g, c) -> g - c }
        if (totalSurplus < 0) return -1

        var tank = 0
        var start = 0

        for (i in gas.indices) {
            tank += gas[i] - cost[i]
            if (tank < 0) {
                start = i + 1
                tank = 0
            }
        }

        return start
    }
}

/** Solution 2: Brute Force (for understanding) - Time: O(n²), Space: O(1) */
class GasStation2 {
    fun canCompleteCircuit(gas: IntArray, cost: IntArray): Int {
        for (start in gas.indices) {
            if (canComplete(gas, cost, start)) return start
        }
        return -1
    }

    private fun canComplete(gas: IntArray, cost: IntArray, start: Int): Boolean {
        var tank = 0
        for (i in gas.indices) {
            val pos = (start + i) % gas.size
            tank += gas[pos] - cost[pos]
            if (tank < 0) return false
        }
        return true
    }
}
