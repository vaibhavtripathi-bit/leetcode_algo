package com.leetcode.solutions.binarysearch.problems.medium

import kotlin.math.ceil
import kotlin.math.max

/**
 * LeetCode 875: Koko Eating Bananas
 * https://leetcode.com/problems/koko-eating-bananas/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon
 *
 * Koko can eat k bananas/hour. Each pile must be finished in h hours.
 * Find the minimum k such that she can finish all piles within h hours.
 *
 * Example: piles = [3,6,7,11], h = 8 → 4
 */

/** Solution 1: Binary Search on Answer - Time: O(n log m), Space: O(1) */
class MinEatingSpeed1 {
    fun minEatingSpeed(piles: IntArray, h: Int): Int {
        var left = 1
        var right = piles.max()!!

        while (left < right) {
            val mid = left + (right - left) / 2

            if (canFinish(piles, mid, h)) right = mid
            else left = mid + 1
        }

        return left
    }

    private fun canFinish(piles: IntArray, speed: Int, hours: Int): Boolean {
        val totalHours = piles.sumOf { hoursForPile(it, speed) }
        return totalHours <= hours
    }

    private fun hoursForPile(pile: Int, speed: Int): Int =
        ceil(pile.toDouble() / speed).toInt()
}

/** Solution 2: Binary Search with Long Math - Time: O(n log m), Space: O(1) */
class MinEatingSpeed2 {
    fun minEatingSpeed(piles: IntArray, h: Int): Int {
        var left = 1
        var right = piles.max()!!

        while (left < right) {
            val mid = left + (right - left) / 2

            if (totalHours(piles, mid) <= h) right = mid
            else left = mid + 1
        }

        return left
    }

    private fun totalHours(piles: IntArray, speed: Int): Long {
        return piles.sumOf { ((it + speed - 1) / speed).toLong() }
    }
}

/** Solution 3: Linear scan (for understanding) - Time: O(n * maxPile), Space: O(1) */
class MinEatingSpeed3 {
    fun minEatingSpeed(piles: IntArray, h: Int): Int {
        var speed = 1

        while (!canFinish(piles, speed, h)) speed++

        return speed
    }

    private fun canFinish(piles: IntArray, speed: Int, hours: Int): Boolean {
        return piles.sumOf { ceil(it.toDouble() / speed).toInt() } <= hours
    }
}
