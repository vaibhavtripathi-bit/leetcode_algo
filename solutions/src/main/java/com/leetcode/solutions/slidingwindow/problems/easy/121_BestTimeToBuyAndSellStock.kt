package com.leetcode.solutions.slidingwindow.problems.easy

/**
 * 121. Best Time to Buy and Sell Stock
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 *
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a
 * different day in the future to sell that stock.
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any
 * profit, return 0.
 *
 * Example 1:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 *
 * Example 2:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: No transactions are done and the max profit = 0.
 *
 * Constraints:
 * - 1 <= prices.length <= 10^5
 * - 0 <= prices[i] <= 10^4
 */
class BestTimeToBuyAndSellStock {

    /**
     * Solution 1: Sliding Window / Two Pointers
     * Track the minimum price seen so far (buy point) and calculate max profit.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    fun maxProfitSlidingWindow(prices: IntArray): Int {
        var left = 0
        var right = 1
        var maxProfit = 0

        while (right < prices.size) {
            if (prices[left] < prices[right]) {
                val profit = prices[right] - prices[left]
                maxProfit = maxOf(maxProfit, profit)
            } else {
                left = right
            }
            right++
        }

        return maxProfit
    }

    /**
     * Solution 2: One Pass with Min Tracking
     * Keep track of minimum price and update max profit accordingly.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    fun maxProfitOnePass(prices: IntArray): Int {
        var minPrice = Int.MAX_VALUE
        var maxProfit = 0

        for (price in prices) {
            if (price < minPrice) {
                minPrice = price
            } else {
                maxProfit = maxOf(maxProfit, price - minPrice)
            }
        }

        return maxProfit
    }

    /**
     * Solution 3: Kadane's Algorithm Variation
     * Treat the problem as finding maximum subarray of price differences.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    fun maxProfitKadane(prices: IntArray): Int {
        var maxCurrent = 0
        var maxSoFar = 0

        for (i in 1 until prices.size) {
            val diff = prices[i] - prices[i - 1]
            maxCurrent = maxOf(0, maxCurrent + diff)
            maxSoFar = maxOf(maxSoFar, maxCurrent)
        }

        return maxSoFar
    }

    /**
     * Solution 4: Dynamic Programming
     * DP approach where we track the minimum price up to each day.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    fun maxProfitDP(prices: IntArray): Int {
        if (prices.isEmpty()) return 0

        var minPriceSoFar = prices[0]
        var maxProfit = 0

        for (i in 1 until prices.size) {
            maxProfit = maxOf(maxProfit, prices[i] - minPriceSoFar)
            minPriceSoFar = minOf(minPriceSoFar, prices[i])
        }

        return maxProfit
    }
}
