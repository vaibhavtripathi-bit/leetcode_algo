package com.leetcode.solutions.math.algorithms

/**
 * Essential Math Algorithms
 */
object MathUtils {

    /** GCD using Euclidean Algorithm - Time: O(log min(a,b)) */
    fun gcd(a: Int, b: Int): Int {
        var x = a; var y = b
        while (y != 0) { val temp = y; y = x % y; x = temp }
        return x
    }

    /** LCM derived from GCD */
    fun lcm(a: Long, b: Long): Long = (a / gcd(a.toInt(), b.toInt())) * b

    /** Fast Power (Binary Exponentiation) - Time: O(log n) */
    fun power(base: Long, exp: Long, mod: Long = Long.MAX_VALUE): Long {
        var result = 1L
        var b = base % mod
        var e = exp

        while (e > 0) {
            if (e % 2 == 1L) result = result * b % mod
            b = b * b % mod
            e /= 2
        }

        return result
    }

    /** Sieve of Eratosthenes - returns isPrime array - Time: O(n log log n) */
    fun sieve(n: Int): BooleanArray {
        val isPrime = BooleanArray(n + 1) { true }
        isPrime[0] = false
        if (n > 0) isPrime[1] = false

        var i = 2
        while (i.toLong() * i <= n) {
            if (isPrime[i]) {
                var j = i * i
                while (j <= n) { isPrime[j] = false; j += i }
            }
            i++
        }

        return isPrime
    }
}
