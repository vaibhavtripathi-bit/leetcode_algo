package com.leetcode.solutions.stack.problems.medium

/**
 * 739. Daily Temperatures
 * https://leetcode.com/problems/daily-temperatures/
 *
 * Given an array of integers temperatures represents the daily temperatures,
 * return an array answer such that answer[i] is the number of days you have to wait
 * after the ith day to get a warmer temperature. If there is no future day for which
 * this is possible, keep answer[i] == 0 instead.
 *
 * Example 1:
 * Input: temperatures = [73,74,75,71,69,72,76,73]
 * Output: [1,1,4,2,1,1,0,0]
 *
 * Example 2:
 * Input: temperatures = [30,40,50,60]
 * Output: [1,1,1,0]
 *
 * Example 3:
 * Input: temperatures = [30,60,90]
 * Output: [1,1,0]
 *
 * Constraints:
 * - 1 <= temperatures.length <= 10^5
 * - 30 <= temperatures[i] <= 100
 *
 * Companies: Amazon, Google, Microsoft, Meta, Apple, Bloomberg, Goldman Sachs, Uber
 */
class DailyTemperatures {

    /**
     * Solution 1: Monotonic Decreasing Stack
     * Use a stack to track indices of temperatures waiting for a warmer day.
     * Stack maintains indices in decreasing temperature order.
     *
     * Time Complexity: O(n) - each index pushed and popped at most once
     * Space Complexity: O(n) - stack can hold up to n indices
     */
    fun dailyTemperaturesStack(temperatures: IntArray): IntArray {
        val n = temperatures.size
        val result = IntArray(n)
        val stack = ArrayDeque<Int>()

        for (i in temperatures.indices) {
            while (stack.isNotEmpty() && temperatures[i] > temperatures[stack.last()]) {
                val prevIndex = stack.removeLast()
                result[prevIndex] = i - prevIndex
            }
            stack.addLast(i)
        }

        return result
    }

    /**
     * Solution 2: Monotonic Stack with Pairs
     * Store both index and temperature in stack for clarity.
     *
     * Time Complexity: O(n) - each element processed once
     * Space Complexity: O(n) - stack storage
     */
    fun dailyTemperaturesWithPairs(temperatures: IntArray): IntArray {
        val n = temperatures.size
        val result = IntArray(n)
        val stack = ArrayDeque<Pair<Int, Int>>()

        for (i in temperatures.indices) {
            val currentTemp = temperatures[i]
            while (stack.isNotEmpty() && currentTemp > stack.last().second) {
                val (prevIndex, _) = stack.removeLast()
                result[prevIndex] = i - prevIndex
            }
            stack.addLast(Pair(i, currentTemp))
        }

        return result
    }

    /**
     * Solution 3: Reverse Iteration with Stack
     * Process from right to left, building answer as we go.
     *
     * Time Complexity: O(n) - each index processed once
     * Space Complexity: O(n) - stack storage
     */
    fun dailyTemperaturesReverse(temperatures: IntArray): IntArray {
        val n = temperatures.size
        val result = IntArray(n)
        val stack = ArrayDeque<Int>()

        for (i in n - 1 downTo 0) {
            while (stack.isNotEmpty() && temperatures[stack.last()] <= temperatures[i]) {
                stack.removeLast()
            }
            result[i] = if (stack.isEmpty()) 0 else stack.last() - i
            stack.addLast(i)
        }

        return result
    }

    /**
     * Solution 4: Optimized Space - Using Result Array
     * Use the result array itself to find next warmer day.
     * Jump forward using previously computed results.
     *
     * Time Complexity: O(n) - amortized, each element visited constant times
     * Space Complexity: O(1) - only output array, no extra stack
     */
    fun dailyTemperaturesOptimized(temperatures: IntArray): IntArray {
        val n = temperatures.size
        val result = IntArray(n)

        for (i in n - 2 downTo 0) {
            var j = i + 1
            while (j < n) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i
                    break
                } else if (result[j] == 0) {
                    result[i] = 0
                    break
                } else {
                    j += result[j]
                }
            }
        }

        return result
    }
}

private fun findNextWarmer(temperatures: IntArray, startIndex: Int): Int {
    for (i in startIndex + 1 until temperatures.size) {
        if (temperatures[i] > temperatures[startIndex]) {
            return i - startIndex
        }
    }
    return 0
}

private fun validateTemperatures(temperatures: IntArray): Boolean {
    return temperatures.all { it in 30..100 }
}

private fun formatResult(result: IntArray): String {
    return result.joinToString(", ", "[", "]")
}
