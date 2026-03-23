package com.leetcode.solutions.stack.problems.medium

/**
 * LeetCode 739: Daily Temperatures
 * https://leetcode.com/problems/daily-temperatures/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Meta, Microsoft
 *
 * Return array where result[i] = number of days until a warmer temperature.
 * If no warmer day exists, result[i] = 0.
 *
 * Example: temperatures = [73,74,75,71,69,72,76,73] → [1,1,4,2,1,1,0,0]
 */

/** Solution 1: Monotonic Stack - Time: O(n), Space: O(n) */
class DailyTemperatures1 {
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        val result = IntArray(temperatures.size)
        val stack = ArrayDeque<Int>()

        for (today in temperatures.indices) {
            while (hasWarmerDay(stack, temperatures, today)) {
                val coldDay = stack.removeLast()
                result[coldDay] = today - coldDay
            }
            stack.addLast(today)
        }

        return result
    }

    private fun hasWarmerDay(stack: ArrayDeque<Int>, temps: IntArray, today: Int) =
        stack.isNotEmpty() && temps[stack.last()] < temps[today]
}

/** Solution 2: Reverse traversal - Time: O(n), Space: O(n) */
class DailyTemperatures2 {
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        val n = temperatures.size
        val result = IntArray(n)

        for (i in n - 2 downTo 0) {
            result[i] = findNextWarmer(temperatures, result, i)
        }

        return result
    }

    private fun findNextWarmer(temps: IntArray, result: IntArray, i: Int): Int {
        var j = i + 1
        while (j < temps.size) {
            if (temps[j] > temps[i]) return j - i
            if (result[j] == 0) break
            j += result[j]
        }
        return 0
    }
}

/** Solution 3: Brute Force - Time: O(n²), Space: O(1) */
class DailyTemperatures3 {
    fun dailyTemperatures(temperatures: IntArray): IntArray {
        return IntArray(temperatures.size) { i ->
            findWarmerDay(temperatures, i)
        }
    }

    private fun findWarmerDay(temps: IntArray, day: Int): Int {
        for (j in day + 1 until temps.size) {
            if (temps[j] > temps[day]) return j - day
        }
        return 0
    }
}
