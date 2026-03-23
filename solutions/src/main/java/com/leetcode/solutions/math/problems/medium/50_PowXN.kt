package com.leetcode.solutions.math.problems.medium

/**
 * LeetCode 50: Pow(x, n)
 * https://leetcode.com/problems/powx-n/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Implement pow(x, n) — x raised to the power n.
 *
 * Example: 2.0, 10 → 1024.0
 * Example: 2.1, 3 → 9.261
 * Example: 2.0, -2 → 0.25
 */

/** Solution 1: Recursive Fast Power - Time: O(log n), Space: O(log n) */
class Pow1 {
    fun myPow(x: Double, n: Int): Double {
        return when {
            n == 0  -> 1.0
            n < 0   -> 1.0 / myPow(x, -(n.toLong()).toInt())
            n % 2 == 0 -> {
                val half = myPow(x, n / 2)
                half * half
            }
            else -> x * myPow(x, n - 1)
        }
    }
}

/** Solution 2: Iterative - Time: O(log n), Space: O(1) */
class Pow2 {
    fun myPow(x: Double, n: Int): Double {
        var exp = n.toLong()
        var base = x
        var result = 1.0

        if (exp < 0) { base = 1.0 / base; exp = -exp }

        while (exp > 0) {
            if (exp % 2 == 1L) result *= base
            base *= base
            exp /= 2
        }

        return result
    }
}
