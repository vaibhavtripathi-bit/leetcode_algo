package com.leetcode.solutions.utils

/**
 * MathUtils — high-frequency math helpers for FAANG interviews.
 *
 * Used across: GCD problems, modular arithmetic (combinations, large answers),
 * fast power (Pow(x,n)), prime checking.
 */

// ─────────────────────────────────────────────
// GCD / LCM
// ─────────────────────────────────────────────

/**
 * Greatest Common Divisor — Euclidean algorithm. O(log min(a,b)).
 * Used in: Fraction to Recurring Decimal, GCD of Strings,
 *          simplifying fractions in interval/math problems.
 */
fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

/** Least Common Multiple. lcm(4, 6) → 12 */
fun lcm(a: Int, b: Int): Long = a.toLong() * b / gcd(a, b)

fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

// ─────────────────────────────────────────────
// Modular arithmetic
// ─────────────────────────────────────────────

const val MOD = 1_000_000_007L

/**
 * Safe modulo that always returns a non-negative result.
 * Kotlin's % can return negative for negative operands.
 * (-1).safeMod(3) → 2 (not -1)
 */
fun Int.safeMod(m: Int): Int = ((this % m) + m) % m

fun Long.safeMod(m: Long): Long = ((this % m) + m) % m

/** (a * b) % mod — avoids overflow for large a, b. */
fun mulMod(a: Long, b: Long, mod: Long = MOD): Long = a % mod * (b % mod) % mod

/** (a + b) % mod */
fun addMod(a: Long, b: Long, mod: Long = MOD): Long = (a % mod + b % mod) % mod

// ─────────────────────────────────────────────
// Fast power (Binary Exponentiation)
// Used in: Pow(x, n), Fibonacci via matrix,
//          modular inverse, combinations mod p
// ─────────────────────────────────────────────

/**
 * Computes base^exp in O(log exp) using fast power.
 * Handles negative exponent: returns 1.0 / fastPow(base, -exp).
 * Used in: LeetCode 50 — Pow(x, n)
 */
fun fastPow(base: Double, exp: Int): Double {
    if (exp == 0) return 1.0
    if (exp < 0)  return 1.0 / fastPow(base, -exp)

    val half = fastPow(base, exp / 2)
    return if (exp % 2 == 0) half * half else half * half * base
}

/**
 * Modular fast power: (base^exp) % mod. All Int inputs, Long intermediate.
 * Used when the answer must be returned modulo a large prime.
 */
fun modPow(base: Long, exp: Long, mod: Long = MOD): Long {
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

// ─────────────────────────────────────────────
// Prime checking
// ─────────────────────────────────────────────

/**
 * True if n is prime. O(√n).
 * Used in: Count Primes (combined with sieve), specific math problems.
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n < 4) return true
    if (n % 2 == 0 || n % 3 == 0) return false
    var i = 5
    while (i * i <= n) {
        if (n % i == 0 || n % (i + 2) == 0) return false
        i += 6
    }
    return true
}

/**
 * Sieve of Eratosthenes — returns BooleanArray where [i] = true means i is prime.
 * O(n log log n) time. Used in: Count Primes (LeetCode 204).
 */
fun sieve(limit: Int): BooleanArray {
    val isPrime = BooleanArray(limit + 1) { it >= 2 }
    var p = 2
    while (p.toLong() * p <= limit) {
        if (isPrime[p]) {
            var multiple = p * p
            while (multiple <= limit) {
                isPrime[multiple] = false
                multiple += p
            }
        }
        p++
    }
    return isPrime
}

// ─────────────────────────────────────────────
// Integer square root
// Used in: Valid Perfect Square, kth factor
// ─────────────────────────────────────────────

/** Integer square root without floating point. isqrt(16) → 4, isqrt(17) → 4 */
fun isqrt(n: Long): Long {
    var lo = 0L; var hi = n
    while (lo < hi) {
        val mid = lo + (hi - lo + 1) / 2
        if (mid <= n / mid) lo = mid else hi = mid - 1
    }
    return lo
}

/** True if n is a perfect square. 16 → true, 17 → false */
fun isPerfectSquare(n: Long): Boolean {
    val root = isqrt(n)
    return root * root == n
}
