package com.leetcode.solutions.greedy.problems.hard

/**
 * LeetCode 135: Candy
 * https://leetcode.com/problems/candy/
 *
 * Difficulty: Hard
 * Companies: Amazon, Google
 *
 * Give each child at least 1 candy. Children with higher rating get more than neighbors.
 * Find minimum total candies.
 *
 * Example: ratings = [1,0,2] → 5 (2,1,2)
 */

/** Solution 1: Two-Pass Greedy - Time: O(n), Space: O(n) */
class Candy1 {
    fun candy(ratings: IntArray): Int {
        val n = ratings.size
        val candies = IntArray(n) { 1 }

        satisfyLeftConstraint(ratings, candies)
        satisfyRightConstraint(ratings, candies)

        return candies.sum()
    }

    private fun satisfyLeftConstraint(ratings: IntArray, candies: IntArray) {
        for (i in 1 until ratings.size) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1
            }
        }
    }

    private fun satisfyRightConstraint(ratings: IntArray, candies: IntArray) {
        for (i in ratings.size - 2 downTo 0) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = maxOf(candies[i], candies[i + 1] + 1)
            }
        }
    }
}

/** Solution 2: Single Pass with slope counting - Time: O(n), Space: O(1) */
class Candy2 {
    fun candy(ratings: IntArray): Int {
        var total = 1
        var up = 0
        var down = 0
        var peak = 0

        for (i in 1 until ratings.size) {
            when {
                ratings[i] > ratings[i - 1] -> {
                    up++; down = 0; peak = up
                    total += up + 1
                }
                ratings[i] == ratings[i - 1] -> {
                    up = 0; down = 0; peak = 0
                    total += 1
                }
                else -> {
                    up = 0; down++
                    total += down + 1 + if (peak >= down) -1 else 0
                }
            }
        }

        return total
    }
}
