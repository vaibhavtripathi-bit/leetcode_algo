package com.leetcode.solutions.math.problems.medium

import com.leetcode.solutions.math.algorithms.MathUtils

/**
 * LeetCode 204: Count Primes
 * https://leetcode.com/problems/count-primes/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Microsoft
 *
 * Count the number of prime numbers less than n.
 *
 * Example: n = 10 → 4 (2, 3, 5, 7)
 */

/** Solution 1: Sieve of Eratosthenes - Time: O(n log log n), Space: O(n) */
class CountPrimes1 {
    fun countPrimes(n: Int): Int {
        if (n < 2) return 0
        return MathUtils.sieve(n - 1).count { it }
    }
}

/** Solution 2: Inline Sieve - Time: O(n log log n), Space: O(n) */
class CountPrimes2 {
    fun countPrimes(n: Int): Int {
        if (n < 2) return 0

        val notPrime = BooleanArray(n)
        var count = 0

        for (i in 2 until n) {
            if (!notPrime[i]) {
                count++
                var j = i.toLong() * i
                while (j < n) {
                    notPrime[j.toInt()] = true
                    j += i
                }
            }
        }

        return count
    }
}
